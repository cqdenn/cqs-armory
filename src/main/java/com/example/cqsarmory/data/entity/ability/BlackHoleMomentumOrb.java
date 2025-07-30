package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.CqsArmory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BlackHoleMomentumOrb extends MomentumOrb{
    public BlackHoleMomentumOrb(EntityType<?> entityType, Level level, Player creator) {
        super(entityType, level, creator);
    }

    public BlackHoleMomentumOrb(EntityType<?> momentumOrbEntityType, Level level) {
        super(momentumOrbEntityType, level);
    }

    @Override
    public ResourceLocation getTexture() {
        return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/black_hole_orb.png");
    }
}
