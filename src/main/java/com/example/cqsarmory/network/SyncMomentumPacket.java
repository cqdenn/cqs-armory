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

public class SyncMomentumPacket implements CustomPacketPayload {

    public int momentum = 0;
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMomentumPacket> STREAM_CODEC = CustomPacketPayload.codec(SyncMomentumPacket::write, SyncMomentumPacket::new);
    public static final Type<SyncMomentumPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "momentum_sync"));

    public SyncMomentumPacket(int momentum) {
        this.momentum = momentum;
    }
    public SyncMomentumPacket(FriendlyByteBuf buf) {
        momentum = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.momentum);
    }


    public static void handle(SyncMomentumPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof Player player) {
                AbilityData.get(player).setMomentum(packet.momentum);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
