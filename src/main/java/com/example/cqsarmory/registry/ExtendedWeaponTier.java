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
    public static ExtendedWeaponTier GOLD;
    public static ExtendedWeaponTier DIAMOND;
    public static ExtendedWeaponTier NETHERITE;
    public static ExtendedWeaponTier CUSTOM;
    String name;
    float mult;
    int uses;
    float damage;
    float speed;
    int enchantmentValue;
    TagKey<Block> incorrectBlocksForDrops;
    Supplier<Ingredient> repairIngredient;
    AttributeContainer[] attributes;

    public ExtendedWeaponTier(String name, float mult, int uses, float damage, float speed, int enchantmentValue, TagKey<Block> incorrectBlocksForDrops, Supplier<Ingredient> repairIngredient, AttributeContainer... attributes) {
        this.name = name;
        this.mult = mult;
        this.uses = uses;
        this.damage = damage;
        this.speed = speed;
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
        WITHERSTEEL = new ExtendedWeaponTier("withersteel", 5, 2500, 10.0F, 1.0F, 4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(new ItemLike[]{ItemRegistry.WITHERSTEEL_WEAPONSET.ingot().get()}));
        COPPER = new ExtendedWeaponTier("copper", 1, 250, 1.0F, 0.2F, 4, BlockTags.INCORRECT_FOR_IRON_TOOL, () -> Ingredient.of(new ItemLike[]{Items.COPPER_INGOT}));
        GOLD = new ExtendedWeaponTier("gold", 1, 150, 1.0F, 0.0F, 5, BlockTags.INCORRECT_FOR_GOLD_TOOL, () -> Ingredient.of(new ItemLike[]{Items.GOLD_INGOT}));
        DIAMOND = new ExtendedWeaponTier("diamond", 1.2F, 250, 2.0F, 0.2F, 4, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, () -> Ingredient.of(new ItemLike[]{Items.DIAMOND}));
        NETHERITE = new ExtendedWeaponTier("netherite", 2, 2000, 3.0F, 0.3F, 4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(new ItemLike[]{Items.NETHERITE_INGOT}));
        CUSTOM = new ExtendedWeaponTier("custom", 1, 2000, 0F, 0F, 4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, () -> Ingredient.of(new ItemLike[]{ItemRegistry.REPAIR_KIT.get()}));


    }
}
