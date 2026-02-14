package com.example.cqsarmory.utils;

import net.minecraft.world.item.Item;

public class CQItemPropertyHelper {

    public static Item.Properties weaponsetItem(boolean fireRes) {
        if (fireRes) {
            return new Item.Properties().fireResistant();
        }
        return new Item.Properties();
    }

}
