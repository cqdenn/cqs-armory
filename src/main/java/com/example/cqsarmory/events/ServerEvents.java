package com.example.cqsarmory.events;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ServerEvents {

    @SubscribeEvent
    public static void onDamage (LivingIncomingDamageEvent event) {
        event.getSource().getEntity().push(1, 1, 1);
    }
}
