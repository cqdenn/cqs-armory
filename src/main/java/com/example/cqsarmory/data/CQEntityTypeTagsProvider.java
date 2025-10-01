package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.Tags;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Entity;

import java.util.concurrent.CompletableFuture;

public class CQEntityTypeTagsProvider extends TagsProvider<EntityType<?>> {


    protected CQEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ENTITY_TYPE, lookupProvider, CqsArmory.MODID, existingFileHelper);
    }



    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        tag(Tags.EntityTypes.EXPLOSIVE_ENTITIES).add(EntityRegistry.WITHER_SKULL_PROJECTILE.getKey());
        tag(Tags.EntityTypes.EXPLOSIVE_ENTITIES).add(EntityRegistry.ECHOING_STRIKE.getKey());
        tag(Tags.EntityTypes.EXPLOSIVE_ENTITIES).add(EntityRegistry.COMET.getKey());
        tag(Tags.EntityTypes.EXPLOSIVE_ENTITIES).add(EntityRegistry.CREEPER_HEAD_PROJECTILE.getKey());
        tag(Tags.EntityTypes.EXPLOSIVE_ENTITIES).add(EntityRegistry.FIRE_ARROW_PROJECTILE.getKey());
        tag(Tags.EntityTypes.EXPLOSIVE_ENTITIES).add(EntityRegistry.FIRE_BOMB.getKey());
        tag(Tags.EntityTypes.EXPLOSIVE_ENTITIES).add(EntityRegistry.MAGIC_FIREBALL.getKey());
    }
}
