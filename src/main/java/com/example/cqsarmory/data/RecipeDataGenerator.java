package com.example.cqsarmory.data;

import com.example.cqsarmory.items.armor.*;
import com.example.cqsarmory.registry.ItemRegistry;
import com.example.cqsarmory.registry.Tags;
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
                        SmithingTransformRecipeBuilder.smithing(Ingredient.of(), Ingredient.of(downgrade), Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), RecipeCategory.COMBAT, armor)
                                .unlocks("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                                .save(recipeOutput, armor + "_smithing");
                    }
                    if (armor instanceof MarksmanArmorItem || armor instanceof SkirmisherArmorItem || armor instanceof ChampionArmorItem || armor instanceof JuggernautArmorItem || armor instanceof ArchmageArmorItem || armor instanceof SageArmorItem) {
                        var downgrade = CraftingMaterials.getSmithingDowngrade(armor);
                        SmithingTransformRecipeBuilder.smithing(Ingredient.of(), Ingredient.of(downgrade), Ingredient.of(Tags.Items.MATERIALS_POWER_THREE), RecipeCategory.COMBAT, armor)
                                .unlocks("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
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

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.ice().asItem())
                    .pattern("  f")
                    .pattern(" x ")
                    .pattern("x  ")
                    .define('x', material)
                    .define('f', Items.SNOWBALL)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.fire().asItem())
                    .pattern("  f")
                    .pattern(" x ")
                    .pattern("x  ")
                    .define('x', material)
                    .define('f', Items.MAGMA_BLOCK)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.lightning().asItem())
                    .pattern("  f")
                    .pattern(" x ")
                    .pattern("x  ")
                    .define('x', material)
                    .define('f', Items.LIGHTNING_ROD)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.necromancy().asItem())
                    .pattern("  f")
                    .pattern(" x ")
                    .pattern("x  ")
                    .define('x', material)
                    .define('f', Items.BONE)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.arcane().asItem())
                    .pattern("  f")
                    .pattern(" x ")
                    .pattern("x  ")
                    .define('x', material)
                    .define('f', Items.AMETHYST_SHARD)
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);

            ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, weaponset.holy().asItem())
                    .pattern("  f")
                    .pattern(" x ")
                    .pattern("x  ")
                    .define('x', material)
                    .define('f', io.redspace.ironsspellbooks.registries.ItemRegistry.DIVINE_PEARL.get())
                    .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(material))
                    .save(recipeOutput);
        }

        //ingots
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.WITHERSTEEL_WEAPONSET.ingot().get().asItem(), 1)
                .requires(Items.NETHERITE_INGOT, 1)
                .requires(Items.WITHER_SKELETON_SKULL)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.BLAZING_WEAPONSET.ingot().get().asItem(), 1)
                .requires(Items.NETHERITE_INGOT, 1)
                .requires(Items.BLAZE_ROD, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.SCULK_WEAPONSET.ingot().get().asItem(), 1)
                .requires(Items.DIAMOND, 1)
                .requires(Items.ECHO_SHARD, 4)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.LIVING_WEAPONSET.ingot().get().asItem(), 1)
                .requires(Items.DIAMOND, 5)
                .requires(Items.HONEY_BOTTLE, 1)
                .requires(Items.GLOW_INK_SAC, 1)
                .requires(Items.AMETHYST_CLUSTER, 1)
                .requires(Items.SPORE_BLOSSOM, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.UMBRITE_WEAPONSET.ingot().get().asItem(), 1)
                .requires(Items.DIAMOND, 5)
                .requires(Items.END_CRYSTAL, 4)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.SILVERSTEEL_WEAPONSET.ingot().get().asItem(), 1)
                .requires(Items.DIAMOND, 5)
                .requires(Items.BREEZE_ROD, 4)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.DWARVEN_STEEL_WEAPONSET.ingot().get().asItem(), 1)
                .requires(Items.DIAMOND, 5)
                .requires(Items.COPPER_BLOCK, 4)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(recipeOutput);

        //shields
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.IRONWALL, 1)
                .requires(Items.SHIELD, 1)
                .requires(Items.IRON_BLOCK, 4)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SHIELD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.THORNBARK, 1)
                .requires(Items.SHIELD, 1)
                .requires(Items.HONEY_BOTTLE, 1)
                .requires(Items.GLOW_INK_SAC, 1)
                .requires(Items.AMETHYST_CLUSTER, 1)
                .requires(Items.SPORE_BLOSSOM, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SHIELD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.FLASHGUARD, 1)
                .requires(Items.SHIELD, 1)
                .requires(ItemRegistry.SILVERSTEEL_WEAPONSET.ingot().get(), 4)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SHIELD))
                .save(recipeOutput);

        //accessories
        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.BERSERKERS_FURY.get(), 1)
                .requires(ItemRegistry.WITHERSTEEL_WEAPONSET.ingot().get(), 1)
                .requires(Items.DIAMOND, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WITHERSTEEL_WEAPONSET.ingot().get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.BERSERKERS_RUSH.get(), 1)
                .requires(ItemRegistry.BLAZING_WEAPONSET.ingot().get(), 1)
                .requires(Items.DIAMOND, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BLAZING_WEAPONSET.ingot().get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.WARDSTONE.get(), 1)
                .requires(ItemRegistry.DWARVEN_STEEL_WEAPONSET.ingot().get(), 4)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.DWARVEN_STEEL_WEAPONSET.ingot().get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.HELLFIRE_SIGIL.get(), 1)
                .requires(ItemRegistry.BLAZING_WEAPONSET.ingot().get(), 2)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BLAZING_WEAPONSET.ingot().get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.CHRONOWARP_RUNE.get(), 1)
                .requires(ItemRegistry.SILVERSTEEL_WEAPONSET.ingot().get(), 2)
                .requires(Items.CLOCK)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SILVERSTEEL_WEAPONSET.ingot().get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.MANASAVER.get(), 1)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.ARCANE_INGOT.get(), 4)
                .requires(Items.DIAMOND)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(io.redspace.ironsspellbooks.registries.ItemRegistry.ARCANE_INGOT.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.HUNTERS_ECHO.get(), 1)
                .requires(ItemRegistry.SCULK_WEAPONSET.ingot().get(), 2)
                .requires(Items.ARROW)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SCULK_WEAPONSET.ingot().get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.BLASTER.get(), 1)
                .requires(Items.TNT, 1)
                .requires(Items.DIAMOND, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.TNT))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.SHARPHOOTER.get(), 1)
                .requires(ItemRegistry.LIVING_WEAPONSET.ingot().get(), 1)
                .requires(Items.DIAMOND, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ARROW))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.BIGGER_BOOMER.get(), 1)
                .requires(Items.GUNPOWDER, 1)
                .requires(Items.DIAMOND, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GUNPOWDER))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.HUNTER_TALISMAN.get(), 1)
                .requires(Items.ARROW, 1)
                .requires(Items.DIAMOND, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.TRACKER_TALISMAN.get(), 1)
                .requires(ItemRegistry.HUNTER_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.HUNTER_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.MARKSMAN_TALISMAN.get(), 1)
                .requires(ItemRegistry.TRACKER_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TRACKER_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.SCOUT_TALISMAN.get(), 1)
                .requires(Items.FEATHER, 1)
                .requires(Items.DIAMOND, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.RANGER_TALISMAN.get(), 1)
                .requires(ItemRegistry.SCOUT_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SCOUT_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.SKIRMISHER_TALISMAN.get(), 1)
                .requires(ItemRegistry.RANGER_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.RANGER_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.WARRIOR_TALISMAN.get(), 1)
                .requires(Items.GOLD_INGOT, 1)
                .requires(Items.DIAMOND, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.SOLDIER_TALISMAN.get(), 1)
                .requires(ItemRegistry.WARRIOR_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WARRIOR_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.CHAMPION_TALISMAN.get(), 1)
                .requires(ItemRegistry.SOLDIER_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SOLDIER_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.RAMPART_TALISMAN.get(), 1)
                .requires(Items.IRON_INGOT, 1)
                .requires(Items.DIAMOND, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.BASTION_TALISMAN.get(), 1)
                .requires(ItemRegistry.RAMPART_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.RAMPART_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.JUGGERNAUT_TALISMAN.get(), 1)
                .requires(ItemRegistry.BASTION_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BASTION_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.SEER_TALISMAN.get(), 1)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.DIVINE_PEARL.get(), 1)
                .requires(Items.DIAMOND, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.MYSTIC_TALISMAN.get(), 1)
                .requires(ItemRegistry.SEER_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SEER_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.SAGE_TALISMAN.get(), 1)
                .requires(ItemRegistry.MYSTIC_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MYSTIC_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.APPRENTICE_TALISMAN.get(), 1)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.ARCANE_ESSENCE.get(), 1)
                .requires(Items.DIAMOND, 8)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.MAGUS_TALISMAN.get(), 1)
                .requires(ItemRegistry.APPRENTICE_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.APPRENTICE_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.ARCHMAGE_TALISMAN.get(), 1)
                .requires(ItemRegistry.MAGUS_TALISMAN.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE))
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MAGUS_TALISMAN.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.BASIC_QUIVER.get(), 1)
                .requires(Items.LEATHER, 4)
                .requires(Items.ARROW, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ARROW))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.POISON_QUIVER.get(), 1)
                .requires(ItemRegistry.BASIC_QUIVER.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_ONE), 1)
                .requires(Items.POISONOUS_POTATO, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BASIC_QUIVER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.FIREWORK_QUIVER.get(), 1)
                .requires(ItemRegistry.BASIC_QUIVER.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(Items.FIREWORK_ROCKET, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BASIC_QUIVER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.HUNTERS_QUIVER.get(), 1)
                .requires(ItemRegistry.BASIC_QUIVER.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(ItemRegistry.LIVING_WEAPONSET.ingot().get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BASIC_QUIVER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.FIRE_QUIVER.get(), 1)
                .requires(ItemRegistry.BASIC_QUIVER.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_ONE), 1)
                .requires(Items.BLAZE_ROD, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BASIC_QUIVER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.ICE_QUIVER.get(), 1)
                .requires(ItemRegistry.BASIC_QUIVER.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_ONE), 1)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.FROZEN_BONE_SHARD.get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BASIC_QUIVER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.GRAVITY_QUIVER.get(), 1)
                .requires(ItemRegistry.BASIC_QUIVER.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(ItemRegistry.UMBRITE_WEAPONSET.ingot().get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BASIC_QUIVER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.PLENTY_QUIVER.get(), 1)
                .requires(ItemRegistry.BASIC_QUIVER.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_ONE), 1)
                .requires(Items.ARROW, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BASIC_QUIVER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.BAT_QUIVER.get(), 1)
                .requires(ItemRegistry.BASIC_QUIVER.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE), 1)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.BLOOD_VIAL.get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BASIC_QUIVER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.SWORD_QUIVER.get(), 1)
                .requires(ItemRegistry.BASIC_QUIVER.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(Items.IRON_SWORD, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BASIC_QUIVER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.ANVIL_QUIVER.get(), 1)
                .requires(ItemRegistry.BASIC_QUIVER.get(), 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE), 1)
                .requires(Items.ANVIL, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BASIC_QUIVER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.MAGMA_BRAND.get(), 1)
                .requires(Items.GLASS_BOTTLE, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(Items.MAGMA_BLOCK, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.COLD_BRAND.get(), 1)
                .requires(Items.GLASS_BOTTLE, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(Items.ICE, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.VOLT_BRAND.get(), 1)
                .requires(Items.GLASS_BOTTLE, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.LIGHTNING_BOTTLE.get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.BLOOD_BRAND.get(), 1)
                .requires(Items.GLASS_BOTTLE, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.BLOOD_VIAL.get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.EVASIVE_BRAND.get(), 1)
                .requires(Items.GLASS_BOTTLE, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(Items.ENDER_EYE, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.ANGEL_BRAND.get(), 1)
                .requires(Items.GLASS_BOTTLE, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.DIVINE_PEARL.get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.VEIL_BRAND.get(), 1)
                .requires(Items.GLASS_BOTTLE, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE), 1)
                .requires(Items.GOLDEN_APPLE, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.ARCANE_BRAND.get(), 1)
                .requires(Items.GLASS_BOTTLE, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.ARCANE_INGOT.get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.INFINITY_BRAND.get(), 1)
                .requires(Items.GLASS_BOTTLE, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.MITHRIL_INGOT.get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.ELEMENTAL_BRAND.get(), 1)
                .requires(Items.GLASS_BOTTLE, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE), 1)
                .requires(ItemRegistry.BLAZING_WEAPONSET.ingot().get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.SUMMONERS_BRAND.get(), 1)
                .requires(Items.GLASS_BOTTLE, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE), 1)
                .requires(ItemRegistry.WITHERSTEEL_WEAPONSET.ingot().get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GLASS_BOTTLE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.POISON_COATING.get(), 1)
                .requires(Items.IRON_SWORD, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(Items.POISONOUS_POTATO, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_SWORD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.VOLCANO_COATING.get(), 1)
                .requires(Items.IRON_SWORD, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE), 1)
                .requires(Items.BLAZE_POWDER, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_SWORD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.LIGHTNING_COATING.get(), 1)
                .requires(Items.IRON_SWORD, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(io.redspace.ironsspellbooks.registries.ItemRegistry.LIGHTNING_BOTTLE.get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_SWORD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.FANG_COATING.get(), 1)
                .requires(Items.IRON_SWORD, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_ONE), 1)
                .requires(Items.EMERALD, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_SWORD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.COSMIC_COATING.get(), 1)
                .requires(Items.IRON_SWORD, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE), 1)
                .requires(Items.END_CRYSTAL, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_SWORD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.THORN_COATING.get(), 1)
                .requires(Items.IRON_SWORD, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_TWO), 1)
                .requires(ItemRegistry.LIVING_WEAPONSET.ingot().get(), 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_SWORD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.TAUNT_COATING.get(), 1)
                .requires(Items.IRON_SWORD, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_ONE), 1)
                .requires(Items.IRON_CHESTPLATE, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_SWORD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.BASH_COATING.get(), 1)
                .requires(Items.IRON_SWORD, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_ONE), 1)
                .requires(Items.SHIELD, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_SWORD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.BLEED_COATING.get(), 1)
                .requires(Items.IRON_SWORD, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_THREE), 1)
                .requires(Items.WITHER_SKELETON_SKULL, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_SWORD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.FIRE_COATING.get(), 1)
                .requires(Items.IRON_SWORD, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_ONE), 1)
                .requires(Items.BLAZE_ROD, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_SWORD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, ItemRegistry.ICE_COATING.get(), 1)
                .requires(Items.IRON_SWORD, 1)
                .requires(Ingredient.of(Tags.Items.MATERIALS_POWER_ONE), 1)
                .requires(Items.ICE, 1)
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_SWORD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.MAX_RAGE_UPGRADE_ORB.get())
                .pattern(" f ")
                .pattern("fxf")
                .pattern(" f ")
                .define('x', io.redspace.ironsspellbooks.registries.ItemRegistry.UPGRADE_ORB.get())
                .define('f', ItemRegistry.WITHERSTEEL_WEAPONSET.ingot().get())
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WITHERSTEEL_WEAPONSET.ingot().get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.MOMENTUM_ON_HIT_UPGRADE_ORB.get())
                .pattern(" f ")
                .pattern("fxf")
                .pattern(" f ")
                .define('x', io.redspace.ironsspellbooks.registries.ItemRegistry.UPGRADE_ORB.get())
                .define('f', ItemRegistry.BLAZING_WEAPONSET.ingot().get())
                .unlockedBy("criteria", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BLAZING_WEAPONSET.ingot().get()))
                .save(recipeOutput);

    }
}
