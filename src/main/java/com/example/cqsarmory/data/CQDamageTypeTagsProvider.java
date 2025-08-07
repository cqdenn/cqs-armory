package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.Tags;
import jdk.dynalink.linker.support.Lookup;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CQDamageTypeTagsProvider extends TagsProvider<DamageType> {


    protected CQDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, CqsArmory.MODID, existingFileHelper);
    }



    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(Tags.DamageTypes.CAUSES_RAGE_GAIN).add(DamageTypes.MOB_ATTACK);
        tag(Tags.DamageTypes.CAUSES_RAGE_GAIN).add(DamageTypes.PLAYER_ATTACK);
        //tag(Tags.DamageTypes.CAUSES_RAGE_GAIN).add(com.example.cqsarmory.registry.DamageTypes.MELEE_SKILL);
    }
}
