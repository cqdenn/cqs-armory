package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import com.example.cqsarmory.utils.CQRaycaster;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;

import java.util.List;

public class HitscanArcaneBeam extends AbilityArrow  implements IEntityWithComplexSpawn {
    public HitscanArcaneBeam(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public HitscanArcaneBeam(Level level, Vec3 start, Vec3 end, float originalDistance, Entity owner, List<HitResult> raycast, int bouncesLeft) {
        this(EntityRegistry.HITSCAN_ARCANE_BEAM.get(), level);
        this.setOwner(owner);
        this.setPos(start);
        this.distance = (float) start.distanceTo(end);
        this.originalDistance = originalDistance;
        this.raycast = raycast;
        this.bouncesLeft = bouncesLeft;
        this.angle = end.subtract(start).normalize();
    }

    public static final int lifetime = 16;
    public float distance;
    public float originalDistance;
    public List<HitResult> raycast;
    public int bouncesLeft;
    public Vec3 angle;

    @Override
    public void tick() {
        //super.tick();
        this.setDeltaMovement(Vec3.ZERO);
        if (tickCount == 1) {
            if (level().isClientSide) {
                /*var forward = getOwner().getForward();
                for (float i = 1; i < distance; i += .5f) {
                    Vec3 pos = position().add(forward.scale(i));
                    level().addParticle(ParticleRegistry.UNSTABLE_ENDER_PARTICLE.get(), false, pos.x, pos.y, pos.z, 0, 0, 0);
                }*/
            } else {
                if (this.raycast.get(0).getType() == HitResult.Type.ENTITY) {
                    EntityHitResult result = (EntityHitResult) raycast.get(0);
                    if (this.canHitEntity(result.getEntity())) {
                        onHitEntity(result);
                    }
                    if (getPierceLevel() > 0) {
                        for (int i = 1; i<=Math.min(getPierceLevel(), this.raycast.size() - 1); i++) {
                            if (raycast.get(i).getType() == HitResult.Type.BLOCK) {
                                BlockHitResult blockHitResult = (BlockHitResult) raycast.get(i);
                                onHitBlock(blockHitResult);
                            }
                        }
                    }
                } else if (this.raycast.get(0).getType() == HitResult.Type.BLOCK) {
                    BlockHitResult result = (BlockHitResult) raycast.get(0);
                    onHitBlock(result);
                }
            }
        } else if (tickCount > lifetime) {
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {

        if (this.bouncesLeft > 0) {
            Vec3 normal = Vec3.atLowerCornerOf(result.getDirection().getNormal());
            Vec3 newAngle = this.angle.subtract(normal.scale((this.angle.dot(normal) * 2)));
            //newAngle = new Vec3(0, 1, 0);

            Vec3 location = result.getLocation().subtract(this.angle.scale(0.0));
            var hitResult = CQRaycaster.begin(level(), getOwner())
                    .rangeFromStart(originalDistance, location, newAngle)
                    .checkForBlocks(true)
                    .bbInflation(.15f)
                    .buildList();
            int pierce = getPierceLevel();
            Vec3 end = hitResult.get(Math.min(pierce, hitResult.size() - 1)).getLocation();//list is never empty, see CQRaycaster
            HitscanArcaneBeam beam = new HitscanArcaneBeam(level(), location, end, originalDistance, getOwner(), hitResult, this.bouncesLeft - 1);
            Vec3 vec3 = newAngle;
            double d0 = vec3.horizontalDistance();
            beam.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * 180.0F / (float) Math.PI));
            beam.setXRot((float) (Mth.atan2(vec3.y, d0) * 180.0F / (float) Math.PI));
            beam.yRotO = beam.getYRot();
            beam.xRotO = beam.getXRot();
            beam.setBaseDamage(this.getBaseDamage());
            beam.setScale(this.getScale());
            beam.setShotFromAbility(this.getShotFromAbility());
            beam.setWeaponItem(this.getWeaponItem());
            level().addFreshEntity(beam);
            MagicManager.spawnParticles(level(), ParticleHelper.ENDER_SPARKS, result.getLocation().x, result.getLocation().y, result.getLocation().z, 10, 0.1, 0.1, 0.1, 0.2, false);
        } else {
            MagicManager.spawnParticles(level(), ParticleHelper.UNSTABLE_ENDER, result.getLocation().x, result.getLocation().y, result.getLocation().z, 50, 0, 0, 0, .3, false);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        //super.onHitEntity(result);
        Entity entity = result.getEntity();
        boolean flag = entity.getType() == EntityType.ENDERMAN;
        if (this.isOnFire() && !flag) {
            entity.igniteForSeconds(5.0F);
        }

        //first hit
        if (!this.level().isClientSide) {
            entity.hurt(damageSources().arrow(this, getOwner()), (float) getBaseDamage());
        }
        if (this.getPierceLevel() > 0) {
            for (int i = 1; i<=Math.min(getPierceLevel(), this.raycast.size() - 1); i++) {
                if (this.raycast.get(i).getType() == HitResult.Type.ENTITY && ((EntityHitResult) this.raycast.get(i)).getEntity() instanceof LivingEntity target) {
                    target.hurt(damageSources().arrow(this, getOwner()), (float) getBaseDamage());
                }
            }
        }
        MagicManager.spawnParticles(level(), ParticleHelper.UNSTABLE_ENDER, result.getLocation().x, result.getLocation().y, result.getLocation().z, 50, 0, 0, 0, .3, false);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isCritArrow() {
        return false;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean shouldRender(double pX, double pY, double pZ) {
        return true;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt((int) (distance * 10));
        buffer.writeFloat(this.getYRot());
        buffer.writeFloat(this.getXRot());
        buffer.writeFloat(this.yRotO);
        buffer.writeFloat(this.xRotO);
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
        this.distance = additionalData.readInt() / 10f;
        this.setYRot(additionalData.readFloat());
        this.setXRot(additionalData.readFloat());
        this.yRotO = additionalData.readFloat();
        this.xRotO = additionalData.readFloat();
    }
}
