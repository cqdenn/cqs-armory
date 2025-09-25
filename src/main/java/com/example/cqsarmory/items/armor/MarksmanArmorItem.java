package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class MarksmanArmorItem extends ExtendedArmorItem {
    public MarksmanArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.MARKSMAN, type, properties, genericArcherArmorAttributes(0.2f, 0f, 1, 0.02f, 0, extraAttribute));
    }
    public MarksmanArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.MARKSMAN, type, properties, genericArcherArmorAttributes(0.2f, 0f, 1, 0.02f, 0));
    }


}
