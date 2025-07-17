package com.example.cqsarmory.models;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.ArrowDamageMomentumOrb;
import com.example.cqsarmory.data.entity.ability.ExplosiveMomentumOrb;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class ArrowDamageMomentumOrbModel extends DefaultedEntityGeoModel<ArrowDamageMomentumOrb> {
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "geo/momentum_orb.geo.json");

    public ArrowDamageMomentumOrbModel() {
        super(CqsArmory.id("entity"));
    }
    @Override
    public ResourceLocation getModelResource(ArrowDamageMomentumOrb object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ArrowDamageMomentumOrb object) {
        return object.getTexture();
    }

}
