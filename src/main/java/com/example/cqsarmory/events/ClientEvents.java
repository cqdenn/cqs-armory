package com.example.cqsarmory.events;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.network.doOnSwingEffectPacket;
import com.example.cqsarmory.registry.MobEffectRegistry;
import com.example.cqsarmory.utils.RenderingUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.render.SpellRenderingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.earlydisplay.ElementShader;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.event.GeckoLibEventsNeoForge;
import software.bernie.geckolib.event.GeoRenderEvent;
import software.bernie.geckolib.service.GeckoLibEvents;

import javax.swing.plaf.basic.BasicTreeUI;


@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    /*@SubscribeEvent
    public static void dodge(ClientTickEvent.Pre event) {
        if (KeyMappings.DODGE_KEYMAP.consumeClick()) {
            var options = Minecraft.getInstance().options;
            int lateral = 0;
            int forward = 0;
            if (options.keyUp.isDown()) {
                forward += 1;
            }
            if (options.keyDown.isDown()) {
                forward -= 1;
            }
            if (options.keyRight.isDown()) {
                lateral -= 1;
            }
            if (options.keyLeft.isDown()) {
                lateral += 1;
            }

            if (lateral == 0 && forward == 0) {forward = 1;}
            PacketDistributor.sendToServer(new PlayerDodgePacket(new Vec3(lateral, 0, forward)));

        }
    }*/

    @SubscribeEvent
    public static void onSwing(PlayerInteractEvent.LeftClickEmpty event) {
        PacketDistributor.sendToServer(new doOnSwingEffectPacket());
    }

    /*@SubscribeEvent
    public static void onLeftClick(ClientTickEvent.Pre event) {
        if (Minecraft.getInstance().options.keyAttack.isDown()) {
            PacketDistributor.sendToServer(new StartSuckingPacket());
        }
    }*/

    @SubscribeEvent
    public static void heavinessCurse(MovementInputUpdateEvent event) {
        //ItemStack oldItem = event.getFrom();
        //ItemStack newItem = event.getTo();
        LivingEntity entity = event.getEntity();
        Holder.Reference<Enchantment> heavinessCurseHolder = entity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "heaviness_curse")));
        Holder.Reference<Enchantment> reallyHeavinessCurseHolder = entity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "really_heaviness_curse")));
        int heavinessCurseLevel = entity.getItemBySlot(EquipmentSlot.FEET).getEnchantmentLevel(heavinessCurseHolder);
        int reallyHeavinessCurseLevel = entity.getItemBySlot(EquipmentSlot.CHEST).getEnchantmentLevel(reallyHeavinessCurseHolder);
        if (heavinessCurseLevel > 0 && !(reallyHeavinessCurseLevel > 0)) {
            event.getInput().shiftKeyDown = true;
        }


    }

    @SubscribeEvent
    public static void chained(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity living = event.getEntity();
        if (living.hasEffect(MobEffectRegistry.CHAINED) && DamageData.get(living).chainWhipLocation != null) {

            float chainHeight = 1;
            Vec3 old = new Vec3(living.xo, living.yo, living.zo);
            Vec3 start = old.add(living.position().subtract(old).scale(event.getPartialTick()));
            Vec3 to = DamageData.get(living).chainWhipLocation.subtract(0, chainHeight, 0);
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            poseStack.translate(0, chainHeight, 0);

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    poseStack.pushPose();
                    Vec3 dir = new Vec3((i - 0.5) * j, 0, (i - 0.5) * (1 - j)).scale(2);
                    Vec3 translate = dir.scale(living.getHitbox().getXsize() / 2).scale(0.5);
                    poseStack.translate(translate.x, translate.y, translate.z);
                    RenderingUtils.renderChainBetween(start, to.add(dir.scale(1)), event.getPoseStack(), event.getMultiBufferSource());
                    poseStack.popPose();
                }
            }
            poseStack.popPose();
        }
    }

    @SubscribeEvent
    public static void chainedGecko (GeoRenderEvent.Entity.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity living && living.hasEffect(MobEffectRegistry.CHAINED) && DamageData.get(living).chainWhipLocation != null) {
            float chainHeight = 1;
            Vec3 old = new Vec3(living.xo, living.yo, living.zo);
            Vec3 start = old.add(living.position().subtract(old).scale(event.getPartialTick()));
            Vec3 to = DamageData.get(living).chainWhipLocation.subtract(0, chainHeight, 0);
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            poseStack.translate(0, chainHeight, 0);

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    poseStack.pushPose();
                    Vec3 dir = new Vec3((i - 0.5) * j, 0, (i - 0.5) * (1 - j)).scale(2);
                    Vec3 translate = dir.scale(living.getHitbox().getXsize() / 2).scale(0.5);
                    poseStack.translate(translate.x, translate.y, translate.z);
                    RenderingUtils.renderChainBetween(start, to.add(dir.scale(1)), event.getPoseStack(), event.getBufferSource());
                    poseStack.popPose();
                }
            }
            poseStack.popPose();
        }
    }

}
