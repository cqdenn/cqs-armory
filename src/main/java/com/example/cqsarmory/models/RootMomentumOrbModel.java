package com.example.cqsarmory.models;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.IceExplosionMomentumOrb;
import com.example.cqsarmory.data.entity.ability.RootMomentumOrb;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class RootMomentumOrbModel extends DefaultedEntityGeoModel<RootMomentumOrb> {
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "geo/momentum_orb.geo.json");

    public RootMomentumOrbModel() {
        super(CqsArmory.id("entity"));
    }
    @Override
    public ResourceLocation getModelResource(RootMomentumOrb object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(RootMomentumOrb object) {
        return object.getTexture();
    }

}
