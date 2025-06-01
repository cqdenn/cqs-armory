package com.example.cqsarmory.data.conditions;

import com.example.cqsarmory.registry.LootItemConditionRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.EntityHasScoreCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record HealthCheck(double healthBelow, boolean inverted, LootContext.EntityTarget entityTarget) implements LootItemCondition {

    public static final MapCodec<HealthCheck> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Codec.DOUBLE.fieldOf("threshold").forGetter(HealthCheck::healthBelow),
            Codec.BOOL.fieldOf("inverted").forGetter(HealthCheck::inverted),
            LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(HealthCheck::entityTarget)
    ).apply(builder, HealthCheck::new));

    @Override
    public LootItemConditionType getType() {
        return LootItemConditionRegistry.HEALTH_CHECK_CONDITION.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (lootContext.getParamOrNull(this.entityTarget.getParam()) instanceof LivingEntity livingEntity) {
            if ((livingEntity.getHealth() / livingEntity.getMaxHealth()) <= healthBelow ^ inverted) {
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
