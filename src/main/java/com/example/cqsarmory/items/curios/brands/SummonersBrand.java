package com.example.cqsarmory.items.curios.brands;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.items.curios.SimpleDescriptiveBrand;
import com.example.cqsarmory.network.SyncElementalistStacksPacket;
import com.example.cqsarmory.network.SyncSummonersStacksPacket;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.ItemRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber
public class SummonersBrand extends SimpleDescriptiveBrand {
    public SummonersBrand(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @SubscribeEvent
    public static void damage(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        float damagePerStack = 0.02f;
        int summonStacks = AbilityData.get(player).summonersStacks.summonsAlive;

        ResourceLocation summonID = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "summon_stacks_modifier");
        AttributeModifier summonMod = new AttributeModifier(summonID, damagePerStack * summonStacks, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        AttributeInstance summonInstance = player.getAttribute(AttributeRegistry.NECROMANCY_SKILL_POWER);
        summonInstance.removeModifier(summonMod.id());

        if (summonStacks > 0) {
            summonInstance.addTransientModifier(summonMod);
        }

    }

    @SubscribeEvent
    public static void stacks(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player && !entity.level().isClientSide) {
            int newSummons = Math.min(SummonManager.getSummons(player).size(), 15);
            AbilityData.get(player).summonersStacks.summonsAlive = newSummons;
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncSummonersStacksPacket(newSummons));
        }
    }
}

