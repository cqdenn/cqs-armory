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

public class SyncMomentumSpeedPacket implements CustomPacketPayload {

    public int momentumSpeed = 0;
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMomentumSpeedPacket> STREAM_CODEC = CustomPacketPayload.codec(SyncMomentumSpeedPacket::write, SyncMomentumSpeedPacket::new);
    public static final Type<SyncMomentumSpeedPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "momentum_speed_sync"));

    public SyncMomentumSpeedPacket(int momentumSpeed) {
        this.momentumSpeed = momentumSpeed;
    }
    public SyncMomentumSpeedPacket(FriendlyByteBuf buf) {
        momentumSpeed = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.momentumSpeed);
    }


    public static void handle(SyncMomentumSpeedPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof Player player) {
                AbilityData.get(player).momentumOrbEffects.speedStacks = packet.momentumSpeed;
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
