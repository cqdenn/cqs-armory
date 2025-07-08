package com.example.cqsarmory.events;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.items.CosmicArkItem;
import com.example.cqsarmory.items.VolcanoSwordItem;
import com.example.cqsarmory.network.StartSuckingPacket;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.ItemRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Random;


@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onLeftClick(ClientTickEvent.Pre event) {
        if (Minecraft.getInstance().options.keyAttack.isDown()) {
            PacketDistributor.sendToServer(new StartSuckingPacket());
        }
    }

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
