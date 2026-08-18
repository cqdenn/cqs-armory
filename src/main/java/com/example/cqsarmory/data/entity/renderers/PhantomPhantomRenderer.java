package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.layers.PhantomPhantomEyesLayer;
import com.example.cqsarmory.data.entity.living.PhantomPhantom;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class PhantomPhantomRenderer extends MagicMobRenderer<PhantomPhantom, PhantomModel<PhantomPhantom>>{
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

    protected void scale(PhantomPhantom livingEntity, PoseStack poseStack, float partialTickTime) {
        int i = livingEntity.getPhantomSize();
        float f = 1.0F + 0.15F * (float)i;
        poseStack.scale(f, f, f);
        poseStack.translate(0.0F, 1.3125F, 0.1875F);
    }

    protected void setupRotations(PhantomPhantom entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
    }
}
