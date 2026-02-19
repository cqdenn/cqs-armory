package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.Tags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.CuriosConstants;

import java.util.concurrent.CompletableFuture;

public class CQDamageTypeTagsProvider extends TagsProvider<DamageType> {


    protected CQDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, CqsArmory.MODID, existingFileHelper);
    }



    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(Tags.DamageTypes.CAUSES_RAGE_GAIN).add(DamageTypes.MOB_ATTACK);
        tag(Tags.DamageTypes.CAUSES_RAGE_GAIN).add(DamageTypes.PLAYER_ATTACK);
        tag(Tags.DamageTypes.CAUSES_RAGE_GAIN).add(DamageTypes.TRIDENT);
        tag(Tags.DamageTypes.CAUSES_RAGE_GAIN).add(com.example.cqsarmory.registry.DamageTypes.MELEE_SKILL);

        tag(DamageTypeTags.NO_KNOCKBACK).add(com.example.cqsarmory.registry.DamageTypes.BLEEDING);

        tag(Tags.DamageTypes.EXPLOSIVE_DAMAGE).add(DamageTypes.FIREWORKS);
        tag(Tags.DamageTypes.EXPLOSIVE_DAMAGE).add(DamageTypes.EXPLOSION);
        tag(Tags.DamageTypes.EXPLOSIVE_DAMAGE).add(DamageTypes.PLAYER_EXPLOSION);
        tag(Tags.DamageTypes.EXPLOSIVE_DAMAGE).add(DamageTypes.FIREBALL);
        tag(Tags.DamageTypes.EXPLOSIVE_DAMAGE).add(DamageTypes.UNATTRIBUTED_FIREBALL);
        tag(Tags.DamageTypes.EXPLOSIVE_DAMAGE).add(com.example.cqsarmory.registry.DamageTypes.VOLCANO);
        tag(Tags.DamageTypes.EXPLOSIVE_DAMAGE).add(com.example.cqsarmory.registry.DamageTypes.FIREWORK_PROJECTILE);
    }
}
