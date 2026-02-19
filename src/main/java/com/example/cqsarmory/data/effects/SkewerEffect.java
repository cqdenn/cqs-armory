package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.items.weapons.GreataxeItem;
import com.example.cqsarmory.items.weapons.SpearItem;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.List;

public class SkewerEffect extends MobEffect {
    public SkewerEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public static final float SKEWER_ATTACK_DAMAGE_MULTIPLIER = 1.3f;

    public double getDamage(Entity target, LivingEntity caster) {
        ItemStack weaponItem = caster.getMainHandItem().getItem() instanceof SwordItem || caster.getMainHandItem().getItem() instanceof GreataxeItem ? caster.getMainHandItem() : null;
        if (weaponItem == null) {
            weaponItem = caster.getOffhandItem().getItem() instanceof SwordItem || caster.getOffhandItem().getItem() instanceof GreataxeItem ? caster.getOffhandItem() : null;
        }
        float damage = (float) caster.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * SKEWER_ATTACK_DAMAGE_MULTIPLIER;
        var source = CQSpellRegistry.SKEWER_SPELL.get().getDamageSource(caster);
        if (caster.level() instanceof ServerLevel serverLevel && target != null) {
            return EnchantmentHelper.modifyDamage(serverLevel, weaponItem, target, source, damage);
        }
        return damage;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        List<Entity> list = livingEntity.level().getEntities(livingEntity, livingEntity.getBoundingBox().inflate(1.2));
        var damageSource = CQSpellRegistry.SKEWER_SPELL.get().getDamageSource(livingEntity);
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (entity instanceof LivingEntity target && !DamageSources.isFriendlyFireBetween(livingEntity, target) && Utils.hasLineOfSight(livingEntity.level(), livingEntity.position(), target.getBoundingBox().getCenter(), true)) {
                    float damage = (float) getDamage(target, livingEntity);
                    int duration = 40 * (amplifier + 1);
                    target.hurt(damageSource, damage);
                    target.addEffect(new CQMobEffectInstance(MobEffectRegistry.BLEED, duration, amplifier, false, false, true, livingEntity, true));
                    livingEntity.setDeltaMovement(0, 0, 0);
                    livingEntity.hurtMarked = true;
                    return false;
                }
            }
        } else if (livingEntity.horizontalCollision || livingEntity.verticalCollisionBelow) {
            return false;
        }
        livingEntity.fallDistance = 0;
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
