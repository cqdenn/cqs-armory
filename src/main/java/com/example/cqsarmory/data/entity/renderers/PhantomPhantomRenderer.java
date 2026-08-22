package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.layers.PhantomPhantomEyesLayer;
import com.example.cqsarmory.data.entity.living.PhantomPhantom;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.redspace.ironsspellbooks.render.RenderHelper;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class PhantomPhantomRenderer extends LivingEntityRenderer<PhantomPhantom, PhantomModel<PhantomPhantom>> {
    private static final ResourceLocation PHANTOM_LOCATION = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/phantom_phantom.png");

    public PhantomPhantomRenderer(EntityRendererProvider.Context p_174338_) {
        super(p_174338_, new PhantomModel<>(p_174338_.bakeLayer(ModelLayers.PHANTOM)), 0.75F);
        this.addLayer(new PhantomPhantomEyesLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(PhantomPhantom entity) {
        return PHANTOM_LOCATION;
    }

    @Override
    protected @Nullable RenderType getRenderType(PhantomPhantom livingEntity, boolean bodyVisible, boolean translucent, boolean glowing) {
        //if (livingEntity.hurtTime > 0 || livingEntity.deathTime > 0) return super.getRenderType(livingEntity, bodyVisible, translucent, glowing);
        return RenderHelper.CustomerRenderType.magic(getTextureLocation(livingEntity));
    }

    @Override
    protected boolean shouldShowName(PhantomPhantom entity) {
        return super.shouldShowName(entity)
                && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public void render(PhantomPhantom entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.hurtTime > 0 || entity.deathTime > 0) RenderSystem.setShaderColor(1, 0.2f, 0.2f, 1);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, LightTexture.FULL_BRIGHT);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        /*if (entity.hurtTime > 0 || entity.deathTime > 0) {
            this.model.renderToBuffer(poseStack, buffer.getBuffer(getRenderType(entity, true, true, true)), packedLight, OverlayTexture.NO_OVERLAY, 0xFF0000);
        }*/
    }

    protected void scale(PhantomPhantom livingEntity, PoseStack poseStack, float partialTickTime) {
        int i = livingEntity.getPhantomSize();
        float f = 1.0F + 0.15F * (float) i;
        poseStack.scale(f, f, f);
        poseStack.translate(0.0F, 1.3125F, 0.1875F);
    }

    protected void setupRotations(PhantomPhantom entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
    }
}
