package com.example.cqsarmory.items.curios;

import com.example.cqsarmory.CqsArmory;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class BoosterBeadsItem extends CurioBaseItem {
    public BoosterBeadsItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        CuriosApi.addSlotModifier(stack, "booster", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "booster_beads"), 1, AttributeModifier.Operation.ADD_VALUE, "necklace");
        super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        CuriosApi.addSlotModifier(stack, "booster", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "booster_beads"), -1, AttributeModifier.Operation.ADD_VALUE, "necklace");
        super.onUnequip(slotContext, newStack, stack);
    }
}
