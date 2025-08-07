package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;

public class ChampionArmorItem extends ExtendedArmorItem {
    public ChampionArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.CHAMPION, type, properties, genericMeleeArmorAttributes(0.2f, 0f, 10, 0.0f, 0, 0, extraAttribute));
    }
    public ChampionArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.CHAMPION, type, properties, genericMeleeArmorAttributes(0.2f, 0f, 10, 0.0f, 0, 0));
    }


}
