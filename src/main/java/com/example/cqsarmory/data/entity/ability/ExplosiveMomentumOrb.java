package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ExplosiveMomentumOrb extends MomentumOrb{
    public ExplosiveMomentumOrb(EntityType<?> entityType, Level level, Player creator) {
        super(entityType, level, creator);
    }
}
