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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.earlydisplay.ElementShader;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Quaternionf;
import org.joml.Vector3f;


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
    public static void chained (RenderLivingEvent.Post<?, ?> event) {
        LivingEntity living = event.getEntity();
        if (living.hasEffect(MobEffectRegistry.CHAINED) && DamageData.get(living).chainWhipLocation != null) {
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            Vec3 old = new Vec3(living.xo, living.yo, living.zo);
            Vec3 pos= old.add(living.position().subtract(old).scale(event.getPartialTick()));
            Vec3 start = pos;
            VertexConsumer consumer = event.getMultiBufferSource().getBuffer(RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/chain_whip.png")));
            // find chain anchor location in local space (subtract from player pos, un-rotate. probably)
            Vec3 anchor = DamageData.get(living).chainWhipLocation;
            Vec3 delta = anchor.subtract(start);
            float distance = (float) delta.length();
            Vec3 direction = delta.normalize();
            Vec3 rotationOffset = delta.normalize();
            Quaternionf rotation = new Quaternionf()
                    .rotationTo(new Vector3f(0, 0, 1), new Vector3f(
                            (float) direction.x,
                            (float) direction.y,
                            (float) direction.z
                    ));
            poseStack.mulPose(rotation);
           /* Vec3 rotationOffset = DamageData.get(living).chainWhipLocation.subtract(pos).normalize();
            Vec3 delta = DamageData.get(living).chainWhipLocation.subtract(pos).yRot((living.getYRot() + 90) * Mth.DEG_TO_RAD);*/
            // find distance between anchor and destination (probably entity hitbox center)
            /*float distance = (float) DamageData.get(living).chainWhipLocation.distanceTo(living.getHitbox().getCenter());*/
            // find rotation this vector forms. apply rotation to pose stack
            /*Vec2 rot = Utils.rotationFromDirection(rotationOffset);
            // use spell rendering helper to render two quads, forming the t shape of the chain. travel only along z axis
            // should be able to render directly between vectors. use vector distance as UV max. it should tile automatically
            // will need horiztonal chain texture
            poseStack.mulPose(Axis.XP.rotation(-rot.x));
            poseStack.mulPose(Axis.YP.rotation(rot.y));*/
            SpellRenderingHelper.drawQuad(Vec3.ZERO, new Vec3(0, 0, distance), 1, 0, poseStack.last(), consumer, 255, 255, 255, 255, 0, distance);
            SpellRenderingHelper.drawQuad(Vec3.ZERO, new Vec3(0, 0, distance), 0, 1, poseStack.last(), consumer, 255, 255, 255, 255, 0, distance);
            poseStack.popPose();
        }
    }

}
