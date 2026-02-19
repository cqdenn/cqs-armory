package com.example.cqsarmory.mixin;

import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.registry.MobEffectRegistry;
import com.example.cqsarmory.utils.RenderingUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow @Final private RenderBuffers renderBuffers;

    @Inject(method = "renderItemInHand", at = @At("HEAD"))
    private void cqs_armory$chainRenderFirstPerson(Camera camera, float partialTick, Matrix4f projectionMatrix, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        if (player.hasEffect(MobEffectRegistry.CHAINED) && DamageData.get(player).chainWhipLocation != null) {
            PoseStack poseStack = new PoseStack();
            poseStack.pushPose();
            //poseStack.mulPose(projectionMatrix);
            //poseStack.mulPose(camera.rotation());

            float chainHeight = 1;
            Vec3 old = new Vec3(player.xo, player.yo, player.zo);
            Vec3 start = old.add(player.position().subtract(old).scale(partialTick));
            Vec3 to = DamageData.get(player).chainWhipLocation.subtract(0, chainHeight, 0);
            poseStack.pushPose();
            poseStack.translate(0, chainHeight, 0);

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    poseStack.pushPose();
                    Vec3 dir = new Vec3((i - 0.5) * j, 0, (i - 0.5) * (1 - j)).scale(2);
                    Vec3 translate = dir.scale(player.getHitbox().getXsize() / 2).scale(0.5);
                    poseStack.translate(translate.x, translate.y - 2, translate.z);
                    RenderingUtils.renderChainBetween(start, to.add(dir.scale(1)), poseStack, renderBuffers.bufferSource());
                    poseStack.popPose();
                }
            }
            poseStack.popPose();
        }
    }

}
