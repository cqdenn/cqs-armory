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

public class SyncSummonersStacksPacket implements CustomPacketPayload {

    public int stacks = 0;
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncSummonersStacksPacket> STREAM_CODEC = CustomPacketPayload.codec(SyncSummonersStacksPacket::write, SyncSummonersStacksPacket::new);
    public static final CustomPacketPayload.Type<SyncSummonersStacksPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "summoner_stacks_sync"));

    public SyncSummonersStacksPacket(int stacks) {
        this.stacks = stacks;
    }
    public SyncSummonersStacksPacket(FriendlyByteBuf buf) {
        stacks = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.stacks);
    }


    public static void handle(SyncSummonersStacksPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof Player player) {
                AbilityData.get(player).summonersStacks.summonsAlive = packet.stacks;
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
