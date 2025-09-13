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

public class SyncQuiverArrowsPacket implements CustomPacketPayload {

    public int arrows = 0;
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncQuiverArrowsPacket> STREAM_CODEC = CustomPacketPayload.codec(SyncQuiverArrowsPacket::write, SyncQuiverArrowsPacket::new);
    public static final CustomPacketPayload.Type<SyncQuiverArrowsPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "quiver_sync"));

    public SyncQuiverArrowsPacket(int rage) {
        this.arrows = rage;
    }
    public SyncQuiverArrowsPacket(FriendlyByteBuf buf) {
        arrows = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.arrows);
    }


    public static void handle(SyncQuiverArrowsPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof Player player) {
                AbilityData.get(player).quiverArrowCount = packet.arrows;
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
