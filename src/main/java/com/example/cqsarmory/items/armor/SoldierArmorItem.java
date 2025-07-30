package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class SoldierArmorItem extends ExtendedArmorItem {
    public SoldierArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.SOLDIER, type, properties, genericMeleeArmorAttributes(0.15f, 0.0f, 10, 0.01f, 0, 0, extraAttribute));
    }
    public SoldierArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.SOLDIER, type, properties, genericMeleeArmorAttributes(0.15f, 0.0f, 10, 0.01f, 0, 0));
    }


}
