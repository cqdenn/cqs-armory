package com.example.cqsarmory.utils;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.DamageData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.redspace.ironsspellbooks.render.SpellRenderingHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class RenderingUtils {

    public static void renderChainBetween(Vec3 start, Vec3 to, PoseStack poseStack, MultiBufferSource multiBufferSource) {
        poseStack.pushPose();
        VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/chain_whip.png")));
        Vec3 delta = to.subtract(start);
        float distance = (float) delta.length();
        Vec3 direction = delta.normalize();
        Quaternionf rotation = new Quaternionf()
                .rotationTo(new Vector3f(0, 0, 1), new Vector3f(
                        (float) direction.x,
                        (float) direction.y,
                        (float) direction.z
                ));
        poseStack.mulPose(rotation);
        SpellRenderingHelper.drawQuad(Vec3.ZERO, new Vec3(0, 0, distance), 1, 0, poseStack.last(), consumer, 255, 255, 255, 255, 0, distance);
        SpellRenderingHelper.drawQuad(Vec3.ZERO, new Vec3(0, 0, distance), 0, 1, poseStack.last(), consumer, 255, 255, 255, 255, 0, distance);
        poseStack.popPose();
    }

}
