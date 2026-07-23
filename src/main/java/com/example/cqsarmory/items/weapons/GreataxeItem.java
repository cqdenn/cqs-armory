package com.example.cqsarmory.items.weapons;

import com.example.cqsarmory.registry.ExtendedWeaponTier;
import com.example.cqsarmory.registry.WeaponPower;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import io.redspace.skillcasting.data.skill.ISkillContainer;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class GreataxeItem extends TieredItem {

    public GreataxeItem(Tier tier, Properties properties, SkillData... spellDataRegistryHolders) {
        super(tier, properties.component(ComponentRegistry.IMBUED_SPELL_CONTAINER, ISkillContainer.create(false, spellDataRegistryHolders)));
    }

    public static Tool createGreataxeToolProperties(ExtendedWeaponTier material, WeaponPower power) {

        List<Tool.Rule> rules = new ArrayList<>();

        // ----- Axe rules -----
        rules.add(Tool.Rule.deniesDrops(material.getIncorrectBlocksForDrops()));
        rules.add(Tool.Rule.minesAndDrops(BlockTags.MINEABLE_WITH_AXE, material.getMiningSpeed()));

        // ----- Sword rules -----
        rules.add(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F));
        rules.add(Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F));

        return new Tool(rules, 1.0F, 1);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.EFFICIENCY);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

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
