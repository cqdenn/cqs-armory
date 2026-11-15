package com.example.cqsarmory.models;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.living.ScorchedBlaze;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ScorchedBlazeModel extends GeoModel<ScorchedBlaze> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/blaze_bulwark.png");

    @Override
    public ResourceLocation getTextureResource(ScorchedBlaze object) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(ScorchedBlaze animatable) {
        return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "animations/scorched_blaze.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(ScorchedBlaze object) {
        return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "geo/blaze_bulwark.geo.json");
    }
}

