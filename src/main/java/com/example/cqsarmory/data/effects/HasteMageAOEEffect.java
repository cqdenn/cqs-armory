package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetedAreaEntity;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class HasteMageAOEEffect extends NonCurableEffect {
    public HasteMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    int radius = 5;

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        super.onEffectStarted(livingEntity, amplifier);
        TargetedAreaEntity visualEntity = TargetedAreaEntity.createTargetAreaEntity(livingEntity.level(), livingEntity.position(), radius, 0xf8ff36);
        visualEntity.setDuration(CQtils.getAOETimeSeconds(livingEntity) * 20);
        visualEntity.setOwner(livingEntity);
        visualEntity.setShouldFade(true);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {

        Level level = livingEntity.level();
        int am = (int) (9 * livingEntity.getAttributeValue(AttributeRegistry.SPELL_POWER) * livingEntity.getAttributeValue(AttributeRegistry.BLOOD_SPELL_POWER) * livingEntity.getAttributeValue(AttributeRegistry.EVOCATION_SPELL_POWER));
        var entities = level.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(radius), target -> DamageSources.isFriendlyFireBetween(livingEntity, target) && Utils.hasLineOfSight(level, livingEntity.position(), target.position(), true));
        entities.forEach(target -> {
            //25% hastened * power
            target.addEffect(new MobEffectInstance(MobEffectRegistry.HASTENED, 5, am, false, false, true));
        });
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}