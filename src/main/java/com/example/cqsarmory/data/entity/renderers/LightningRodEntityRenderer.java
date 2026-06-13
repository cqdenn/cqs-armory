package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.LightningRodEntity;
import com.example.cqsarmory.data.entity.ability.MomentumOrb;
import com.example.cqsarmory.models.LightningRodEntityModel;
import com.example.cqsarmory.models.MomentumOrbModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LightningRodEntityRenderer extends GeoEntityRenderer<LightningRodEntity> {

    public LightningRodEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LightningRodEntityModel());
    }

    @Override
    public void preRender(PoseStack poseStack, LightningRodEntity animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        poseStack.scale(2, 2, 2);
    }
}
