package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.ice_tomb.IceTombEntity;
import io.redspace.ironsspellbooks.entity.spells.poison_cloud.PoisonCloud;
import io.redspace.ironsspellbooks.entity.spells.snowball.FrostField;
import io.redspace.ironsspellbooks.entity.spells.snowball.Snowball;
import io.redspace.ironsspellbooks.network.particles.ShockwaveParticlesPacket;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.network.PacketDistributor;

public class IceArrow extends AbilityArrow{

    public IceArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public IceArrow(Level level) {
        this(EntityRegistry.ICE_ARROW.get(), level);
    }

    private static final EntityDataAccessor<Boolean> IS_SPELL_ARROW = SynchedEntityData.defineId(IceArrow.class, EntityDataSerializers.BOOLEAN);

    float aoeDamage;

    public void setSpellArrow (boolean isSpellArrow) {
        if (level().isClientSide) return;
        entityData.set(IS_SPELL_ARROW, isSpellArrow);
        this.setNoGravity(isSpellArrow);
    }

    public boolean getIsSpellArrow () {
        return entityData.get(IS_SPELL_ARROW);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_SPELL_ARROW, false);
    }

    public void setAoeDamage(float damage) {
        this.aoeDamage = damage;
    }

    public float getAoeDamage() {
        return aoeDamage;
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        living.setTicksFrozen(living.getTicksFrozen() + 60);
    }

    @Override
    public void tick() {
        super.tick();
        if (getIsSpellArrow()) {
            if (tickCount == 10) {this.setNoGravity(false);}
            if (this.level().isClientSide) {
                Vec3 vec3 = getDeltaMovement();
                double d0 = this.getX() - vec3.x;
                double d1 = this.getY() - vec3.y;
                double d2 = this.getZ() - vec3.z;
                var count = Mth.clamp((int) (vec3.lengthSqr() * 4), 1, 4);
                for (int i = 0; i < count; i++) {
                    Vec3 random = Utils.getRandomVec3(1).add(vec3.normalize()).scale(0.25);
                    var f = i / ((float) count);
                    var x = Mth.lerp(f, d0, this.getX() + vec3.x);
                    var y = Mth.lerp(f, d1, this.getY() + vec3.y) - .4;
                    var z = Mth.lerp(f, d2, this.getZ() + vec3.z);
                    this.level().addParticle(ParticleHelper.SNOWFLAKE, true, x - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                }
            }

        }
    }

    @Override
    public void customCritParticles() {
        Vec3 vec3 = getDeltaMovement();
        double d0 = this.getX() - vec3.x;
        double d1 = this.getY() - vec3.y;
        double d2 = this.getZ() - vec3.z;
        var count = Mth.clamp((int) (vec3.lengthSqr() * 4), 1, 4);
        for (int i = 0; i < count; i++) {
            Vec3 random = Utils.getRandomVec3(1).add(vec3.normalize()).scale(0.25);
            var f = i / ((float) count);
            var x = Mth.lerp(f, d0, this.getX() + vec3.x);
            var y = Mth.lerp(f, d1, this.getY() + vec3.y) - .4;
            var z = Mth.lerp(f, d2, this.getZ() + vec3.z);
            this.level().addParticle(ParticleHelper.SNOWFLAKE, true, x - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (getIsSpellArrow()) {
            createAoe(result.getLocation());
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        if (target instanceof PartEntity<?> part) target = part.getParent();
        if (getIsSpellArrow() && target instanceof LivingEntity livingEntity) {
            createAoe(result.getLocation());
            livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.CHILLED,  60));
        }
        if (target instanceof IceTombEntity tomb && tomb.hasIndirectPassenger(this)) { //hasIndirectPassenger() returns evil, waiting for mr. 431 to make a real accessor
            float radius = 4;
            Entity entity = getOwner();
            if (entity == null) return;
            MagicManager.spawnParticles(level(), new BlastwaveParticleOptions(SchoolRegistry.ICE.get().getTargetingColor(), radius), tomb.getX(), tomb.getY() + .165f, tomb.getZ(), 1, 0, 0, 0, 0, true);
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new ShockwaveParticlesPacket(new Vec3(tomb.getX(), tomb.getY() + .165f, tomb.getZ()), radius, ParticleRegistry.SNOWFLAKE_PARTICLE.get()));
            level().getEntities(tomb.getFirstPassenger() == null ? tomb : tomb.getFirstPassenger(), tomb.getBoundingBox().inflate(radius, 4, radius), (enemy) ->
                            !DamageSources.isFriendlyFireBetween(enemy, entity)
                                    && Utils.hasLineOfSight(level(), tomb, enemy, true))
                    .forEach(enemy -> {
                        if (enemy instanceof LivingEntity livingEntity && livingEntity.distanceToSqr(tomb) < radius * radius) {
                            livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.CHILLED, 100));
                            MagicManager.spawnParticles(level(), ParticleHelper.SNOWFLAKE, livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * .5f, livingEntity.getZ(), 50, livingEntity.getBbWidth() * .5f, livingEntity.getBbHeight() * .5f, livingEntity.getBbWidth() * .5f, .03, false);
                        }
                    });
        }
        super.onHitEntity(result);
    }

    public void createAoe(Vec3 location) {
        if (!level().isClientSide) {
            FrostField fire = new FrostField(level());
            fire.setOwner(getOwner());
            fire.setDuration(100);
            fire.setRadius(4);
            fire.setCircular();
            fire.moveTo(location);
            level().addFreshEntity(fire);
        }
    }
}
