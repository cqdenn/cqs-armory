package com.example.cqsarmory.data.effects;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.fireball.MagicFireball;
import io.redspace.ironsspellbooks.entity.spells.fireball.SmallMagicFireball;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.spells.fire.FireballSpell;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class HellfireMageAOEEffect extends MobEffect {
    public HellfireMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        float radius = 5;
        int randX = Utils.random.nextBoolean() ? 1 : -1;
        int randZ = Utils.random.nextBoolean() ? 1 : -1;
        var x = livingEntity.position().x + (Utils.random.nextFloat() * radius) * randX;
        var y = livingEntity.position().y + 4;
        var z = livingEntity.position().z + (Utils.random.nextFloat() * radius) * randZ;
        Level level = livingEntity.level();
        //float damage = (float) livingEntity.getAttributeValue(AttributeRegistry.MAX_MANA) / 20; //5% of max mana
        float damage = (float) (30 * livingEntity.getAttributeValue(AttributeRegistry.SPELL_POWER) * livingEntity.getAttributeValue(AttributeRegistry.FIRE_SPELL_POWER));

        SmallMagicFireball fireball = new SmallMagicFireball(level, livingEntity);
        fireball.setDamage(damage);
        fireball.setExplosionRadius(1f);
        fireball.setPos(new Vec3(x, y, z));
        fireball.setDeltaMovement(0, -1, 0);
        level.addFreshEntity(fireball);
        level.playSound(null, x, y, z, SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.2f, 1);


        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return pDuration % 2 == 0;
    }
}
