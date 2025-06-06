package com.example.cqsarmory.data.enchants;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;

public record ReinforcementCurse() implements EnchantmentEntityEffect {
    public static final MapCodec<ReinforcementCurse> CODEC = MapCodec.unit(ReinforcementCurse::new);

    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        if (Utils.random.nextFloat() < 0.3) {
            return;
        }
        float angle = (Utils.random.nextBoolean() ? -90 : 90) * Mth.DEG_TO_RAD;
        float choice = Utils.random.nextFloat();
        Mob mob;


        if (choice > 0.2) {
            mob = new Zombie(level);
        } else if (choice > 0.4) {
            mob = new Skeleton(EntityType.SKELETON, level);
        } else if (choice > 0.6) {
            mob = new Silverfish(EntityType.SILVERFISH, level);
        } else if (choice > 0.8) {
            mob = new Slime(EntityType.SLIME, level);
        } else {
            mob = new Spider(EntityType.SPIDER, level);
        }

        if (entity instanceof LivingEntity livingEntity) {
            Vec3 offset = entity.getForward().multiply(3, 0, 3).scale(livingEntity.getScale()).yRot(angle);
            Vec3 spawn = Utils.moveToRelativeGroundLevel(level, Utils.raycastForBlock(level, livingEntity.getEyePosition(), livingEntity.position().add(offset), ClipContext.Fluid.NONE).getLocation(), 4);
            mob.moveTo(spawn.add(0, 0.1, 0));
            mob.finalizeSpawn(level, level.getCurrentDifficultyAt(livingEntity.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
            level.addFreshEntity(mob);
        }
    }

    @Override
    public MapCodec<ReinforcementCurse> codec() {
        return CODEC;
    }
}
