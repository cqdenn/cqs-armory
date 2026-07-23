package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import top.theillusivec4.curios.api.SlotAttribute;

public class ChampionArmorItem extends ExtendedArmorItem {

    private static final ResourceLocation ARMOR_ID =
            ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "champion_armor_coating_slot");

    ArmorItem.Type type;

    public ChampionArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.CHAMPION, type, properties, genericMeleeArmorAttributes(0f, 0f, 5, 0, 0.0f, 0, 0, extraAttribute));
        this.type = type;
    }
    public ChampionArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.CHAMPION, type, properties, genericMeleeArmorAttributes(0f, 0f, 5, 0, 0.0f, 0, 0));
        this.type = type;
    }


    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        ItemAttributeModifiers modifiers = super.getDefaultAttributeModifiers(stack);
        if (this.type == Type.CHESTPLATE) {
            Holder<Attribute> slotAttribute = SlotAttribute.getOrCreate("coating");
            AttributeModifier modifier = new AttributeModifier(ARMOR_ID, 1.0, AttributeModifier.Operation.ADD_VALUE);
            modifiers = modifiers.withModifierAdded(slotAttribute, modifier, EquipmentSlotGroup.CHEST);
        }
        return modifiers;
    }
}
