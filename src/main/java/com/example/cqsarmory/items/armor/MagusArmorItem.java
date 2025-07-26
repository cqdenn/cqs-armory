package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class MagusArmorItem extends ExtendedArmorItem {
    public MagusArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.MAGUS, type, properties, genericMageArmorAttributes(0.15f, 0f, 100, 0.1f, 0f, extraAttribute));
    }
    public MagusArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.MAGUS, type, properties, genericMageArmorAttributes(0.15f, 0f, 100, 0.1f, 0f));
    }



}
