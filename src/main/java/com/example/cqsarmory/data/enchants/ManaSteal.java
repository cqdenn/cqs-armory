package com.example.cqsarmory.data.enchants;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.network.SyncManaPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public record ManaSteal(LevelBasedValue mana) implements EnchantmentEntityEffect {
    public static final MapCodec<ManaSteal> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                    LevelBasedValue.CODEC.fieldOf("mana").forGetter(manaSteal -> manaSteal.mana))
            .apply(builder, ManaSteal::new)
    );

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (item.owner() instanceof ServerPlayer serverPlayer) {
            MagicData.getPlayerMagicData(serverPlayer).addMana(mana.calculate(enchantmentLevel));
            PacketDistributor.sendToPlayer(serverPlayer, new SyncManaPacket(MagicData.getPlayerMagicData(serverPlayer)));
        }
    }

    @Override
    public MapCodec<ManaSteal> codec() {
        return CODEC;
    }
}
