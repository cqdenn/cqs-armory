package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class ApprenticeArmorItem extends ExtendedArmorItem {
    public ApprenticeArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.APPRENTICE, type, properties, genericMageArmorAttributes(0.1f, 0f, 100, 0.05f, 0f, extraAttribute));
    }
    public ApprenticeArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.APPRENTICE, type, properties, genericMageArmorAttributes(0.1f, 0f, 100, 0.05f, 0f));
    }



}
