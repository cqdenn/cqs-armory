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

public class SyncElementalistStacksPacket implements CustomPacketPayload {

    public int fire = 0;
    public int ice = 0;
    public int lightning = 0;
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncElementalistStacksPacket> STREAM_CODEC = CustomPacketPayload.codec(SyncElementalistStacksPacket::write, SyncElementalistStacksPacket::new);
    public static final CustomPacketPayload.Type<SyncElementalistStacksPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "elementalist_sync"));

    public SyncElementalistStacksPacket(int fire, int ice, int lightning) {
        this.fire = fire;
        this.ice = ice;
        this.lightning = lightning;
    }
    public SyncElementalistStacksPacket(FriendlyByteBuf buf) {
        fire = buf.readInt();
        ice = buf.readInt();
        lightning = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.fire);
        buf.writeInt(this.ice);
        buf.writeInt(this.lightning);
    }


    public static void handle(SyncElementalistStacksPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof Player player) {
                AbilityData.get(player).elementalistStacks.fireStacks = packet.fire;
                AbilityData.get(player).elementalistStacks.iceStacks = packet.ice;
                AbilityData.get(player).elementalistStacks.lightningStacks = packet.lightning;
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
