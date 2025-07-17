package com.example.cqsarmory.models;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.ExplosiveMomentumOrb;
import com.example.cqsarmory.data.entity.ability.MomentumOrb;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class ExplosiveMomentumOrbModel extends DefaultedEntityGeoModel<ExplosiveMomentumOrb> {
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "geo/momentum_orb.geo.json");

    public ExplosiveMomentumOrbModel() {
        super(CqsArmory.id("entity"));
    }
    @Override
    public ResourceLocation getModelResource(ExplosiveMomentumOrb object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ExplosiveMomentumOrb object) {
        return object.getTexture();
    }

}
