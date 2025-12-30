package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.DodgeData;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PlayerDodgePacket implements CustomPacketPayload {

    public Vec3 motion;

    public PlayerDodgePacket(Vec3 motion) {
        this.motion = motion;
    }
    public PlayerDodgePacket(FriendlyByteBuf buf) {
        motion = buf.readVec3();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeVec3(this.motion);
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerDodgePacket> STREAM_CODEC = CustomPacketPayload.codec(PlayerDodgePacket::write, PlayerDodgePacket::new);

    public static final CustomPacketPayload.Type<PlayerDodgePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "player_dodge"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PlayerDodgePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                Vec3 push = packet.motion.yRot(Mth.DEG_TO_RAD * -player.getYRot()).normalize();
                if (!player.onGround()) {push = push.scale(0.5);}
                player.push(push);
                player.hurtMarked = true;
                DodgeData.get(player).invulnerableTimeEnd = player.level().getGameTime() + 5;
            }
        });
    }
}
