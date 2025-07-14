package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.data.AbilityData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SpeedMomentumOrb extends MomentumOrb{
    public SpeedMomentumOrb(EntityType<?> entityType, Level level, Player creator) {
        super(entityType, level, creator);
    }
}
