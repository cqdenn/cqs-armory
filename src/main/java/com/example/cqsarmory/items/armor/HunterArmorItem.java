package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class HunterArmorItem extends ExtendedArmorItem {
    public HunterArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.HUNTER, type, properties, genericArcherArmorAttributes(0.1f, 0, 0, 1, 0.01f, 0, extraAttribute));
    }
    public HunterArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.HUNTER, type, properties, genericArcherArmorAttributes(0.1f, 0, 0, 1, 0.01f, 0));
    }


}
