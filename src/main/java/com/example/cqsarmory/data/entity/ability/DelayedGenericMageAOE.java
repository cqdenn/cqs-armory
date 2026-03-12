package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.registry.EntityRegistry;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.Vector;

public class DelayedGenericMageAOE extends AoeEntity {

    public DelayedGenericMageAOE(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public DelayedGenericMageAOE(Level level, LivingEntity owner, Vec3 from) {
        this(EntityRegistry.DELAYED_GENERIC_MAGE_AOE_ENTITY.get(), level);
        setLivingOwner(owner);
        setOwner(owner);
        setFrom(from);
        this.setLevel(level);
    }

    Vec3 from;
    LivingEntity livingOwner;

    public void setLivingOwner(LivingEntity livingOwner) {
        this.livingOwner = livingOwner;
    }

    public LivingEntity getLivingOwner () {
        return this.livingOwner;
    }

    public void setFrom(Vec3 from) {
        this.from = from;
    }

    public Vec3 getFrom() {
        return this.from;
    }

    @Override
    public void tick() {

        if (tickCount == 10 && !level().isClientSide) {
            CQtils.doGenericMageAOE(getLivingOwner(), this, getFrom(), 3, 10);
        } else if (tickCount > 10) discard();

    }

    @Override
    public void applyEffect(LivingEntity target) {

    }

    @Override
    public float getParticleCount() {
        return 0;
    }

    @Override
    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }


}
