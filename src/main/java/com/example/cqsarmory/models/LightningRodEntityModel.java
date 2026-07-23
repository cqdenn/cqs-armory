package com.example.cqsarmory.models;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.LightningRodEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class LightningRodEntityModel extends DefaultedEntityGeoModel<LightningRodEntity> {
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "geo/lightning_rod.geo.json");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/lightning_rod.png");

    public LightningRodEntityModel() {
        super(CqsArmory.id("entity"));
    }
    @Override
    public ResourceLocation getModelResource(LightningRodEntity object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(LightningRodEntity object) {
        return TEXTURE;
    }

}
