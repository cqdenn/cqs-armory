package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.MomentumOrb;
import com.example.cqsarmory.models.MomentumOrbModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.redspace.ironsspellbooks.entity.mobs.wizards.archevoker.ArchevokerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MomentumOrbRenderer extends GeoEntityRenderer<MomentumOrb> {

    public MomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MomentumOrbModel());
    }

    @Override
    public void preRender(PoseStack poseStack, MomentumOrb animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        poseStack.scale(2, 2, 2);
    }
}
