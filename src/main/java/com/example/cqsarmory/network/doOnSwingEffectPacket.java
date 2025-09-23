package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.items.curios.OnSwingCoating;
import com.example.cqsarmory.registry.ItemRegistry;
import com.example.cqsarmory.utils.CQtils;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class doOnSwingEffectPacket implements CustomPacketPayload {

    public doOnSwingEffectPacket(FriendlyByteBuf buf) {

    }

    public doOnSwingEffectPacket() {

    }

    public void write(FriendlyByteBuf buf) {

    }

    public static final StreamCodec<RegistryFriendlyByteBuf, doOnSwingEffectPacket> STREAM_CODEC = CustomPacketPayload.codec(doOnSwingEffectPacket::write, doOnSwingEffectPacket::new);

    public static final CustomPacketPayload.Type<doOnSwingEffectPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "swing"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(doOnSwingEffectPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            var coatingSlot = CQtils.getPlayerCurioStack(player, "coating");
            if (!coatingSlot.isEmpty() && coatingSlot.getItem() instanceof OnSwingCoating coating) {
                coating.doOnSwingEffect(player, (float) (player.getAttackStrengthScale(0) * player.getAttributeValue(Attributes.ATTACK_DAMAGE)));
            }
        });
    }
}
