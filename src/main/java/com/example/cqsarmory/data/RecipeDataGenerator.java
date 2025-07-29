package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.ItemRegistry;
import com.example.cqsarmory.utils.WeaponsetMaterials;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

public class RecipeDataGenerator extends RecipeProvider {
    public RecipeDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        super.buildRecipes(recipeOutput);

        for (ItemRegistry.Weaponset weaponset : ItemRegistry.WEAPONSETS) {
            var material = WeaponsetMaterials.getIngotReplacement(weaponset);
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

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.bow().asItem())
                    .pattern(" xS")
                    .pattern("s S")
                    .pattern(" xS")
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
