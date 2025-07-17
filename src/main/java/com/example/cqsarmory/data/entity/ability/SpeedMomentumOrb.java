package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.resources.ResourceLocation;
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

    public SpeedMomentumOrb(EntityType<?> speedMomentumOrbEntityType, Level level) {
        super(speedMomentumOrbEntityType, level);
    }

    @Override
    public ResourceLocation getTexture() {
        return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/speed_orb.png");
    }
}
