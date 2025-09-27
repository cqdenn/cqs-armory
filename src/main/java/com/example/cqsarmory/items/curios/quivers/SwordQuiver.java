package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.ThrownItemProjectile;
import com.example.cqsarmory.items.curios.QuiverItem;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfileKeyPair;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SwordQuiver extends QuiverItem {
    final @Nullable String slotIdentifier;
    Style descriptionStyle;
    boolean showHeader;
    public SwordQuiver(Properties properties, String slotIdentifier, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, spellDataRegistryHolders);
        this.slotIdentifier = slotIdentifier;
        this.showHeader = true;
        descriptionStyle = Style.EMPTY.withColor(ChatFormatting.YELLOW);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        double newDamage = (arrowDmg * 0.5) + ((shooter.getAttributeValue(Attributes.ATTACK_DAMAGE) - 1) * 0.75);
        ThrownItemProjectile projectile = new ThrownItemProjectile(shooter.level(), new ItemStack(Items.IRON_SWORD), 0.1, 0.75);
        projectile.copyStats(arrow, shooter, (float) newDamage);
        return projectile;
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
