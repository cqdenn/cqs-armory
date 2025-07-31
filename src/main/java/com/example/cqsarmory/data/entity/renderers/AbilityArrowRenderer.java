package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.client.renderer.entity.TippableArrowRenderer.NORMAL_ARROW_LOCATION;

public class AbilityArrowRenderer<T extends AbilityArrow> extends ArrowRenderer<T> {
    public AbilityArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        var f = entity.getScale();
        poseStack.scale(f, f, f);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return NORMAL_ARROW_LOCATION;
    }
}
