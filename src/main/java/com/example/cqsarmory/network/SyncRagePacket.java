package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.registry.ItemRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SyncRagePacket implements CustomPacketPayload {

    public int rage = 0;
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncRagePacket> STREAM_CODEC = CustomPacketPayload.codec(SyncRagePacket::write, SyncRagePacket::new);
    public static final CustomPacketPayload.Type<SyncRagePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "rage_sync"));

    public SyncRagePacket(int rage) {
        this.rage = rage;
    }
    public SyncRagePacket(FriendlyByteBuf buf) {
        rage = buf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.rage);
    }


    public static void handle(SyncRagePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof Player player) {
                AbilityData.get(player).setRage(packet.rage);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
