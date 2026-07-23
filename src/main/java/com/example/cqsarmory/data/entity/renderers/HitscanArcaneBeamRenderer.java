package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.HitscanArcaneBeam;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.render.RenderHelper;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

public class HitscanArcaneBeamRenderer extends EntityRenderer<HitscanArcaneBeam> {

    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "eldritch_blast_model"), "main");
    private static final ResourceLocation TEXTURE_CORE = CqsArmory.id("textures/entity/arcane_beam/core.png");
    private static final ResourceLocation TEXTURE_OVERLAY = CqsArmory.id("textures/entity/arcane_beam/overlay.png");

    private final ModelPart body;

    public HitscanArcaneBeamRenderer(EntityRendererProvider.Context context) {
        super(context);
        ModelPart modelpart = context.bakeLayer(MODEL_LAYER_LOCATION);
        this.body = modelpart.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8, -64, -8, 16, 128, 16), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public boolean shouldRender(HitscanArcaneBeam pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public void render(HitscanArcaneBeam entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        poseStack.pushPose();
        float lifetime = HitscanArcaneBeam.lifetime;
        float scalar = .25f;
        float length = 128 * scalar * scalar;
        float f = entity.tickCount + partialTicks;
        poseStack.translate(0, 0, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 180.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot() - 90));
        float scale = entity.getScale() * scalar;
        float forLoop = 0;
        for (float i = 0; i < entity.distance * 4; i += length) forLoop++;
        float accuracyScalar = entity.distance / forLoop * 0.5f + 0.5f;
        poseStack.scale(1, accuracyScalar, 1);
        poseStack.scale(scale, scale, scale);

        //float scale = Mth.lerp(Mth.clamp(f / 6f, 0, 1), 1, 2.3f);

        float alpha = Mth.clamp(1f - f / lifetime, 0, 1);

        poseStack.translate(0, length/2, 0);
        for (float i = 0; i < entity.distance * 4; i += length) {
            //Render overlay
            //VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE_OVERLAY));
            VertexConsumer consumer = bufferSource.getBuffer(RenderHelper.CustomerRenderType.magicNoCull(TEXTURE_OVERLAY));
            {
                poseStack.pushPose();
                float expansion = Mth.clampedLerp(1.2f, 0, f / (lifetime));
                poseStack.mulPose(Axis.YP.rotationDegrees(f * 5));
                poseStack.scale(expansion, 1, expansion);
                poseStack.mulPose(Axis.YP.rotationDegrees(45));
                this.body.render(poseStack, consumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.color((int) (alpha * 255), 255, 255, 255));
                poseStack.popPose();
            }
            //Render core
            consumer = bufferSource.getBuffer(RenderHelper.CustomerRenderType.darkGlow(TEXTURE_CORE));
            //consumer = bufferSource.getBuffer(EldritchBlastRenderType.eldritchBlast(TEXTURE_CORE));
            {
                poseStack.pushPose();
                float expansion = Mth.clampedLerp(1, 0, f / (lifetime - 5));
                poseStack.scale(expansion, 1, expansion);
                poseStack.mulPose(Axis.YP.rotationDegrees(f * -10));
                this.body.render(poseStack, consumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, -1);
                poseStack.popPose();
            }
            poseStack.translate(0, length, 0);
        }


        poseStack.popPose();

        super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    @Override
    public ResourceLocation getTextureLocation(HitscanArcaneBeam entity) {
        return TEXTURE_CORE;
    }
}
