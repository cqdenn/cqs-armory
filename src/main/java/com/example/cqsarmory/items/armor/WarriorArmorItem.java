package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class WarriorArmorItem extends ExtendedArmorItem {
    public WarriorArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.WARRIOR, type, properties, genericMeleeArmorAttributes(0.1f, 0.0f, 5, 0.01f, 0, 0, extraAttribute));
    }
    public WarriorArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.WARRIOR, type, properties, genericMeleeArmorAttributes(0.1f, 0.0f, 5, 0.01f, 0, 0));
    }



}
