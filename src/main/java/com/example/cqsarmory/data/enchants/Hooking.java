package com.example.cqsarmory.data.enchants;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record Hooking(LevelBasedValue value) implements EnchantmentEntityEffect {
    public static final MapCodec<Hooking> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                    LevelBasedValue.CODEC.fieldOf("value").forGetter(hooking -> hooking.value))
            .apply(builder, Hooking::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (entity instanceof LivingEntity target) {
            if (target.getLastDamageSource().getDirectEntity() != null) {
                Entity arrow = target.getLastDamageSource().getDirectEntity();
                double x = (arrow.xOld - target.getX());
                double z = (arrow.zOld - target.getZ());
                Vec3 vec3 = new Vec3(x, 0.2, z).normalize().scale(value.calculate(enchantmentLevel));
                target.push(vec3);
            }
        }
    }

    @Override
    public MapCodec<Hooking> codec() {
        return CODEC;
    }
}
