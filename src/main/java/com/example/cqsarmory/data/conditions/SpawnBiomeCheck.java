package com.example.cqsarmory.data.conditions;

import com.example.cqsarmory.registry.LootItemConditionRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record SpawnBiomeCheck(String spawnBiome, LootContext.EntityTarget entityTarget) implements LootItemCondition {

    public static final MapCodec<SpawnBiomeCheck> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.STRING.fieldOf("spawn_biome").forGetter(SpawnBiomeCheck::spawnBiome),
            LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(SpawnBiomeCheck::entityTarget)
    ).apply(builder, SpawnBiomeCheck::new));

    @Override
    public LootItemConditionType getType() {
        return LootItemConditionRegistry.SPAWN_BIOME_CONDITION.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (lootContext.getParamOrNull(this.entityTarget.getParam()) instanceof Entity entity) {
            if (entity.getPersistentData().getString("cqs_armory:spawn_biome").equals(spawnBiome)) {
                return true;
            }
        }
        return false;
    }
}
