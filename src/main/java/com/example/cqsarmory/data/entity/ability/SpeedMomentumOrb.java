package com.example.cqsarmory.data.entity.ability;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SpeedMomentumOrb extends MomentumOrb{
    public SpeedMomentumOrb(EntityType<?> entityType, Level level, Player creator) {
        super(entityType, level, creator);
    }
}
