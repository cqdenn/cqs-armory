package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class Tags {
    public static class Items {
        public static final TagKey<Item> MATERIALS_POWER_ONE = tag("ingots/power_one");
        public static final TagKey<Item> MATERIALS_POWER_TWO = tag("ingots/power_two");
        public static final TagKey<Item> MATERIALS_POWER_THREE = tag("ingots/power_three");


        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, name));
        }
    }
}
