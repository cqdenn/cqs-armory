package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.network.SyncRagePacket;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import com.example.cqsarmory.spells.LeapSpell;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.entity.spells.EarthquakeAoe;
import io.redspace.skillcasting.data.selection.SkillSelectionManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class LeapEffect extends NonCurableEffect {
    public LeapEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public double getDamage(Entity target, LivingEntity caster) {
        ItemStack weaponItem = MagicData.get(caster).getCachedCastingEquipmentSlot().equals(SkillSelectionManager.OFFHAND) ? caster.getOffhandItem() : caster.getMainHandItem();
        float damage = (float) caster.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * LeapSpell.WEAPON_DAMAGE_PERCENT;
        var source = CQSpellRegistry.LEAP_SPELL.get().getDamageSource(caster.level(), null, caster);
        if (caster.level() instanceof ServerLevel serverLevel && target != null) {
            return EnchantmentHelper.modifyDamage(serverLevel, weaponItem, target, source, damage);
        }
        return damage;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        MobEffectInstance leap = livingEntity.getEffect(MobEffectRegistry.LEAP);
        int durationRemaining = leap != null ? leap.getDuration() : 0;
        if (LeapSpell.DURATION - durationRemaining > 1 && livingEntity.onGround()) {
            EarthquakeAoe aoeEntity = new EarthquakeAoe(livingEntity.level());
            aoeEntity.moveTo(livingEntity.position());
            aoeEntity.setOwner(livingEntity);
            aoeEntity.setCircular();
            aoeEntity.setRadius(LeapSpell.RADIUS);
            aoeEntity.setDuration(20);
            aoeEntity.setSlownessAmplifier(1);
            livingEntity.level().addFreshEntity(aoeEntity);

            List<Entity> entities = livingEntity.level().getEntities(livingEntity, livingEntity.getBoundingBox().inflate(LeapSpell.RADIUS));
            var damageSource = CQSpellRegistry.LEAP_SPELL.get().getDamageSource(livingEntity.level(), null, livingEntity);

            for (Entity target : entities) {
                if (target instanceof LivingEntity) {
                    if (!(target == livingEntity)) {
                        float damage = (float) getDamage(target, livingEntity);
                        if (target.hurt(damageSource, damage)) {
                            if (livingEntity instanceof Player && !livingEntity.level().isClientSide) {
                                float newRageTest = (AbilityData.get(livingEntity).getRage() + LeapSpell.RAGE_PER_HIT);
                                float newRage = newRageTest < livingEntity.getAttribute(AttributeRegistry.MAX_RAGE).getValue() ? newRageTest : (float) livingEntity.getAttribute(AttributeRegistry.MAX_RAGE).getValue();
                                AbilityData.get(livingEntity).setRage(newRage);
                                PacketDistributor.sendToPlayer((ServerPlayer) livingEntity, new SyncRagePacket((int) newRage));
                            }
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
