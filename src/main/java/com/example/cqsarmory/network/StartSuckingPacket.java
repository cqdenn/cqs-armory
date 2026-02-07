package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
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
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class StartSuckingPacket implements CustomPacketPayload {

    public StartSuckingPacket(FriendlyByteBuf buf) {

    }

    public StartSuckingPacket() {

    }

    public void write(FriendlyByteBuf buf) {

    }

    public static final StreamCodec<RegistryFriendlyByteBuf, StartSuckingPacket> STREAM_CODEC = CustomPacketPayload.codec(StartSuckingPacket::write, StartSuckingPacket::new);

    public static final CustomPacketPayload.Type<StartSuckingPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "sucking"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(StartSuckingPacket packet, IPayloadContext context) {
        /*context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.getMainHandItem().is(ItemRegistry.SOUL_SUCKER)) {
//                    serverPlayer.startUsingItem(InteractionHand.MAIN_HAND);
                    var proj = serverPlayer.getForward().scale(4);
                    var entities = serverPlayer.level().getEntities(serverPlayer, serverPlayer.getBoundingBox().expandTowards(proj), entity -> {
                        return Utils.checkEntityIntersecting(entity, serverPlayer.getEyePosition(), (serverPlayer.getEyePosition().add(proj)), 0.15f).getType() == HitResult.Type.ENTITY;
                    });
                    if (serverPlayer.level().getGameTime() % 20 == 0) {
                        MagicManager.spawnParticles(serverPlayer.level(), ParticleTypes.HEART, ((serverPlayer.getForward().x()) * 2) + serverPlayer.getEyePosition().x(), ((serverPlayer.getForward().y()) * 2) + serverPlayer.getEyePosition().y(), ((serverPlayer.getForward().z()) * 2) + serverPlayer.getEyePosition().z(), 1, 0, 0, 0, 0, false);
                        MagicManager.spawnParticles(serverPlayer.level(), ParticleTypes.POOF, ((serverPlayer.getForward().x()) * 5) + serverPlayer.getEyePosition().x(), ((serverPlayer.getForward().y()) * 5) + serverPlayer.getEyePosition().y(), ((serverPlayer.getForward().z()) * 5) + serverPlayer.getEyePosition().z(), 3, 0, 0, 0, 1, false);
                        serverPlayer.level().playSound(
                                null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.WIND_CHARGE_BURST, serverPlayer.getSoundSource(), 2F, 0.5F
                        );
                    }
                    if (entities.size() > 0) {
                        for (Entity Bob : entities) {
                            Bob.push(serverPlayer.getLookAngle().scale(0.2).subtract(0, 0, 0));
                            serverPlayer.heal(0.25f);
                        }
                    }
                }
            }
        });*/
    }
}
