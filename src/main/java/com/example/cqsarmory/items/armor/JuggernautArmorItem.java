package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class JuggernautArmorItem extends ExtendedArmorItem {
    public JuggernautArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.JUGGERNAUT, type, properties, genericMeleeArmorAttributes(0.2f, 0f, 0, 0.0f, 10, 50, extraAttribute));
    }
    public JuggernautArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.JUGGERNAUT, type, properties, genericMeleeArmorAttributes(0.2f, 0f, 0, 0.0f, 10, 50));
    }



}
