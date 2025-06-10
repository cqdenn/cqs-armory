package com.example.cqsarmory.items;

import com.example.cqsarmory.registry.ExtendedWeaponTier;
import com.example.cqsarmory.registry.WeaponPower;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;


public class ExtendedWeaponItem extends MagicSwordItem {

    public static final ResourceLocation BASE_ARMOR_ID = ResourceLocation.withDefaultNamespace("base_armor");
    private static final ResourceLocation BASE_MAX_HEALTH_ID = ResourceLocation.withDefaultNamespace("base_max_health");

    public ExtendedWeaponItem(Tier tier, Item.Properties properties, SpellDataRegistryHolder[] spellDataRegistryHolder) {
        super(tier, properties, spellDataRegistryHolder);
    }

    public static Tool createToolProperties() {
        return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F), Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
    }

    /**
     * Neo: Method overload to allow giving a float for damage instead of an int.
     */
    public static ItemAttributeModifiers createAttributes(ExtendedWeaponTier material, WeaponPower power, float attackDamage, float attackSpeed, AttributeContainer[] attributes) {



        var builder = ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, (double)((float)attackDamage + material.getAttackDamageBonus() + power.attackDamage()), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, (double)attackSpeed + material.getSpeed() + power.attackSpeed(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                );

        for (AttributeContainer holder : attributes) {
            builder.add(holder.attribute(), holder.createModifier(EquipmentSlot.MAINHAND.getName()), EquipmentSlotGroup.MAINHAND);
        }
        return builder.build();
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise the damage on the stack.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return net.neoforged.neoforge.common.ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
    }
}

