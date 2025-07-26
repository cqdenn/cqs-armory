package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class RampartArmorItem extends ExtendedArmorItem {
    public RampartArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.RAMPART, type, properties, genericMeleeArmorAttributes(0.1f, 0f, 0, 0.0f, 2, 10, extraAttribute));
    }
    public RampartArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.RAMPART, type, properties, genericMeleeArmorAttributes(0.1f, 0f, 0, 0.0f, 2, 10));
    }



}
