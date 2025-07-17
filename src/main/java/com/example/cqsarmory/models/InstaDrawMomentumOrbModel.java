package com.example.cqsarmory.models;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.ArrowDamageMomentumOrb;
import com.example.cqsarmory.data.entity.ability.InstaDrawMomentumOrb;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class InstaDrawMomentumOrbModel extends DefaultedEntityGeoModel<InstaDrawMomentumOrb> {
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "geo/momentum_orb.geo.json");

    public InstaDrawMomentumOrbModel() {
        super(CqsArmory.id("entity"));
    }
    @Override
    public ResourceLocation getModelResource(InstaDrawMomentumOrb object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(InstaDrawMomentumOrb object) {
        return object.getTexture();
    }

}
