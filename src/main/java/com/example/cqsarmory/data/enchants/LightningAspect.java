package com.example.cqsarmory.data.enchants;

import com.example.cqsarmory.data.DamageData;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.entity.spells.ChainLightning;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record LightningAspect(LevelBasedValue bounces) implements EnchantmentEntityEffect {
    public static final MapCodec<LightningAspect> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            LevelBasedValue.CODEC.fieldOf("bounces").forGetter(lightningAspect -> lightningAspect.bounces))
            .apply(builder, LightningAspect::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (entity instanceof LivingEntity livingEntity) {
            LivingEntity attacker = livingEntity.getLastAttacker();
            float dmg = DamageData.get(livingEntity).lastDamage * 0.5f;
            ChainLightning chainLightning = new ChainLightning(level, attacker, entity);
            chainLightning.setDamage(dmg);
            chainLightning.maxConnections = (int) bounces.calculate(enchantmentLevel);
            chainLightning.range = 8;
            level.addFreshEntity(chainLightning);
        }
    }

    @Override
    public MapCodec<LightningAspect> codec() {
        return CODEC;
    }
}
