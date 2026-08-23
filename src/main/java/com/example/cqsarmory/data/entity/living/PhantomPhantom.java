package com.example.cqsarmory.data.entity.living;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.root.PreventDismount;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.cast.CastSource;
import io.redspace.skillcasting.data.cast.CasterRef;
import io.redspace.skillcasting.lifecycle.SkillcastingManager;
import io.redspace.skillcasting.registry.SkillcastingComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class PhantomPhantom extends Phantom implements PreventDismount {

    PhantomPhantom.AttackPhase attackPhase = PhantomPhantom.AttackPhase.CIRCLE;
    Vec3 moveTargetPoint = Vec3.ZERO;
    BlockPos anchorPoint = BlockPos.ZERO;
    ArrayList<Entity> riders = new ArrayList<>();
    int shootCD = 60;
    int shootCount = 5;


    public PhantomPhantom(EntityType<? extends Phantom> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PhantomPhantom.PhantomPhantomMoveControl(this);
    }

    public static boolean checkPhantomPhantomSpawnRules(
            EntityType<PhantomPhantom> type,
            ServerLevelAccessor level,
            MobSpawnType reason,
            BlockPos pos,
            RandomSource random
    ) {
        if (pos.getY() < 150) {
            return false;
        }

        return level.isEmptyBlock(pos)
                && level.isEmptyBlock(pos.above())
                && level.isEmptyBlock(pos.below());
    }

    @Override
    public boolean showVehicleHealth() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getTarget() != null && this.attackPhase == AttackPhase.CIRCLE && !this.riders.contains(this.getTarget())) {
            if (this.tickCount % 2 == 0 && this.shootCount >= 1 && this.shootCD <= 0) {
                this.shootCount--;
                shootWindCharge();
            }
            if (this.shootCount <= 0) {
                this.shootCount = 5;
                this.shootCD = 60;
            }
        }
        this.shootCD--;
    }

    @Override
    public boolean hasIndirectPassenger(Entity p_entity) {
        return true;
    }

    public void dropRider(Entity entity) {
        entity.vehicle = null;
        this.removePassenger(entity);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (super.hurt(source, amount)) {
            if (this.riders.contains(source.getEntity())) {
                riders.forEach(this::dropRider);
                riders.clear();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PhantomPhantom.PhantomPhantomAttackStrategyGoal());
        this.goalSelector.addGoal(2, new PhantomPhantom.PhantomPhantomSweepAttackGoal());
        this.goalSelector.addGoal(3, new PhantomPhantom.PhantomPhantomCircleAroundAnchorGoal());
        this.targetSelector.addGoal(1, new PhantomPhantom.PhantomPhantomAttackPlayerTargetGoal());
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 14.0)
                .add(Attributes.ATTACK_KNOCKBACK, 8.0)
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .add(Attributes.MOVEMENT_SPEED, 2)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 3)
                .add(Attributes.SCALE, 3);
    }

    static enum AttackPhase {
        CIRCLE,
        SWOOP;
    }

    class PhantomPhantomAttackPlayerTargetGoal extends Goal {
        private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0);
        private int nextScanTick = reducedTickDelay(20);

        @Override
        public boolean canUse() {
            if (this.nextScanTick > 0) {
                this.nextScanTick--;
                return false;
            } else {
                this.nextScanTick = reducedTickDelay(60);
                List<Player> list = PhantomPhantom.this.level()
                        .getNearbyPlayers(this.attackTargeting, PhantomPhantom.this, PhantomPhantom.this.getBoundingBox().inflate(64.0, 64.0, 64.0));
                if (!list.isEmpty()) {
                    list.sort(Comparator.<Player, Double>comparing(Entity::getY).reversed());

                    for (Player player : list) {
                        if (PhantomPhantom.this.canAttack(player, TargetingConditions.DEFAULT)) {
                            PhantomPhantom.this.setTarget(player);
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingentity = PhantomPhantom.this.getTarget();
            return livingentity != null ? PhantomPhantom.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
        }
    }

    class PhantomPhantomAttackStrategyGoal extends Goal {
        private int nextSweepTick;

        @Override
        public boolean canUse() {
            LivingEntity livingentity = PhantomPhantom.this.getTarget();
            return livingentity != null ? PhantomPhantom.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
        }

        @Override
        public void start() {
            this.nextSweepTick = this.adjustedTickDelay(10);
            PhantomPhantom.this.attackPhase = PhantomPhantom.AttackPhase.CIRCLE;
            this.setAnchorAboveTarget();
        }

        @Override
        public void stop() {
            PhantomPhantom.this.anchorPoint = PhantomPhantom.this.level()
                    .getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, PhantomPhantom.this.anchorPoint)
                    .above(10 + PhantomPhantom.this.random.nextInt(20));
        }

        @Override
        public void tick() {
            if (PhantomPhantom.this.attackPhase == PhantomPhantom.AttackPhase.CIRCLE) {
                this.nextSweepTick--;
                /*if (this.nextSweepTick % 2 == 0 && PhantomPhantom.this.getTarget() != null) {
                    shootWindCharge();
                }*/
                if (this.nextSweepTick <= 0) {
                    if (!PhantomPhantom.this.riders.isEmpty()) {
                        PhantomPhantom.this.riders.forEach(PhantomPhantom.this::doHurtTarget);
                        PhantomPhantom.this.riders.forEach(PhantomPhantom.this::dropRider);
                        PhantomPhantom.this.riders.clear();
                    }
                    PhantomPhantom.this.attackPhase = PhantomPhantom.AttackPhase.SWOOP;
                    this.setAnchorAboveTarget();
                    this.nextSweepTick = this.adjustedTickDelay((8 + PhantomPhantom.this.random.nextInt(4)) * 20);
                    PhantomPhantom.this.playSound(SoundEvents.PHANTOM_SWOOP, 10.0F, 0.95F + PhantomPhantom.this.random.nextFloat() * 0.1F);
                }
            }
        }

        private void setAnchorAboveTarget() {
            PhantomPhantom.this.anchorPoint = PhantomPhantom.this.getTarget().blockPosition().above(20 + PhantomPhantom.this.random.nextInt(20));
            if (PhantomPhantom.this.anchorPoint.getY() < PhantomPhantom.this.level().getSeaLevel()) {
                PhantomPhantom.this.anchorPoint = new BlockPos(
                        PhantomPhantom.this.anchorPoint.getX(), PhantomPhantom.this.level().getSeaLevel() + 1, PhantomPhantom.this.anchorPoint.getZ()
                );
            }
        }
    }

    class PhantomPhantomBodyRotationControl extends BodyRotationControl {
        public PhantomPhantomBodyRotationControl(Mob mob) {
            super(mob);
        }

        @Override
        public void clientTick() {
            PhantomPhantom.this.yHeadRot = PhantomPhantom.this.yBodyRot;
            PhantomPhantom.this.yBodyRot = PhantomPhantom.this.getYRot();
        }
    }

    class PhantomPhantomCircleAroundAnchorGoal extends PhantomPhantom.PhantomPhantomMoveTargetGoal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        @Override
        public boolean canUse() {
            return PhantomPhantom.this.getTarget() == null || PhantomPhantom.this.attackPhase == PhantomPhantom.AttackPhase.CIRCLE;
        }

        @Override
        public void start() {
            this.distance = 5.0F + PhantomPhantom.this.random.nextFloat() * 10.0F;
            this.height = -4.0F + PhantomPhantom.this.random.nextFloat() * 9.0F;
            this.clockwise = PhantomPhantom.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }

        @Override
        public void tick() {

            /*if (PhantomPhantom.this.tickCount % 2 == 0 && PhantomPhantom.this.getTarget() != null) {
                shootWindCharge();
            }*/

            if (PhantomPhantom.this.random.nextInt(this.adjustedTickDelay(350)) == 0) {
                this.height = -4.0F + PhantomPhantom.this.random.nextFloat() * 9.0F;
            }

            if (PhantomPhantom.this.random.nextInt(this.adjustedTickDelay(250)) == 0) {
                this.distance++;
                if (this.distance > 15.0F) {
                    this.distance = 5.0F;
                    this.clockwise = -this.clockwise;
                }
            }

            if (PhantomPhantom.this.random.nextInt(this.adjustedTickDelay(450)) == 0) {
                this.angle = PhantomPhantom.this.random.nextFloat() * 2.0F * (float) Math.PI;
                this.selectNext();
            }

            if (this.touchingTarget()) {
                this.selectNext();
            }

            if (PhantomPhantom.this.moveTargetPoint.y < PhantomPhantom.this.getY() && !PhantomPhantom.this.level().isEmptyBlock(PhantomPhantom.this.blockPosition().below(1))) {
                this.height = Math.max(1.0F, this.height);
                this.selectNext();
            }

            if (PhantomPhantom.this.moveTargetPoint.y > PhantomPhantom.this.getY() && !PhantomPhantom.this.level().isEmptyBlock(PhantomPhantom.this.blockPosition().above(1))) {
                this.height = Math.min(-1.0F, this.height);
                this.selectNext();
            }
        }

        private void selectNext() {
            if (BlockPos.ZERO.equals(PhantomPhantom.this.anchorPoint)) {
                PhantomPhantom.this.anchorPoint = PhantomPhantom.this.blockPosition();
            }

            this.angle = this.angle + this.clockwise * 15.0F * (float) (Math.PI / 180.0);
            PhantomPhantom.this.moveTargetPoint = Vec3.atLowerCornerOf(PhantomPhantom.this.anchorPoint)
                    .add((double)(this.distance * Mth.cos(this.angle)), (double)(-4.0F + this.height), (double)(this.distance * Mth.sin(this.angle)));
        }
    }

    abstract class PhantomPhantomMoveTargetGoal extends Goal {
        public PhantomPhantomMoveTargetGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        protected boolean touchingTarget() {
            return PhantomPhantom.this.moveTargetPoint.distanceToSqr(PhantomPhantom.this.getX(), PhantomPhantom.this.getY(), PhantomPhantom.this.getZ()) < 4.0;
        }
    }

    class PhantomPhantomSweepAttackGoal extends PhantomPhantom.PhantomPhantomMoveTargetGoal {
        private static final int CAT_SEARCH_TICK_DELAY = 20;
        private boolean isScaredOfCat;
        private int catSearchTick;

        @Override
        public boolean canUse() {
            return PhantomPhantom.this.getTarget() != null && PhantomPhantom.this.attackPhase == PhantomPhantom.AttackPhase.SWOOP;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity livingentity = PhantomPhantom.this.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (livingentity instanceof Player player && (livingentity.isSpectator() || player.isCreative())) {
                    return false;
                }

                if (!this.canUse()) {
                    return false;
                } else {
                    if (PhantomPhantom.this.tickCount > this.catSearchTick) {
                        this.catSearchTick = PhantomPhantom.this.tickCount + 20;
                        List<Cat> list = PhantomPhantom.this.level()
                                .getEntitiesOfClass(Cat.class, PhantomPhantom.this.getBoundingBox().inflate(16.0), EntitySelector.ENTITY_STILL_ALIVE);

                        for (Cat cat : list) {
                            cat.hiss();
                        }

                        this.isScaredOfCat = !list.isEmpty();
                    }

                    return !this.isScaredOfCat;
                }
            }
        }

        @Override
        public void start() {
        }

        @Override
        public void stop() {
            PhantomPhantom.this.setTarget(null);
            PhantomPhantom.this.attackPhase = PhantomPhantom.AttackPhase.CIRCLE;
        }

        @Override
        public void tick() {
            LivingEntity livingentity = PhantomPhantom.this.getTarget();
            if (livingentity != null) {
                PhantomPhantom.this.moveTargetPoint = new Vec3(livingentity.getX(), livingentity.getY(0.5), livingentity.getZ());
                if (PhantomPhantom.this.getBoundingBox().inflate(0.2F).intersects(livingentity.getBoundingBox())) {
                    if (PhantomPhantom.this.doHurtTarget(livingentity) && Utils.random.nextBoolean()) {
                        livingentity.startRiding(PhantomPhantom.this, true);
                        PhantomPhantom.this.riders.add(livingentity);
                    } else {
                        PhantomPhantom.this.lookAt(livingentity, 30, 30);
                        CastContext cast = SkillcastingManager.buildCastContext(CasterRef.entity(PhantomPhantom.this), SpellRegistry.GUST_SPELL.get().holder(), 10, CastSource.EMPTY);
                        cast.set(SkillcastingComponentTypes.CAST_TIME, 0);
                        SkillcastingManager.initiateCast(CasterRef.entity(PhantomPhantom.this), cast);
                    }
                    PhantomPhantom.this.attackPhase = PhantomPhantom.AttackPhase.CIRCLE;
                    if (!PhantomPhantom.this.isSilent()) {
                        PhantomPhantom.this.level().levelEvent(1039, PhantomPhantom.this.blockPosition(), 0);
                    }
                } else if (PhantomPhantom.this.horizontalCollision || PhantomPhantom.this.hurtTime > 0) {
                    PhantomPhantom.this.attackPhase = PhantomPhantom.AttackPhase.CIRCLE;
                }
            }
        }
    }

    class PhantomPhantomMoveControl extends MoveControl {
        private float speed = (float) PhantomPhantom.this.getAttributeValue(Attributes.MOVEMENT_SPEED);

        public PhantomPhantomMoveControl(Mob mob) {
            super(mob);
        }

        @Override
        public void tick() {
            if (PhantomPhantom.this.horizontalCollision) {
                PhantomPhantom.this.setYRot(PhantomPhantom.this.getYRot() + 180.0F);
                this.speed = (float) PhantomPhantom.this.getAttributeValue(Attributes.MOVEMENT_SPEED);
            }

            double d0 = PhantomPhantom.this.moveTargetPoint.x - PhantomPhantom.this.getX();
            double d1 = PhantomPhantom.this.moveTargetPoint.y - PhantomPhantom.this.getY();
            double d2 = PhantomPhantom.this.moveTargetPoint.z - PhantomPhantom.this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            if (Math.abs(d3) > 1.0E-5F) {
                double d4 = 1.0 - Math.abs(d1 * 0.7F) / d3;
                d0 *= d4;
                d2 *= d4;
                d3 = Math.sqrt(d0 * d0 + d2 * d2);
                double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
                float f = PhantomPhantom.this.getYRot();
                float f1 = (float)Mth.atan2(d2, d0);
                float f2 = Mth.wrapDegrees(PhantomPhantom.this.getYRot() + 90.0F);
                float f3 = Mth.wrapDegrees(f1 * (180.0F / (float)Math.PI));
                PhantomPhantom.this.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
                PhantomPhantom.this.yBodyRot = PhantomPhantom.this.getYRot();
                /*if (Mth.degreesDifferenceAbs(f, PhantomPhantom.this.getYRot()) < 3.0F) {
                    this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                } else {
                    this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
                }*/

                float f4 = (float)(-(Mth.atan2(-d1, d3) * 180.0F / (float)Math.PI));
                PhantomPhantom.this.setXRot(f4);
                float f5 = PhantomPhantom.this.getYRot() + 90.0F;
                double d6 = (double)(this.speed * Mth.cos(f5 * (float) (Math.PI / 180.0))) * Math.abs(d0 / d5);
                double d7 = (double)(this.speed * Mth.sin(f5 * (float) (Math.PI / 180.0))) * Math.abs(d2 / d5);
                double d8 = (double)(this.speed * Mth.sin(f4 * (float) (Math.PI / 180.0))) * Math.abs(d1 / d5);
                Vec3 vec3 = PhantomPhantom.this.getDeltaMovement();
                PhantomPhantom.this.setDeltaMovement(vec3.add(new Vec3(d6, d8, d7).subtract(vec3).scale(0.2)));
            }
        }
    }

    public void shootWindCharge () {
        if (PhantomPhantom.this.getTarget() == null) return;
        Vec3 selfPos = new Vec3(getX(), getY() - 1, getZ());
        Vec3 direction = PhantomPhantom.this.getTarget().position().subtract(selfPos).normalize();
        Vec3 motion = direction.scale(1.7F);

        double spawnOffset = PhantomPhantom.this.getBbWidth() + 0.5;
        Vec3 spawnPos = selfPos.add(direction.scale(spawnOffset));

        WindCharge wind = new WindCharge(
                level(),
                spawnPos.x,
                spawnPos.y,
                spawnPos.z,
                motion
        );

        wind.setOwner(PhantomPhantom.this);
        wind.setDeltaMovement(motion);
        level().addFreshEntity(wind);
    }

    /*private Vec3 calculateInterceptDirection() {
        LivingEntity target = PhantomPhantom.this.getTarget();

        if (target == null) {
            return Vec3.ZERO;
        }

        Vec3 shooterPos = new Vec3(
                PhantomPhantom.this.getX(),
                PhantomPhantom.this.getY() - 1,
                PhantomPhantom.this.getZ()
        );

        Vec3 targetPos = target.position();
        Vec3 targetVelocity = target.getDeltaMovement();

        // Replace this with the actual speed of your projectile.
        double projectileSpeed = 1.0;

        Vec3 r = targetPos.subtract(shooterPos);

        double a = targetVelocity.dot(targetVelocity) - projectileSpeed * projectileSpeed;
        double b = 2.0 * r.dot(targetVelocity);
        double c = r.dot(r);

        double t = 0.0;

        if (Math.abs(a) < 1.0E-6) {
            if (Math.abs(b) > 1.0E-6) {
                t = -c / b;
            }
        } else {
            double discriminant = b * b - 4.0 * a * c;

            if (discriminant >= 0.0) {
                double sqrt = Math.sqrt(discriminant);

                double t1 = (-b - sqrt) / (2.0 * a);
                double t2 = (-b + sqrt) / (2.0 * a);

                t = Math.min(
                        t1 > 0.0 ? t1 : Double.MAX_VALUE,
                        t2 > 0.0 ? t2 : Double.MAX_VALUE
                );

                if (t == Double.MAX_VALUE) {
                    t = 0.0;
                }
            }
        }

        Vec3 predictedPos = targetPos.add(targetVelocity.scale(t));
        return predictedPos.subtract(shooterPos).normalize();
    }*/

}
