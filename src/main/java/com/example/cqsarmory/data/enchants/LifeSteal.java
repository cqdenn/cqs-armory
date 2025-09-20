package com.example.cqsarmory.data.enchants;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record LifeSteal(LevelBasedValue health) implements EnchantmentEntityEffect {
    public static final MapCodec<LifeSteal> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                    LevelBasedValue.CODEC.fieldOf("health").forGetter(lifeSteal -> lifeSteal.health))
            .apply(builder, LifeSteal::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        /*if (item.owner() != null){
            item.owner().heal(health.calculate(enchantmentLevel));
        }*/
    }

    @Override
    public MapCodec<LifeSteal> codec() {
        return CODEC;
    }
}