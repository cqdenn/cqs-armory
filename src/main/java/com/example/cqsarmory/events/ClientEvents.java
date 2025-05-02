package com.example.cqsarmory.events;

import com.example.cqsarmory.items.CosmicArkItem;
import com.example.cqsarmory.items.VolcanoSwordItem;
import com.example.cqsarmory.network.StartSuckingPacket;
import com.example.cqsarmory.registry.ItemRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
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
    public static void onDeath(LivingDeathEvent event) {
        /*
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.getMainHandItem().is(ItemRegistry.VOLCANO)) {
                VolcanoSwordItem.particles(event.getEntity().getEyePosition(), event.getEntity().level());
                }
            } else if(event.getEntity().getTags().contains("fire_sword")) {
            VolcanoSwordItem.particles(event.getEntity().getEyePosition(), event.getEntity().level());
        }

         */
        }



    }
