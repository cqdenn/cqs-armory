package com.example.cqsarmory.events;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.network.DoubleJumpPacket;
import com.example.cqsarmory.network.doOnSwingEffectPacket;
import com.example.cqsarmory.registry.MobEffectRegistry;
import com.example.cqsarmory.utils.RenderingUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import software.bernie.geckolib.event.GeoRenderEvent;


@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void archerDoubleJump (InputEvent.Key event) {
        if (Minecraft.getInstance().level == null) return;
        var options = Minecraft.getInstance().options;
        if (options.keyJump.getKey().getValue() == event.getKey() && event.getAction() == 1) {
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
            if (lateral == 0 && forward == 0) {forward = -1;}
            PacketDistributor.sendToServer(new DoubleJumpPacket(new Vec3(lateral, 0, forward)));
        }
    }

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
    public static void chainHookRender(RenderLivingEvent.Post<?, ?> event) {
        LivingEntity living = event.getEntity();
        DamageData data = DamageData.get(living);
        if (data.hookedByLocation == null) return;

        float partialTick = event.getPartialTick();

        Vec3 start = new Vec3(
                Mth.lerp(partialTick, living.xo, living.getX()),
                Mth.lerp(partialTick, living.yo, living.getY()),
                Mth.lerp(partialTick, living.zo, living.getZ())
        ).add(0, 1, 0);

        Vec3 to = data.hookedByLocation;

        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();
        poseStack.translate(0, 1, 0);

        RenderingUtils.renderChainBetween(start, to, event.getPoseStack(), event.getMultiBufferSource());

        poseStack.popPose();
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

    @SubscribeEvent
    public static void chainHookRender(GeoRenderEvent.Entity.Post event) {
        Entity living = event.getEntity();
        DamageData data = DamageData.get(living);
        if (data.hookedByLocation == null) return;

        float partialTick = event.getPartialTick();

        Vec3 start = new Vec3(
                Mth.lerp(partialTick, living.xo, living.getX()),
                Mth.lerp(partialTick, living.yo, living.getY()),
                Mth.lerp(partialTick, living.zo, living.getZ())
        ).add(0, 1, 0);

        Vec3 to = data.hookedByLocation;

        PoseStack poseStack = event.getPoseStack();
        poseStack.pushPose();
        poseStack.translate(0, 1, 0);

        RenderingUtils.renderChainBetween(start, to, event.getPoseStack(), event.getBufferSource());

        poseStack.popPose();
    }

}
