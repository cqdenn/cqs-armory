package com.example.cqsarmory.utils;

import com.example.cqsarmory.registry.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Objects;
import java.util.Optional;

public class WeaponsetMaterials {

    public static Item getIngotReplacement (ItemRegistry.Weaponset weaponset) {
        if (weaponset.ingot().isPresent()) {
            return weaponset.ingot().get().asItem();
        }
        else {
            String name = weaponset.greataxe().asItem().toString().split(":")[1].split("_")[0];
            if (Objects.equals(name, "diamond")) {
                return Items.DIAMOND;
            }
            else if (Objects.equals(name, "netherite")) {
                return Items.NETHERITE_INGOT;
            }
            else if (Objects.equals(name, "iron")) {
                return Items.IRON_INGOT;
            }
            else if (Objects.equals(name, "gold")) {
                return Items.GOLD_INGOT;
            }
            else if (Objects.equals(name, "copper")) {
                return Items.COPPER_INGOT;
            }
            else if (Objects.equals(name, "obsidian")) {
                return Items.OBSIDIAN;
            } else {
                return Items.AMETHYST_SHARD;
            }
        }
    }
}
