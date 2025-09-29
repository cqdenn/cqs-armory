package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.AnvilProjectile;
import com.example.cqsarmory.data.entity.ability.ThrownItemProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class AnvilProjectileRenderer extends EntityRenderer<AnvilProjectile> {

    public AnvilProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(AnvilProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        var item = entity.getItem();
        if (item.isEmpty()) {
            return;
        }
        var scale = entity.getScale();
        poseStack.pushPose();
        Vec3 motion = entity.deltaMovementOld.add(entity.getDeltaMovement().subtract(entity.deltaMovementOld).scale(partialTick));
        float xRot = -((float) (Mth.atan2(motion.horizontalDistance(), motion.y) * (double) (180F / (float) Math.PI)) - 90.0F);
        float yRot = -((float) (Mth.atan2(motion.z, motion.x) * (double) (180F / (float) Math.PI)) + 90.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        //poseStack.mulPose(Axis.XP.rotationDegrees(xRot - (entity.tickCount + partialTick) * 36));
        poseStack.scale(2 * scale, 2 * scale, 2 * scale);
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), 0);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(AnvilProjectile entity) {
        return IronsSpellbooks.id("empty");
    }
}
