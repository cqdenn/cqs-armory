package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.ScytheProjectile;
import com.example.cqsarmory.data.entity.ability.ThrownItemProjectile;
import com.example.cqsarmory.utils.RenderingUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

public class ScytheProjectileRenderer extends EntityRenderer<ScytheProjectile> {

    public ScytheProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ScytheProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        var item = entity.getThrownItem();
        if (item.isEmpty()) {
            return;
        }
        var scale = entity.getScale();
        poseStack.pushPose();
        if (!entity.isInGround()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot()));

            poseStack.mulPose(Axis.XP.rotationDegrees(90));

            poseStack.mulPose(Axis.ZP.rotationDegrees((entity.tickCount + partialTick) * 36f));
        }
        poseStack.scale(scale, scale, scale);
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), 0);
        poseStack.popPose();

        Vec3 projOld = new Vec3(entity.xo, entity.yo, entity.zo);
        Vec3 start = projOld.add(entity.position().subtract(projOld).scale(partialTick));

        Entity caster = entity.getOwner();
        Vec3 casterOld = new Vec3(caster.xo, caster.yo + 1, caster.zo);
        Vec3 to = casterOld.add(caster.position().add(0, 1, 0).subtract(casterOld).scale(partialTick));

        RenderingUtils.renderChainBetween(start, to, poseStack, bufferSource);

    }

    @Override
    public ResourceLocation getTextureLocation(ScytheProjectile entity) {
        return IronsSpellbooks.id("empty");
    }
}