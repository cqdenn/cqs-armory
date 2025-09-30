package com.example.cqsarmory.items.curios;

import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SimpleDescriptiveQuiver extends QuiverItem{
    final @Nullable String slotIdentifier;
    Style descriptionStyle;
    boolean showHeader;
    public SimpleDescriptiveQuiver(Properties properties, String slotIdentifier, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, spellDataRegistryHolders);
        this.slotIdentifier = slotIdentifier;
        this.showHeader = true;
        descriptionStyle = Style.EMPTY.withColor(ChatFormatting.YELLOW);
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext tooltipContext, ItemStack stack) {
        var attrTooltip = super.getAttributesTooltip(tooltips, tooltipContext, stack);
        boolean needHeader = attrTooltip.isEmpty();
        var descriptionLines = getDescriptionLines(stack);
        if (needHeader && !descriptionLines.isEmpty()) {
            attrTooltip.add(Component.empty());
            attrTooltip.add(Component.translatable("curios.modifiers." + slotIdentifier).withStyle(ChatFormatting.GOLD));
        }
        attrTooltip.addAll(descriptionLines);

        return attrTooltip;
    }

    public List<Component> getDescriptionLines(ItemStack stack) {
        return List.of(getDescription(stack));
    }

    public Component getDescription(ItemStack stack) {
        return Component.literal(" ").append(Component.translatable(this.getDescriptionId() + ".desc")).withStyle(descriptionStyle);
    }
}
