package com.example.cqsarmory.registry;

import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ExtendedWeaponTier implements Tier {


    public static ExtendedWeaponTier WITHERSTEEL;
    public static ExtendedWeaponTier COPPER;
    public static ExtendedWeaponTier IRON;
    public static ExtendedWeaponTier GOLD;
    public static ExtendedWeaponTier DIAMOND;
    public static ExtendedWeaponTier NETHERITE;
    public static ExtendedWeaponTier SCULK;
    public static ExtendedWeaponTier OBSIDIAN;
    public static ExtendedWeaponTier AMETHYST;
    public static ExtendedWeaponTier BLAZING;
    public static ExtendedWeaponTier LIVING;
    public static ExtendedWeaponTier UMBRITE;
    public static ExtendedWeaponTier SILVERSTEEL;
    public static ExtendedWeaponTier DWARVEN_STEEL;
    public static ExtendedWeaponTier CUSTOM;

    String name;
    float mult;
    int uses;
    float damage;
    float speed;
    float drawSpeed;
    int enchantmentValue;
    TagKey<Block> incorrectBlocksForDrops;
    Supplier<Ingredient> repairIngredient;
    AttributeContainer[] attributes;

    public ExtendedWeaponTier(String name, float mult, int uses, float damage, float speed, float drawSpeed, int enchantmentValue, TagKey<Block> incorrectBlocksForDrops, Supplier<Ingredient> repairIngredient, AttributeContainer... attributes) {
        this.name = name;
        this.mult = mult;
        this.uses = uses;
        this.damage = damage;
        this.speed = speed;
        this.drawSpeed = drawSpeed;
        this.enchantmentValue = enchantmentValue;
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.repairIngredient = repairIngredient;
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return name;
    }

    public float getMult() { return this.mult; }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() { return this.speed; }

    public float getDrawSpeed() {return this.drawSpeed;}

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return (Ingredient)this.repairIngredient.get();
    }

    public AttributeContainer[] getAdditionalAttributes() {
        return this.attributes;
    }

    static {
        WITHERSTEEL = new ExtendedWeaponTier("withersteel", 5, 2500, 5.0F, 0.0F, 0f, 4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(new ItemLike[]{ItemRegistry.WITHERSTEEL_WEAPONSET.ingot().get()}));
        COPPER = new ExtendedWeaponTier("copper", 1, 250, 1.0F, 0.2F, 1.5f, 4, BlockTags.INCORRECT_FOR_IRON_TOOL, () -> Ingredient.of(new ItemLike[]{Items.COPPER_INGOT}));
        IRON = new ExtendedWeaponTier("iron", 1, 250, 2.0F, 0.0F, 0f, 4, BlockTags.INCORRECT_FOR_IRON_TOOL, () -> Ingredient.of(new ItemLike[]{Items.IRON_INGOT}));
        GOLD = new ExtendedWeaponTier("gold", 1.2f, 150, 1.0F, 0.0F, 0f, 5, BlockTags.INCORRECT_FOR_GOLD_TOOL, () -> Ingredient.of(new ItemLike[]{Items.GOLD_INGOT}));
        DIAMOND = new ExtendedWeaponTier("diamond", 1.5F, 250, 3.0F, 0.2F, 2f, 4, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, () -> Ingredient.of(new ItemLike[]{Items.DIAMOND}));
        NETHERITE = new ExtendedWeaponTier("netherite", 4, 2000, 4.0F, 0.0F, 1f, 4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(new ItemLike[]{Items.NETHERITE_INGOT}));
        SCULK = new ExtendedWeaponTier("sculk", 2, 2000, 2.0F, 0.2F, 1.5f, 4, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, () -> Ingredient.of(new ItemLike[]{ItemRegistry.SCULK_WEAPONSET.ingot().get()}));
        OBSIDIAN = new ExtendedWeaponTier("obsidian", 1, 3000, 4.0F, -0.3F, -0.5f, 4, BlockTags.INCORRECT_FOR_IRON_TOOL, () -> Ingredient.of(new ItemLike[]{Items.OBSIDIAN}));
        AMETHYST = new ExtendedWeaponTier("amethyst", 1, 300, 1.0F, 0.2F, 2f, 4, BlockTags.INCORRECT_FOR_STONE_TOOL, () -> Ingredient.of(new ItemLike[]{Items.AMETHYST_SHARD}));
        BLAZING = new ExtendedWeaponTier("blazing", 5, 2500, 2.0F, 0.4F, 4f, 4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(new ItemLike[]{ItemRegistry.BLAZING_WEAPONSET.ingot().get()}));
        LIVING = new ExtendedWeaponTier("living", 3, 2000, 5.0F, -0.1F, -0.2f, 4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(new ItemLike[]{ItemRegistry.LIVING_WEAPONSET.ingot().get()}));
        UMBRITE = new ExtendedWeaponTier("umbrite", 3, 2000, 1.0F, 0.5F, 5f, 4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(new ItemLike[]{ItemRegistry.UMBRITE_WEAPONSET.ingot().get()}));
        SILVERSTEEL = new ExtendedWeaponTier("silversteel", 3, 2000, 0.0F, 0.4F, 4f, 4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(new ItemLike[]{ItemRegistry.SILVERSTEEL_WEAPONSET.ingot().get()}));
        DWARVEN_STEEL = new ExtendedWeaponTier("dwarvensteel", 3, 2000, 3.0F, 0.0F, 0f, 4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(new ItemLike[]{ItemRegistry.DWARVEN_STEEL_WEAPONSET.ingot().get()}));
        CUSTOM = new ExtendedWeaponTier("custom", 1, 2000, 0F, 0F, 0f, 4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(new ItemLike[]{ItemRegistry.REPAIR_KIT.get()}));


    }
}
