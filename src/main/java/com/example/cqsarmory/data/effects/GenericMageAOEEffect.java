package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3f;

import java.util.List;

public class GenericMageAOEEffect extends MobEffect {
    public GenericMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        float radius = 5;
        Vector3f center = new Vector3f(1, 1f, 1f);
        var x = livingEntity.position().x;
        var y = livingEntity.position().y;
        var z = livingEntity.position().z;
        Level level = livingEntity.level();
        var damageSource = SpellRegistry.MAGIC_MISSILE_SPELL.get().getDamageSource(livingEntity);
        float damage = (float) livingEntity.getAttributeValue(AttributeRegistry.MAX_MANA) / 40; //2.5% of max mana
        var entities = level.getEntities(livingEntity, new AABB(livingEntity.position(), livingEntity.position()).inflate(radius, 1, radius), (targeted) -> !DamageSources.isFriendlyFireBetween(livingEntity, targeted) && Utils.hasLineOfSight(level, livingEntity.position(), targeted.position(), true));

        level.addParticle(new BlastwaveParticleOptions(center, radius), x, y + .165f, z, 0, 0, 0);
        level.addParticle(new BlastwaveParticleOptions(center, radius), x, y + .135f, z, 0, 0, 0);
        level.addParticle(new BlastwaveParticleOptions(center, radius * 1.02f), x, y + .135f, z, 0, 0, 0);
        level.addParticle(new BlastwaveParticleOptions(center, radius * 0.98f), x, y + .135f, z, 0, 0, 0);

        for (Entity target : entities) {
            if (target instanceof LivingEntity) {
                target.hurt(damageSource, damage);
            }
        }
        entities.clear();

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return pDuration % 20 == 0;
    }
}
