package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;

public class Tags {
    public static class Items {
        public static final TagKey<Item> MATERIALS_POWER_ONE = tag("ingots/power_one");
        public static final TagKey<Item> MATERIALS_POWER_TWO = tag("ingots/power_two");
        public static final TagKey<Item> MATERIALS_POWER_THREE = tag("ingots/power_three");
        public static final TagKey<Item> ARROWS = tag("arrows");


        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, name));
        }
    }

    public static class DamageTypes {
        public static final TagKey<DamageType> CAUSES_RAGE_GAIN = tag("causes_rage_gain");

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, name));
        }
    }
}
