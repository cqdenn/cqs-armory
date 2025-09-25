package com.example.cqsarmory.items.weapons;

import com.example.cqsarmory.items.ExtendedWeaponItem;
import com.example.cqsarmory.registry.ExtendedWeaponTier;
import com.example.cqsarmory.registry.WeaponPower;
import com.example.cqsarmory.registry.WeaponType;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.entity.spells.thrown_spear.ThrownSpear;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Set;

import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE;

public class SpearItem extends ExtendedWeaponItem {
    public WeaponPower power;
    public ExtendedWeaponTier tier;

    public SpearItem(ExtendedWeaponTier tier, WeaponPower power, Properties properties, SpellDataRegistryHolder[] spellDataRegistryHolder) {
        super(tier, properties, spellDataRegistryHolder);
        this.power = power;
        this.tier = tier;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (isTooDamagedToUse(itemstack)) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.LOYALTY);
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            int i = this.getUseDuration(stack, entityLiving) - timeLeft;
            if (i >= 8) {
                if (!isTooDamagedToUse(stack)) {
                    Holder<SoundEvent> holder = EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.TRIDENT_SOUND)
                            .orElse(SoundEvents.TRIDENT_THROW);
                    if (!level.isClientSide) {
                        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(entityLiving.getUsedItemHand()));
                        double damage = getThrownDamage(player);
                        ThrownSpear throwntrident = new ThrownSpear(level, stack, damage);
                        throwntrident.setOwner(player);
                        throwntrident.moveTo(player.getEyePosition());
                        throwntrident.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 0.5F);
                        if (player.hasInfiniteMaterials()) {
                            throwntrident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }
                        level.addFreshEntity(throwntrident);
                        if (!player.hasInfiniteMaterials()) {
                            player.getCooldowns().addCooldown(stack.getItem(), 20 * 10);
                        }
                        level.playSound(null, throwntrident, holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public double getThrownDamage(Player player) {
        double arrowModifier = 1;
        double meleeModifier = 1;
        if (player.getAttribute(BowAttributes.ARROW_DAMAGE).getModifiers() != null) {
            for (AttributeModifier modifier : player.getAttribute(BowAttributes.ARROW_DAMAGE).getModifiers()) {
                switch(modifier.operation()) {
                    case ADD_MULTIPLIED_BASE -> arrowModifier += 1 * modifier.amount();
                    case ADD_MULTIPLIED_TOTAL -> arrowModifier *= 1 + modifier.amount();
                }
            }
        }
        return (1 + power.attackDamage() + tier.getAttackDamageBonus() + WeaponType.SPEAR.attackDamage()) * arrowModifier;
    }

    private static boolean isTooDamagedToUse(ItemStack stack) {
        return stack.getDamageValue() >= stack.getMaxDamage() - 1;
    }
}