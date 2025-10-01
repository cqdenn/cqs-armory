package com.example.cqsarmory.items.curios.brands;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.items.curios.OnHitBrand;
import com.example.cqsarmory.network.SyncElementalistStacksPacket;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber
public class ElementalBrand extends OnHitBrand {
    public ElementalBrand(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage, DamageSource source) {
        if (source.is(ISSDamageTypes.FIRE_MAGIC)) {
            int newFire = Math.min(AbilityData.get(attacker).elementalistStacks.fireStacks + 1, 15);
            AbilityData.get(attacker).elementalistStacks.fireStacks = newFire;
            AbilityData.get(attacker).elementalistStacks.iceStacks = 0;
            AbilityData.get(attacker).elementalistStacks.lightningStacks = 0;
            PacketDistributor.sendToPlayer((ServerPlayer) attacker, new SyncElementalistStacksPacket(newFire, 0, 0));
            AbilityData.get(attacker).elementalistStacks.stackDecayTime = attacker.tickCount + 200;
        } else if (source.is(ISSDamageTypes.ICE_MAGIC)) {
            int newIce = Math.min(AbilityData.get(attacker).elementalistStacks.iceStacks + 1, 15);
            AbilityData.get(attacker).elementalistStacks.fireStacks = 0;
            AbilityData.get(attacker).elementalistStacks.iceStacks = newIce;
            AbilityData.get(attacker).elementalistStacks.lightningStacks = 0;
            PacketDistributor.sendToPlayer((ServerPlayer) attacker, new SyncElementalistStacksPacket(0, newIce, 0));
            AbilityData.get(attacker).elementalistStacks.stackDecayTime = attacker.tickCount + 200;
        } else if (source.is(ISSDamageTypes.LIGHTNING_MAGIC)) {
            int newLighting = Math.min(AbilityData.get(attacker).elementalistStacks.lightningStacks + 1, 15);
            AbilityData.get(attacker).elementalistStacks.fireStacks = 0;
            AbilityData.get(attacker).elementalistStacks.iceStacks = 0;
            AbilityData.get(attacker).elementalistStacks.lightningStacks = newLighting;
            PacketDistributor.sendToPlayer((ServerPlayer) attacker, new SyncElementalistStacksPacket(0, 0, newLighting));
            AbilityData.get(attacker).elementalistStacks.stackDecayTime = attacker.tickCount + 200;
        }
    }

    @SubscribeEvent
    public static void damage(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        float damagePerStack = 0.02f;
        int fireStacks = AbilityData.get(player).elementalistStacks.fireStacks;
        int iceStacks = AbilityData.get(player).elementalistStacks.iceStacks;
        int lightningStacks = AbilityData.get(player).elementalistStacks.lightningStacks;

        ResourceLocation fireID = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "elemental_fire_modifier");
        AttributeModifier fireMod = new AttributeModifier(fireID, damagePerStack * fireStacks, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        AttributeInstance fireInstance = player.getAttribute(AttributeRegistry.FIRE_SPELL_POWER);
        fireInstance.removeModifier(fireMod.id());

        if (fireStacks > 0) {
            fireInstance.addTransientModifier(fireMod);
        }

        ResourceLocation iceID = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "elemental_ice_modifier");
        AttributeModifier iceMod = new AttributeModifier(iceID, damagePerStack * iceStacks, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        AttributeInstance iceInstance = player.getAttribute(AttributeRegistry.ICE_SPELL_POWER);
        iceInstance.removeModifier(iceMod.id());

        if (iceStacks > 0) {
            iceInstance.addTransientModifier(iceMod);
        }

        ResourceLocation lightningID = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "elemental_lightning_modifier");
        AttributeModifier lightningMod = new AttributeModifier(lightningID, damagePerStack * lightningStacks, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        AttributeInstance lightningInstance = player.getAttribute(AttributeRegistry.LIGHTNING_SPELL_POWER);
        lightningInstance.removeModifier(lightningMod.id());

        if (lightningStacks > 0) {
            lightningInstance.addTransientModifier(lightningMod);
        }

    }

    @SubscribeEvent
    public static void stackDecay(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) return;
        if (AbilityData.get(player).elementalistStacks.stackDecayTime <= player.tickCount && player.level().getGameTime() % 20 == 0) {
            int newFire = Math.max(AbilityData.get(player).elementalistStacks.fireStacks - 1, 0);
            int newIce = Math.max(AbilityData.get(player).elementalistStacks.iceStacks - 1, 0);
            int newLightning = Math.max(AbilityData.get(player).elementalistStacks.lightningStacks - 1, 0);

            AbilityData.get(player).elementalistStacks.fireStacks = newFire;
            AbilityData.get(player).elementalistStacks.iceStacks = newIce;
            AbilityData.get(player).elementalistStacks.lightningStacks = newLightning;
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncElementalistStacksPacket(newFire, newIce, newLightning));
        }
    }
}
