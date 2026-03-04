package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.DoubleJumpData;
import com.example.cqsarmory.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
                if (player.isInLiquid() || player.onGround() || AbilityData.get(player).getMomentum() < 5) return;
                Vec3 push = packet.motion.yRot(Mth.DEG_TO_RAD * -player.getYRot()).normalize();
                if (DoubleJumpData.get(player).jumps > 0) {
                    Vec3 motion = push.scale(0.25);
                    double y = player.getDeltaMovement().y < 0 ? 0.75 + player.getDeltaMovement().scale(-1).y : 0.75;
                    player.push(motion.x, y, motion.z);
                    player.hurtMarked = true;
                    DoubleJumpData.get(player).jumps--;
                    player.addEffect(new MobEffectInstance(MobEffectRegistry.FALL_DAMAGE_IMMUNITY, 20, 0, false, false, false));

                    float newMomentumTest = (AbilityData.get(player).getMomentum() - 5 + (float) player.getAttributeValue(AttributeRegistry.MOMENTUM_MOVEMENT_COST));
                    float newMomentum = newMomentumTest > player.getAttribute(AttributeRegistry.MIN_MOMENTUM).getValue() ? newMomentumTest : (float) player.getAttribute(AttributeRegistry.MIN_MOMENTUM).getValue();
                    AbilityData.get(player).setMomentum(newMomentum);
                    PacketDistributor.sendToPlayer(player, new SyncMomentumPacket((int) newMomentum));

                } else if (DoubleJumpData.get(player).dashes > 0) {
                    Vec3 motion = push.scale(0.75);
                    player.push(motion.x, 0.25, motion.z);
                    player.hurtMarked = true;
                    DoubleJumpData.get(player).dashes--;
                    player.addEffect(new MobEffectInstance(MobEffectRegistry.FALL_DAMAGE_IMMUNITY, 20, 0, false, false, false));

                    float newMomentumTest = (AbilityData.get(player).getMomentum() - 5 + (float) player.getAttributeValue(AttributeRegistry.MOMENTUM_MOVEMENT_COST));
                    float newMomentum = newMomentumTest > player.getAttribute(AttributeRegistry.MIN_MOMENTUM).getValue() ? newMomentumTest : (float) player.getAttribute(AttributeRegistry.MIN_MOMENTUM).getValue();
                    AbilityData.get(player).setMomentum(newMomentum);
                    PacketDistributor.sendToPlayer(player, new SyncMomentumPacket((int) newMomentum));

                }
            }
        });
    }
}
