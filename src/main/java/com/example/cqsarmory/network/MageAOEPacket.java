package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.items.curios.AOEBrandItem;
import com.example.cqsarmory.registry.MobEffectRegistry;
import com.example.cqsarmory.utils.CQtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MageAOEPacket implements CustomPacketPayload {

    public MageAOEPacket(FriendlyByteBuf buf) {

    }

    public MageAOEPacket() {

    }

    public void write(FriendlyByteBuf buf) {

    }

    public static final StreamCodec<RegistryFriendlyByteBuf, MageAOEPacket> STREAM_CODEC = CustomPacketPayload.codec(MageAOEPacket::write, MageAOEPacket::new);

    public static final CustomPacketPayload.Type<MageAOEPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "mage_aoe"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MageAOEPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            float defaultMinManaSpent = CQtils.getDefaultManaSpent(player);
            int seconds = CQtils.getAOETimeSeconds(player);

            if (AbilityData.get(player).manaSpentSinceLastAOE >= defaultMinManaSpent) {
                if (CQtils.getPlayerCurioStacks(player, "brand").isEmpty()) {
                    player.addEffect(new MobEffectInstance(MobEffectRegistry.GENERIC_MAGE_AOE, seconds * 20, 0, false, false, true));
                } else {
                    for (ItemStack stack : CQtils.getPlayerCurioStacks(player, "brand")) {
                        if (stack.getItem() instanceof AOEBrandItem brand) {
                            player.addEffect(brand.aoeEffect(seconds));
                        }
                    }
                }
                AbilityData.get(player).manaSpentSinceLastAOE = 0;
                PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncManaSpentPacket(0));
            }
        });
    }
}
