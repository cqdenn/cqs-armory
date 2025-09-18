package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.entity.spells.poison_cloud.PoisonCloud;
import io.redspace.ironsspellbooks.entity.spells.snowball.FrostField;
import io.redspace.ironsspellbooks.entity.spells.snowball.Snowball;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class IceArrow extends AbilityArrow{

    public IceArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public IceArrow(Level level) {
        this(EntityRegistry.ICE_ARROW.get(), level);
    }

    boolean isSpellArrow;
    float aoeDamage;

    public void setAoeDamage(float damage) {
        this.aoeDamage = damage;
    }

    public float getAoeDamage() {
        return aoeDamage;
    }

    public boolean isSpellArrow() {
        return isSpellArrow;
    }

    public void setSpellArrow(boolean isSpellArrow) {
        this.isSpellArrow = isSpellArrow;
        this.setNoGravity(isSpellArrow);
    }

    @Override
    public double getBaseDamage() {
        if (this.getOwner() instanceof LivingEntity owner) {
            return super.getBaseDamage() * owner.getAttributeValue(AttributeRegistry.ICE_SPELL_POWER);
        }
        return super.getBaseDamage();
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        living.setTicksFrozen(living.getTicksFrozen() + 400);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isSpellArrow) {
            if (tickCount == 10) {this.setNoGravity(false);}
            if (this.level().isClientSide) {
                Vec3 vec3 = this.position().subtract(getDeltaMovement().scale(2));
                level().addParticle(ParticleHelper.ICY_FOG, vec3.x, vec3.y, vec3.z, 0, 0, 0);
            }

        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (this.isSpellArrow) {
            createAoe(result.getLocation());
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.isSpellArrow && result.getEntity() instanceof LivingEntity livingEntity) {
            createAoe(result.getLocation());
            livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.CHILLED,  60));
        }
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
