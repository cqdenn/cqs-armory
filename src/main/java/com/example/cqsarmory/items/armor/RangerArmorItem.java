package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class RangerArmorItem extends ExtendedArmorItem {
    public RangerArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.RANGER, type, properties, genericArcherArmorAttributes(0f, 0.15f, 1, 0f, 0.1f, extraAttribute));
    }
    public RangerArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.RANGER, type, properties, genericArcherArmorAttributes(0f, 0.15f, 1, 0f, 0.1f));
    }


}
