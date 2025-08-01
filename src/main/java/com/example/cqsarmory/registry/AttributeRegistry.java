package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.attribute.MagicPercentAttribute;
import io.redspace.ironsspellbooks.api.attribute.MagicRangedAttribute;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = CqsArmory.MODID, bus = EventBusSubscriber.Bus.MOD)
public class AttributeRegistry {

    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

    public static final DeferredHolder<Attribute, Attribute> DODGE_CHANCE = ATTRIBUTES.register("dodge_chance", () -> (new PercentageAttribute("attribute.cqs_armory.dodge_chance", 0.0D, 0.0D, 1.0D, 100).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> BLOCK_STRENGTH = ATTRIBUTES.register("block_strength", () -> (new RangedAttribute("attribute.cqs_armory.block_strength", 0.0D, 0.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MAX_RAGE = ATTRIBUTES.register("max_rage", () -> (new RangedAttribute("attribute.cqs_armory.max_rage", 10.0D, 0.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MIN_RAGE = ATTRIBUTES.register("min_rage", () -> (new RangedAttribute("attribute.cqs_armory.min_rage", 0.0D, 0.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> RAGE_ON_HIT = ATTRIBUTES.register("rage_on_hit", () -> (new RangedAttribute("attribute.cqs_armory.rage_on_hit", 1.0D, 1.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> RAGE_DAMAGE = ATTRIBUTES.register("rage_damage", () -> (new PercentageAttribute("attribute.cqs_armory.rage_damage", 0.01D, 0.00D, 10000.0D, 100).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> RAGE_SPEED = ATTRIBUTES.register("rage_speed", () -> (new PercentageAttribute("attribute.cqs_armory.rage_speed", 0.00D, 0.00D, 10000.0D, 100).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MAX_MOMENTUM = ATTRIBUTES.register("max_momentum", () -> (new RangedAttribute("attribute.cqs_armory.max_momentum", 100.0D, 0.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MIN_MOMENTUM = ATTRIBUTES.register("min_momentum", () -> (new RangedAttribute("attribute.cqs_armory.min_momentum", 0.0D, 0.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MOMENTUM_ON_HIT = ATTRIBUTES.register("momentum_on_hit", () -> (new RangedAttribute("attribute.cqs_armory.momentum_on_hit", 1.0D, 1.0D, 10000.0D).setSyncable(true)));

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent e) {
        e.getTypes().forEach(entity -> ATTRIBUTES.getEntries().forEach(attribute -> e.add(entity, attribute)));
    }

}