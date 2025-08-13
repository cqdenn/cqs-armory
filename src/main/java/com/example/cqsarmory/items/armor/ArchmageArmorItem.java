package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class ArchmageArmorItem extends ExtendedArmorItem {
    public ArchmageArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.ARCHMAGE, type, properties, genericMageArmorAttributes(0.2f, 0f, 300, 0.15f, 0f, extraAttribute));
    }
    public ArchmageArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.ARCHMAGE, type, properties, genericMageArmorAttributes(0.2f, 0f, 300, 0.15f, 0f));
    }



}
