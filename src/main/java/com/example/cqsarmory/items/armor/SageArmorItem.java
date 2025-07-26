package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class SageArmorItem extends ExtendedArmorItem {
    public SageArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.SAGE, type, properties, genericMageArmorAttributes(0f, 0.2f, 300, 0f, 0.15f, extraAttribute));
    }
    public SageArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.SAGE, type, properties, genericMageArmorAttributes(0f, 0.2f, 300, 0f, 0.15f));
    }



}
