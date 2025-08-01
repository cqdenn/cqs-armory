package com.example.cqsarmory.data;

import com.example.cqsarmory.items.armor.*;
import com.example.cqsarmory.registry.ItemRegistry;
import com.example.cqsarmory.utils.CraftingMaterials;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.concurrent.CompletableFuture;

public class RecipeDataGenerator extends RecipeProvider {
    public RecipeDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput);

        for (DeferredHolder item : ItemRegistry.ITEMS.getEntries()) {
            if (item.get() instanceof ArmorItem armor) {
                var slot = armor.getEquipmentSlot();
                var material = CraftingMaterials.getArmorMaterial(armor);

                if (armor instanceof HunterArmorItem || armor instanceof ScoutArmorItem || armor instanceof WarriorArmorItem || armor instanceof RampartArmorItem || armor instanceof ApprenticeArmorItem || armor instanceof  SeerArmorItem) {

                    if (slot == EquipmentSlot.HEAD) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, armor)
                                .pattern("xxx")
                                .pattern("xax")
                                .pattern("xxx")
                                .define('x', material)
                                .define('a', Items.IRON_HELMET)
                                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                                .save(recipeOutput);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, armor)
                                .pattern("xxx")
                                .pattern("xax")
                                .pattern("xxx")
                                .define('x', material)
                                .define('a', Items.IRON_CHESTPLATE)
                                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                                .save(recipeOutput);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, armor)
                                .pattern("xxx")
                                .pattern("xax")
                                .pattern("xxx")
                                .define('x', material)
                                .define('a', Items.IRON_LEGGINGS)
                                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                                .save(recipeOutput);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, armor)
                                .pattern("xxx")
                                .pattern("xax")
                                .pattern("xxx")
                                .define('x', material)
                                .define('a', Items.IRON_BOOTS)
                                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                                .save(recipeOutput);
                    }

                }
                else {
                    if (armor instanceof TrackerArmorItem || armor instanceof RangerArmorItem || armor instanceof SoldierArmorItem || armor instanceof BastionArmorItem || armor instanceof MagusArmorItem || armor instanceof MysticArmorItem) {
                        var downgrade = CraftingMaterials.getSmithingDowngrade(armor);
                        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND), Ingredient.of(downgrade), Ingredient.of(Items.DIAMOND), RecipeCategory.MISC, armor)
                                .unlocks("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                                .save(recipeOutput, armor + "_smithing");
                    }
                    if (armor instanceof MarksmanArmorItem || armor instanceof SkirmisherArmorItem || armor instanceof ChampionArmorItem || armor instanceof JuggernautArmorItem || armor instanceof ArchmageArmorItem || armor instanceof SageArmorItem) {
                        var downgrade = CraftingMaterials.getSmithingDowngrade(armor);
                        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND), Ingredient.of(downgrade), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.MISC, armor)
                                .unlocks("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                                .save(recipeOutput, armor + "_smithing");
                    }
                }
            }
        }

        for (ItemRegistry.Weaponset weaponset : ItemRegistry.WEAPONSETS) {
            var material = CraftingMaterials.getIngotReplacement(weaponset);
            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.warhammer().asItem())
                    .pattern("xxx")
                    .pattern("xsx")
                    .pattern(" s ")
                    .define('x', material)
                    .define('s', Items.STICK)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.greatsword().asItem())
                    .pattern(" x ")
                    .pattern("xxx")
                    .pattern(" s ")
                    .define('x', material)
                    .define('s', Items.STICK)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.halberd().asItem())
                    .pattern(" xx")
                    .pattern(" sx")
                    .pattern("s  ")
                    .define('x', material)
                    .define('s', Items.STICK)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.scythe().asItem())
                    .pattern("xxx")
                    .pattern(" sx")
                    .pattern("s  ")
                    .define('x', material)
                    .define('s', Items.STICK)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.mace().asItem())
                    .pattern(" xx")
                    .pattern(" xx")
                    .pattern("s  ")
                    .define('x', material)
                    .define('s', Items.STICK)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.spear().asItem())
                    .pattern("  x")
                    .pattern(" s ")
                    .pattern("s  ")
                    .define('x', material)
                    .define('s', Items.STICK)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.rapier().asItem())
                    .pattern("x")
                    .pattern("s")
                    .define('x', material)
                    .define('s', Items.STICK)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.greataxe().asItem())
                    .pattern("xsx")
                    .pattern("xsx")
                    .pattern(" s ")
                    .define('x', material)
                    .define('s', Items.STICK)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.shortbow().asItem())
                    .pattern(" sS")
                    .pattern("x S")
                    .pattern(" sS")
                    .define('x', material)
                    .define('s', Items.STICK)
                    .define('S', Items.STRING)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.recurve().asItem())
                    .pattern("xxS")
                    .pattern(" sS")
                    .pattern("xxS")
                    .define('x', material)
                    .define('s', Items.STICK)
                    .define('S', Items.STRING)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.longbow().asItem())
                    .pattern(" xS")
                    .pattern("x S")
                    .pattern(" xS")
                    .define('x', material)
                    .define('S', Items.STRING)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);
        }
    }
}
