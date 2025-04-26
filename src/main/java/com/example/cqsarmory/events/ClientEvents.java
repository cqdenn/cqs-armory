package com.example.cqsarmory.events;

import com.example.cqsarmory.network.StartSuckingPacket;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onLeftClick (ClientTickEvent.Pre event) {
        if (Minecraft.getInstance().options.keyAttack.isDown()) {
            PacketDistributor.sendToServer(new StartSuckingPacket());
        }
    }

//    @SubscribeEvent
//    public static void onLeftClick (AttackEntityEvent event) {
//        if (event.getEntity().getMainHandItem().is(ItemRegistry.SOUL_SUCKER)) {
//            PacketDistributor.sendToServer(new StartSuckingPacket());
//        }
//    }

}
