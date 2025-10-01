package com.example.cqsarmory.data.enchants;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record PoisonAspect(LevelBasedValue duration) implements EnchantmentEntityEffect {
    public static final MapCodec<PoisonAspect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                    LevelBasedValue.CODEC.fieldOf("duration").forGetter(poisonAspect -> poisonAspect.duration))
            .apply(builder, PoisonAspect::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        /*if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.BLIGHT, (int) duration.calculate(enchantmentLevel), enchantmentLevel));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, (int) duration.calculate(enchantmentLevel), enchantmentLevel - 1));
        }*/
    }

    @Override
    public MapCodec<PoisonAspect> codec() {
        return CODEC;
    }
}
