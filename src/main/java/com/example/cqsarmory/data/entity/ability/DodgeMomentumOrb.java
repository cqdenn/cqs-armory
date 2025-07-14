package com.example.cqsarmory.data.entity.ability;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DodgeMomentumOrb extends MomentumOrb{
    public DodgeMomentumOrb(EntityType<?> entityType, Level level, Player creator) {
        super(entityType, level, creator);
    }
}
