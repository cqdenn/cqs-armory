package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.utils.RenderingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SmashParticlePacket implements CustomPacketPayload {

    private final BlockPos pos;
    public static final CustomPacketPayload.Type<SmashParticlePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "smash_particles"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SmashParticlePacket> STREAM_CODEC = CustomPacketPayload.codec(SmashParticlePacket::write, SmashParticlePacket::new);

    public SmashParticlePacket(BlockPos pos) {
        this.pos = pos;
    }

    public SmashParticlePacket(FriendlyByteBuf buf) {
        pos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(SmashParticlePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            RenderingUtils.renderSmashParticles(packet.pos);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


}
