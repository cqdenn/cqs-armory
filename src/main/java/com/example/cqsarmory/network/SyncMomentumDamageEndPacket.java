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

public class SyncMomentumDamageEndPacket implements CustomPacketPayload {

    public int momentumDamageEnd = 0;
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMomentumDamageEndPacket> STREAM_CODEC = CustomPacketPayload.codec(SyncMomentumDamageEndPacket::write, SyncMomentumDamageEndPacket::new);
    public static final Type<SyncMomentumDamageEndPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "momentum_damage_end_sync"));

    public SyncMomentumDamageEndPacket(int momentumDamageEnd) {
        this.momentumDamageEnd = momentumDamageEnd;
    }
    public SyncMomentumDamageEndPacket(FriendlyByteBuf buf) {
        momentumDamageEnd = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.momentumDamageEnd);
    }


    public static void handle(SyncMomentumDamageEndPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof Player player) {
                AbilityData.get(player).momentumOrbEffects.arrowDamageEnd = packet.momentumDamageEnd;
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
