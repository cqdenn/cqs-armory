package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class TrackerArmorItem extends ExtendedArmorItem {
    public TrackerArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.TRACKER, type, properties, genericArcherArmorAttributes(0.15f, 0.10f, 1, 0.025f, extraAttribute));
    }
    public TrackerArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.TRACKER, type, properties, genericArcherArmorAttributes(0.15f, 0.10f, 1, 0.025f));
    }


}
