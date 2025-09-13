package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static com.example.cqsarmory.CqsArmory.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class PayloadHandler {
    @SubscribeEvent
    public static void register (RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar payloadRegistrar = event.registrar(CqsArmory.MODID).versioned("1.0.0").optional();
        payloadRegistrar.playToServer(StartSuckingPacket.TYPE, StartSuckingPacket.STREAM_CODEC, StartSuckingPacket::handle);
        payloadRegistrar.playToClient(SyncRagePacket.TYPE, SyncRagePacket.STREAM_CODEC, SyncRagePacket::handle);
        payloadRegistrar.playToClient(SyncMomentumPacket.TYPE, SyncMomentumPacket.STREAM_CODEC, SyncMomentumPacket::handle);
        payloadRegistrar.playToClient(SyncQuiverArrowsPacket.TYPE, SyncQuiverArrowsPacket.STREAM_CODEC, SyncQuiverArrowsPacket::handle);
        payloadRegistrar.playToClient(SyncMomentumSpeedPacket.TYPE, SyncMomentumSpeedPacket.STREAM_CODEC, SyncMomentumSpeedPacket::handle);
        payloadRegistrar.playToClient(SyncMomentumSpeedEndPacket.TYPE, SyncMomentumSpeedEndPacket.STREAM_CODEC, SyncMomentumSpeedEndPacket::handle);
        payloadRegistrar.playToClient(SyncMomentumDamagePacket.TYPE, SyncMomentumDamagePacket.STREAM_CODEC, SyncMomentumDamagePacket::handle);
        payloadRegistrar.playToClient(SyncMomentumDamageEndPacket.TYPE, SyncMomentumDamageEndPacket.STREAM_CODEC, SyncMomentumDamageEndPacket::handle);
    }
}
