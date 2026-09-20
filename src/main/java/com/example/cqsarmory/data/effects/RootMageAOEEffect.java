package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.root.RootEntity;
import io.redspace.ironsspellbooks.util.ModTags;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class RootMageAOEEffect extends NonCurableEffect {
    public RootMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.level();
        int radius = 5;
        Vector3f center = new Vector3f(0, 1, 0);
        Vec3 from = livingEntity.position().add(0, 1, 0);
        var entities = level.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(radius), target -> !DamageSources.isFriendlyFireBetween(livingEntity, target) && Utils.hasLineOfSight(level, livingEntity.position(), target.position(), true) && !target.getType().is(ModTags.CANT_ROOT) && !(target instanceof RootEntity) && target != livingEntity);
        entities.forEach(target -> {
            Vec3 spawn = target.position();
            float health = 40;
            //lvl 3 root
            int duration = (int) (CQtils.getAOETimeSeconds(livingEntity) * 20 * livingEntity.getAttributeValue(AttributeRegistry.SPELL_POWER) * livingEntity.getAttributeValue(AttributeRegistry.NATURE_SPELL_POWER));
            RootEntity rootEntity = new RootEntity(level, livingEntity);
            rootEntity.setDuration(duration);
            rootEntity.setTarget(target);
            rootEntity.moveTo(spawn);
            rootEntity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
            rootEntity.setHealth(health);
            level.addFreshEntity(rootEntity);
            target.stopRiding();
            target.startRiding(rootEntity, true);
        });
        if (!livingEntity.level().isClientSide) {
            CQtils.genericMageAOEParticlesServer(livingEntity.level(), radius, center, from);
        }
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return pDuration == 1;
    }
}