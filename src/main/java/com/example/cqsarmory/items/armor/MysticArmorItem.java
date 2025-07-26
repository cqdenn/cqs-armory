package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class MysticArmorItem extends ExtendedArmorItem {
    public MysticArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.MYSTIC, type, properties, genericMageArmorAttributes(0f, 0.15f, 200, 0f, 0.1f, extraAttribute));
    }
    public MysticArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.MYSTIC, type, properties, genericMageArmorAttributes(0f, 0.15f, 200, 0f, 0.1f));
    }



}
