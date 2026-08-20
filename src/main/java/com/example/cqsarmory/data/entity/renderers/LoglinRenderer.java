package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.living.Loglin;
import net.minecraft.client.model.HoglinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LoglinRenderer extends MobRenderer<Loglin, HoglinModel<Loglin>> {
    private static final ResourceLocation LOGLIN_LOCATION = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/loglin.png");

    public LoglinRenderer(EntityRendererProvider.Context p_174165_) {
        super(p_174165_, new HoglinModel<>(p_174165_.bakeLayer(ModelLayers.HOGLIN)), 0.7F);
    }

    public ResourceLocation getTextureLocation(Loglin entity) {
        return LOGLIN_LOCATION;
    }

    protected boolean isShaking(Loglin entity) {
        return super.isShaking(entity) || entity.isChargingJump();
    }
}
