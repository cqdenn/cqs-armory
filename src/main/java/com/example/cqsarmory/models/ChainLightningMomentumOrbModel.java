package com.example.cqsarmory.models;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.BlackHoleMomentumOrb;
import com.example.cqsarmory.data.entity.ability.ChainLightningMomentumOrb;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class ChainLightningMomentumOrbModel extends DefaultedEntityGeoModel<ChainLightningMomentumOrb> {
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "geo/momentum_orb.geo.json");

    public ChainLightningMomentumOrbModel() {
        super(CqsArmory.id("entity"));
    }
    @Override
    public ResourceLocation getModelResource(ChainLightningMomentumOrb object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ChainLightningMomentumOrb object) {
        return object.getTexture();
    }

}
