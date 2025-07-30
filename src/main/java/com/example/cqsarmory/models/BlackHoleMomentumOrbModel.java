package com.example.cqsarmory.models;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.ArrowDamageMomentumOrb;
import com.example.cqsarmory.data.entity.ability.BlackHoleMomentumOrb;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class BlackHoleMomentumOrbModel extends DefaultedEntityGeoModel<BlackHoleMomentumOrb> {
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "geo/momentum_orb.geo.json");

    public BlackHoleMomentumOrbModel() {
        super(CqsArmory.id("entity"));
    }
    @Override
    public ResourceLocation getModelResource(BlackHoleMomentumOrb object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(BlackHoleMomentumOrb object) {
        return object.getTexture();
    }

}