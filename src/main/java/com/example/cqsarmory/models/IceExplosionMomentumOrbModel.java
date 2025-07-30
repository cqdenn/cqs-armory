package com.example.cqsarmory.models;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.BlackHoleMomentumOrb;
import com.example.cqsarmory.data.entity.ability.IceExplosionMomentumOrb;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class IceExplosionMomentumOrbModel extends DefaultedEntityGeoModel<IceExplosionMomentumOrb> {
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "geo/momentum_orb.geo.json");

    public IceExplosionMomentumOrbModel() {
        super(CqsArmory.id("entity"));
    }
    @Override
    public ResourceLocation getModelResource(IceExplosionMomentumOrb object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(IceExplosionMomentumOrb object) {
        return object.getTexture();
    }

}
