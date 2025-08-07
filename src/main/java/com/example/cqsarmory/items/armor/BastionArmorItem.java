package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class BastionArmorItem extends ExtendedArmorItem {
    public BastionArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.BASTION, type, properties, genericMeleeArmorAttributes(0.f, 0f, 0, 0.0f, 5, 25, extraAttribute));
    }
    public BastionArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.BASTION, type, properties, genericMeleeArmorAttributes(0f, 0f, 0, 0.0f, 5, 25));
    }



}
