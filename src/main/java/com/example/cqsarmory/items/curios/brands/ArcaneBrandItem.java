package com.example.cqsarmory.items.curios.brands;

import com.example.cqsarmory.items.curios.PassiveAbilityBrand;
import io.redspace.ironsspellbooks.api.events.ChangeManaEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.item.curios.PassiveAbilityCurio;
import io.redspace.ironsspellbooks.network.SyncManaPacket;
import io.redspace.ironsspellbooks.render.CinderousRarity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;

public class ArcaneBrandItem extends PassiveAbilityBrand {
    public ArcaneBrandItem() {
        super(new Properties().stacksTo(1), "brand");
        //this.showHeader = false; // prevent generative header since we have attributes
    }

    @Override
    protected int getCooldownTicks() {
        return 60 * 20;
    }
}
