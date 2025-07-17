package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.CqsArmory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ArrowDamageMomentumOrb extends MomentumOrb{
    public ArrowDamageMomentumOrb(EntityType<?> entityType, Level level, Player creator) {
        super(entityType, level, creator);
    }

    @Override
    public ResourceLocation getTexture() {
        return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/arrow_damage_orb.png");
    }
}
