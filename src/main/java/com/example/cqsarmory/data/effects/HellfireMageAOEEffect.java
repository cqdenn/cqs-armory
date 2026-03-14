package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.data.entity.ability.HellfireAOEEntity;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.fireball.MagicFireball;
import io.redspace.ironsspellbooks.entity.spells.fireball.SmallMagicFireball;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.spells.fire.FireballSpell;
import net.minecraft.Util;
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

public class HellfireMageAOEEffect extends NonCurableEffect {
    public HellfireMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        float radius = 7;
        Level level = livingEntity.level();
        //float damage = (float) livingEntity.getAttributeValue(AttributeRegistry.MAX_MANA) / 20; //5% of max mana
        float damage = (float) (30 * livingEntity.getAttributeValue(AttributeRegistry.SPELL_POWER) * livingEntity.getAttributeValue(AttributeRegistry.FIRE_SPELL_POWER));
        int total = 6;
        float height = 4;
        var entities = livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.position(), livingEntity.position()).inflate(radius, radius*2, radius));

        for (LivingEntity target : entities) {
            if (!DamageSources.isFriendlyFireBetween(livingEntity, target) && !livingEntity.isSpectator() && DamageData.get(target).hellfireAOETargetingDelay <= 0) {
                var targetX = target.position().x;
                var targetY = target.position().y + height;
                var targetZ = target.position().z;

                HellfireAOEEntity fireball = new HellfireAOEEntity(level, livingEntity);
                fireball.setDamage(damage);
                fireball.setExplosionRadius(1f);
                fireball.setPos(new Vec3(targetX, targetY, targetZ));
                fireball.setDeltaMovement(0, -1, 0);
                level.addFreshEntity(fireball);
                level.playSound(null, targetX, targetY, targetZ, SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.2f, 1);
                total -= 1;
                DamageData.get(target).hellfireAOETargetingDelay = 3;//1/4 of the effect ticks
            } else if (DamageData.get(target).hellfireAOETargetingDelay > 0) {
                DamageData.get(target).hellfireAOETargetingDelay--;
            }
        }

        int rings = 3;
        for (int i = 1; i <= total + total/2; i++) {//adds extra random fireballs
            int randX = Utils.random.nextBoolean() ? 1 : -1;
            int randZ = Utils.random.nextBoolean() ? 1 : -1;
            var x = livingEntity.position().x + (Utils.random.nextFloat() * radius/* * i/rings*/) * randX;
            var y = livingEntity.position().y + height;
            var z = livingEntity.position().z + (Utils.random.nextFloat() * radius/* * i/rings*/) * randZ;

            HellfireAOEEntity fireball = new HellfireAOEEntity(level, livingEntity);
            fireball.setDamage(damage);
            fireball.setExplosionRadius(1f);
            fireball.setPos(new Vec3(x, y, z));
            fireball.setDeltaMovement(0, -1, 0);
            level.addFreshEntity(fireball);
            level.playSound(null, x, y, z, SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.2f, 1);
        }


        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return pDuration % 4 == 0;
    }
}
