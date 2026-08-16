package com.example.cqsarmory.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class TwoLineDescriptiveItem extends Item {
    public TwoLineDescriptiveItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext context, List<Component> lines, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, context, lines, pIsAdvanced);
        lines.add(Component.translatable(String.format("%s.line1", this.getDescriptionId())).withStyle(ChatFormatting.GRAY));
        lines.add(Component.translatable(String.format("%s.line2", this.getDescriptionId())).withStyle(ChatFormatting.DARK_GRAY));
    }
}
