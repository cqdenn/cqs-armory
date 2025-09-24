package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.FireworkProjectile;
import com.example.cqsarmory.items.curios.QuiverItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PoisonQuiver extends QuiverItem {
    final @Nullable String slotIdentifier;
    Style descriptionStyle;
    boolean showHeader;

    public PoisonQuiver(Properties properties, String slotIdentifier, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, spellDataRegistryHolders);
        this.slotIdentifier = slotIdentifier;
        this.showHeader = true;
        descriptionStyle = Style.EMPTY.withColor(ChatFormatting.YELLOW);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        AbilityArrow abilityArrow = new AbilityArrow(shooter.level());
        abilityArrow.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
        abilityArrow.copyStats(arrow, shooter, arrowDmg);
        return abilityArrow;
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
