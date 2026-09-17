package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.MobEffectRegistry;
import com.example.cqsarmory.registry.Tags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CQMobEffectsTagsProvider extends TagsProvider<MobEffect> {


    protected CQMobEffectsTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.MOB_EFFECT, lookupProvider, CqsArmory.MODID, existingFileHelper);
    }



    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(Tags.MobEffects.MAGE_AOES).add(MobEffectRegistry.GENERIC_MAGE_AOE.getKey());
        tag(Tags.MobEffects.MAGE_AOES).add(MobEffectRegistry.HELLFIRE_MAGE_AOE.getKey());
        tag(Tags.MobEffects.MAGE_AOES).add(MobEffectRegistry.BLIZZARD_MAGE_AOE.getKey());
        tag(Tags.MobEffects.MAGE_AOES).add(MobEffectRegistry.SHOCKWAVE_MAGE_AOE.getKey());
        tag(Tags.MobEffects.MAGE_AOES).add(MobEffectRegistry.HEALING_MAGE_AOE.getKey());
    }
}
