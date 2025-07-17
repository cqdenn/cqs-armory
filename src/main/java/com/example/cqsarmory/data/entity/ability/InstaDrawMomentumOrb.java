package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.CqsArmory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class InstaDrawMomentumOrb extends MomentumOrb{
    public InstaDrawMomentumOrb(EntityType<?> entityType, Level level, Player creator) {
        super(entityType, level, creator);
    }

    public InstaDrawMomentumOrb(EntityType<?> instaDrawMomentumOrbEntityType, Level level) {
        super(instaDrawMomentumOrbEntityType, level);
    }

    @Override
    public ResourceLocation getTexture() {
        return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/insta_draw_orb.png");
    }
}
