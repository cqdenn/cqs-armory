package com.example.cqsarmory.items.curios.boosters;

import com.example.cqsarmory.items.curios.BoosterBaseItem;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

public class ExtraAuraBooster extends BoosterBaseItem {
    public ExtraAuraBooster(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();
        CuriosApi.addSlotModifier(modifiers, "brand", id, 2, AttributeModifier.Operation.ADD_VALUE);
        return modifiers;
    }
}