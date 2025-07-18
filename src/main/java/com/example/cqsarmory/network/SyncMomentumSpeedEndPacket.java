package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SyncMomentumSpeedEndPacket implements CustomPacketPayload {

    public int momentumSpeedEnd = 0;
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMomentumSpeedEndPacket> STREAM_CODEC = CustomPacketPayload.codec(SyncMomentumSpeedEndPacket::write, SyncMomentumSpeedEndPacket::new);
    public static final Type<SyncMomentumSpeedEndPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "momentum_speed_end_sync"));

    public SyncMomentumSpeedEndPacket(int momentumSpeedEnd) {
        this.momentumSpeedEnd = momentumSpeedEnd;
    }
    public SyncMomentumSpeedEndPacket(FriendlyByteBuf buf) {
        momentumSpeedEnd = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.momentumSpeedEnd);
    }


    public static void handle(SyncMomentumSpeedEndPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof Player player) {
                AbilityData.get(player).momentumOrbEffects.speedEnd = packet.momentumSpeedEnd;
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
