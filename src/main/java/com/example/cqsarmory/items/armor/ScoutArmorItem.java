package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class ScoutArmorItem extends ExtendedArmorItem {
    public ScoutArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.SCOUT, type, properties, genericArcherArmorAttributes(0.0f, 0.1f, 1, 0, 0.05f, extraAttribute));
    }
    public ScoutArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.SCOUT, type, properties, genericArcherArmorAttributes(0.0f, 0.1f, 1, 0, 0.05f));
    }


}
