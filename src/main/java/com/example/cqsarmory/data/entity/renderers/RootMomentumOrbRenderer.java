package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.BlackHoleMomentumOrb;
import com.example.cqsarmory.data.entity.ability.RootMomentumOrb;
import com.example.cqsarmory.models.BlackHoleMomentumOrbModel;
import com.example.cqsarmory.models.RootMomentumOrbModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RootMomentumOrbRenderer extends GeoEntityRenderer<RootMomentumOrb> {

    public RootMomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RootMomentumOrbModel());
    }

    @Override
    public void preRender(PoseStack poseStack, RootMomentumOrb animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        poseStack.scale(2, 2, 2);
    }
}
