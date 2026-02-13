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

public class SyncManaSpentPacket implements CustomPacketPayload {

    public int mana_spent = 0;
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncManaSpentPacket> STREAM_CODEC = CustomPacketPayload.codec(SyncManaSpentPacket::write, SyncManaSpentPacket::new);
    public static final Type<SyncManaSpentPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "mana_spent_sync"));

    public SyncManaSpentPacket(int mana_spent) {
        this.mana_spent = mana_spent;
    }
    public SyncManaSpentPacket(FriendlyByteBuf buf) {
        mana_spent = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.mana_spent);
    }


    public static void handle(SyncManaSpentPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof Player player) {
                AbilityData.get(player).manaSpentSinceLastAOE = packet.mana_spent;
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
