package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class SeerArmorItem extends ExtendedArmorItem {
    public SeerArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.SEER, type, properties, genericMageArmorAttributes(0f, 0.1f, 100, 0f, 0.05f, extraAttribute));
    }
    public SeerArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.SEER, type, properties, genericMageArmorAttributes(0f, 0.1f, 100, 0f, 0.05f));
    }



}
