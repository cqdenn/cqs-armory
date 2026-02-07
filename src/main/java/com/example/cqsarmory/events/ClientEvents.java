package com.example.cqsarmory.events;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.network.PlayerDodgePacket;
import com.example.cqsarmory.network.StartSuckingPacket;
import com.example.cqsarmory.network.doOnSwingEffectPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;


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

}
