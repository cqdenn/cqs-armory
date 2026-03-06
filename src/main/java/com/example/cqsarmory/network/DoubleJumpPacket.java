package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.DoubleJumpData;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.ItemRegistry;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class DoubleJumpPacket implements CustomPacketPayload {

    public Vec3 motion;

    public DoubleJumpPacket(Vec3 motion) {
        this.motion = motion;
    }
    public DoubleJumpPacket(FriendlyByteBuf buf) {
        motion = buf.readVec3();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeVec3(this.motion);
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, DoubleJumpPacket> STREAM_CODEC = CustomPacketPayload.codec(DoubleJumpPacket::write, DoubleJumpPacket::new);

    public static final CustomPacketPayload.Type<DoubleJumpPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "double_jump"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DoubleJumpPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                if (player.isSpectator() || player.isInLiquid() || player.onGround() || AbilityData.get(player).getMomentum() < (5 - player.getAttributeValue(AttributeRegistry.MOMENTUM_MOVEMENT_COST_REDUCTION))) return;
                Vec3 push = packet.motion.yRot(Mth.DEG_TO_RAD * -player.getYRot()).normalize();
                if (DoubleJumpData.get(player).jumps > 0) {
                    Vec3 motion = push.scale(0.25);
                    double y = player.getDeltaMovement().y < 0 ? 0.75 + player.getDeltaMovement().scale(-1).y : 0.75;
                    CQtils.doMomentumMovement(player, new Vec3(motion.x, y, motion.z));
                    DoubleJumpData.get(player).jumps--;
                } else if (DoubleJumpData.get(player).dashes > 0) {
                    Vec3 motion = push.scale(0.75);
                    double y = player.getDeltaMovement().y < 0 ? 0.25 + player.getDeltaMovement().scale(-1).y : 0.25;
                    double x = (player.getDeltaMovement().x < 0 && motion.x > 0) || (player.getDeltaMovement().x > 0 && motion.x < 0) ? motion.x + player.getDeltaMovement().scale(-1).x : motion.x;
                    double z = (player.getDeltaMovement().z < 0 && motion.z > 0) || (player.getDeltaMovement().z > 0 && motion.z < 0) ? motion.z + player.getDeltaMovement().scale(-1).z : motion.z;
                    CQtils.doMomentumMovement(player, new Vec3(x, y, z));
                    DoubleJumpData.get(player).dashes--;
                }
            }
        });
    }
}
