package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class SkirmisherArmorItem extends ExtendedArmorItem {
    public SkirmisherArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.SKIRMISHER, type, properties, genericArcherArmorAttributes(0f, 0.2f, 1, 0f, 0.15f, extraAttribute));
    }
    public SkirmisherArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.SKIRMISHER, type, properties, genericArcherArmorAttributes(0f, 0.2f, 1, 0f, 0.15f));
    }


}
