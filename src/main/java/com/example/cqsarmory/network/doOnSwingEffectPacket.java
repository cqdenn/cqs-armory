package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.items.curios.OnSwingCoating;
import com.example.cqsarmory.utils.CQtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
            for (ItemStack stack : CQtils.getPlayerCurioStacks(player, "coating")) {
                if (stack.getItem() instanceof OnSwingCoating coating && player.getAttackStrengthScale(0) >= 0.8) {
                    coating.doOnSwingEffect(player, (float) (player.getAttackStrengthScale(0) * player.getAttributeValue(Attributes.ATTACK_DAMAGE)));
                }
            }
        });
    }
}
