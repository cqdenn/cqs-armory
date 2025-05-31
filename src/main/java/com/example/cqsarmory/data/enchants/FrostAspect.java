package com.example.cqsarmory.data.enchants;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.redspace.ironsspellbooks.entity.spells.ChainLightning;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record FrostAspect(LevelBasedValue duration) implements EnchantmentEntityEffect {
    public static final MapCodec<FrostAspect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                    LevelBasedValue.CODEC.fieldOf("duration").forGetter(frostAspect -> frostAspect.duration))
            .apply(builder, FrostAspect::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        entity.setTicksFrozen((int) duration.calculate(enchantmentLevel));
    }

    @Override
    public MapCodec<FrostAspect> codec() {
        return CODEC;
    }
}
