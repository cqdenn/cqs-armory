package com.example.cqsarmory.events;

import com.example.cqsarmory.items.CosmicArkItem;
import com.example.cqsarmory.network.StartSuckingPacket;
import com.example.cqsarmory.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.example.cqsarmory.items.CosmicArkItem.abilityStacks;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onLeftClick (ClientTickEvent.Pre event) {
        if (Minecraft.getInstance().options.keyAttack.isDown()) {
            PacketDistributor.sendToServer(new StartSuckingPacket());
        }
    }


    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        Entity target = event.getEntity();
        if (event.getSource().getDirectEntity() instanceof Player player) {
            if (player.getMainHandItem().is(ItemRegistry.COSMIC_ARK) && !player.level().getEntities(player, player.getBoundingBox().expandTowards(player.getForward().scale(7).multiply(1, 0, 1)).move(player.getForward().scale(2))).contains(target)) {
                CosmicArkItem.refresh(player);
            }
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Post event) {
    }

//    @SubscribeEvent
//    public static void onLeftClick (AttackEntityEvent event) {
//        if (event.getEntity().getMainHandItem().is(ItemRegistry.SOUL_SUCKER)) {
//            PacketDistributor.sendToServer(new StartSuckingPacket());
//        }
//    }

}
