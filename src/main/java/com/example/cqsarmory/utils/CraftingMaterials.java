package com.example.cqsarmory.utils;

import com.example.cqsarmory.items.armor.*;
import com.example.cqsarmory.registry.ItemRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Objects;

public class CraftingMaterials {

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

    public static Item getArmorMaterial (ArmorItem armorItem) {
        if (armorItem instanceof HunterArmorItem) {
            return Items.ARROW;
        }
        else if (armorItem instanceof ScoutArmorItem) {
            return Items.FEATHER;
        }
        else if (armorItem instanceof WarriorArmorItem) {
            return Items.GOLD_INGOT;
        }
        else if (armorItem instanceof RampartArmorItem) {
            return Items.IRON_INGOT;
        }
        else if (armorItem instanceof ApprenticeArmorItem) {
            return io.redspace.ironsspellbooks.registries.ItemRegistry.ARCANE_ESSENCE.get();
        }
        else {
            return io.redspace.ironsspellbooks.registries.ItemRegistry.DIVINE_PEARL.get();
        }
    }

    public static Item getSmithingDowngrade (ArmorItem armorItem) {
        var slot = armorItem.getEquipmentSlot();
        if (armorItem instanceof TrackerArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.HUNTER_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.HUNTER_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.HUNTER_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.HUNTER_BOOTS.get();
            }
        }
        else if (armorItem instanceof MarksmanArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.TRACKER_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.TRACKER_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.TRACKER_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.TRACKER_BOOTS.get();
            }
        }
        else if (armorItem instanceof RangerArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.SCOUT_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.SCOUT_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.SCOUT_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.SCOUT_BOOTS.get();
            }
        }
        else if (armorItem instanceof SkirmisherArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.RANGER_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.RANGER_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.RANGER_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.RANGER_BOOTS.get();
            }
        }
        else if (armorItem instanceof SoldierArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.WARRIOR_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.WARRIOR_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.WARRIOR_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.WARRIOR_BOOTS.get();
            }
        }
        else if (armorItem instanceof ChampionArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.SOLDIER_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.SOLDIER_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.SOLDIER_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.SOLDIER_BOOTS.get();
            }
        }
        else if (armorItem instanceof BastionArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.RAMPART_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.RAMPART_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.RAMPART_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.RAMPART_BOOTS.get();
            }
        }
        else if (armorItem instanceof JuggernautArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.BASTION_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.BASTION_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.BASTION_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.BASTION_BOOTS.get();
            }
        }
        else if (armorItem instanceof MagusArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.APPRENTICE_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.APPRENTICE_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.APPRENTICE_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.APPRENTICE_BOOTS.get();
            }
        }
        else if (armorItem instanceof ArchmageArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.MAGUS_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.MAGUS_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.MAGUS_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.MAGUS_BOOTS.get();
            }
        }
        else if (armorItem instanceof MysticArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.SEER_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.SEER_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.SEER_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.SEER_BOOTS.get();
            }
        }
        else if (armorItem instanceof SageArmorItem) {
            if (slot == EquipmentSlot.HEAD) {
                return ItemRegistry.MYSTIC_HELMET.get();
            }
            else if (slot == EquipmentSlot.CHEST) {
                return ItemRegistry.MYSTIC_CHESTPLATE.get();
            }
            else if (slot == EquipmentSlot.LEGS) {
                return ItemRegistry.MYSTIC_LEGGINGS.get();
            }
            else if (slot == EquipmentSlot.FEET) {
                return ItemRegistry.MYSTIC_BOOTS.get();
            }
        }
        return Items.ACACIA_TRAPDOOR;
    }
}
