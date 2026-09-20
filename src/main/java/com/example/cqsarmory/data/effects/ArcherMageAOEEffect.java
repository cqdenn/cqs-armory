package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.registry.AttributeRegistry;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.spells.small_magic_arrow.SmallMagicArrow;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ArcherMageAOEEffect extends NonCurableEffect {
    public ArcherMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {

        Level level = livingEntity.level();
        int degPerArrow = 5;
        int arrows = 360 / degPerArrow;
        Vec3 dir = new Vec3(1, 0.2, 0);
        Vec3 spawn = livingEntity.position().add(0, 1, 0);
        for (int i = 0; i < arrows; i++) {
            SmallMagicArrow arrow = new SmallMagicArrow(level, livingEntity);
            arrow.setPos(spawn);
            arrow.shoot(dir.x, dir.y, dir.z, 1f, 0f);
            arrow.setOwner(livingEntity);
            arrow.setDamage((float) Math.max(10, livingEntity.getAttributeValue(BowAttributes.ARROW_DAMAGE) * livingEntity.getAttributeValue(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER)));
            arrow.setPierceLevel((int) livingEntity.getAttributeValue(AttributeRegistry.ARROW_PIERCING));
            level.addFreshEntity(arrow);
            dir = dir.yRot(degPerArrow * Mth.DEG_TO_RAD);
            if (level instanceof ServerLevel serverLevel) {
                MagicManager.spawnParticles(serverLevel, ParticleTypes.FIREWORK, spawn.x, spawn.y, spawn.z, 2, .1, .1, .1, .05, false);
            }
        }
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return pDuration == 1;
    }
}