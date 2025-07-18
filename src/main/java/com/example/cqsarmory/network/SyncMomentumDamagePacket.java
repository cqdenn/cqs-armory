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

public class SyncMomentumDamagePacket implements CustomPacketPayload {

    public int momentumDamage = 0;
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMomentumDamagePacket> STREAM_CODEC = CustomPacketPayload.codec(SyncMomentumDamagePacket::write, SyncMomentumDamagePacket::new);
    public static final Type<SyncMomentumDamagePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "momentum_damage_sync"));

    public SyncMomentumDamagePacket(int momentumDamage) {
        this.momentumDamage = momentumDamage;
    }
    public SyncMomentumDamagePacket(FriendlyByteBuf buf) {
        momentumDamage = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.momentumDamage);
    }


    public static void handle(SyncMomentumDamagePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof Player player) {
                AbilityData.get(player).momentumOrbEffects.arrowDamageStacks = packet.momentumDamage;
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
