package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import io.redspace.bowattributes.BowAttributeLib;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.attribute.MagicPercentAttribute;
import io.redspace.ironsspellbooks.api.attribute.MagicRangedAttribute;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = CqsArmory.MODID, bus = EventBusSubscriber.Bus.MOD)
public class AttributeRegistry {

    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

    public static final DeferredHolder<Attribute, Attribute> DODGE_CHANCE = ATTRIBUTES.register("dodge_chance", () -> (new PercentageAttribute("attribute.cqs_armory.dodge_chance", 0.0D, 0.0D, 1.0D, 100).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> BLOCK_STRENGTH = ATTRIBUTES.register("block_strength", () -> (new RangedAttribute("attribute.cqs_armory.block_strength", 0.0D, 0.0D, 10000.0D) {public @Nullable ResourceLocation getBaseId() {
        return CqsArmory.BASE_BLOCK_STRENGTH_ID;
    }}.setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MAX_RAGE = ATTRIBUTES.register("max_rage", () -> (new RangedAttribute("attribute.cqs_armory.max_rage", 10.0D, 0.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MIN_RAGE = ATTRIBUTES.register("min_rage", () -> (new RangedAttribute("attribute.cqs_armory.min_rage", 0.0D, 0.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> RAGE_ON_HIT = ATTRIBUTES.register("rage_on_hit", () -> (new RangedAttribute("attribute.cqs_armory.rage_on_hit", 1.0D, 1.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> RAGE_DAMAGE = ATTRIBUTES.register("rage_damage", () -> (new PercentageAttribute("attribute.cqs_armory.rage_damage", 0.01D, 0.00D, 10000.0D, 100).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> RAGE_SPEED = ATTRIBUTES.register("rage_speed", () -> (new PercentageAttribute("attribute.cqs_armory.rage_speed", 0.00D, 0.00D, 10000.0D, 100).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MAX_MOMENTUM = ATTRIBUTES.register("max_momentum", () -> (new RangedAttribute("attribute.cqs_armory.max_momentum", 100.0D, 0.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MIN_MOMENTUM = ATTRIBUTES.register("min_momentum", () -> (new RangedAttribute("attribute.cqs_armory.min_momentum", 0.0D, 0.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MOMENTUM_ON_HIT = ATTRIBUTES.register("momentum_on_hit", () -> (new RangedAttribute("attribute.cqs_armory.momentum_on_hit", 1.0D, 1.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MOMENTUM_ORBS_SPAWNED = ATTRIBUTES.register("momentum_orbs_spawned", () -> (new RangedAttribute("attribute.cqs_armory.momentum_orbs_spawned", 1.0D, 1.0D, 5.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> QUIVER_CAPACITY = ATTRIBUTES.register("quiver_capacity", () -> (new RangedAttribute("attribute.cqs_armory.quiver_capacity", 0.0D, 0.0D, 10000.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> EXPLOSIVE_DAMAGE = ATTRIBUTES.register("explosive_damage", () -> (new RangedAttribute("attribute.cqs_armory.explosive_damage", 1.0D, -100.0D, 100.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> ARROW_PIERCING = ATTRIBUTES.register("arrow_pierce", () -> (new RangedAttribute("attribute.cqs_armory.arrow_pierce", 0.0D, 0.0D, 100.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> AUTO_CRIT = ATTRIBUTES.register("auto_crit", () -> (new RangedAttribute("attribute.cqs_armory.auto_crit", 0.0D, 0.0D, 100.0D).setSyncable(true)));
    public static final DeferredHolder<Attribute, Attribute> MELEE_SKILL_RESIST = newResistanceAttribute("melee");
    public static final DeferredHolder<Attribute, Attribute> MELEE_SKILL_POWER = newSkillAttribute("melee");
    public static final DeferredHolder<Attribute, Attribute> ARCHER_SKILL_RESIST = newResistanceAttribute("archer");
    public static final DeferredHolder<Attribute, Attribute> ARCHER_SKILL_POWER = newSkillAttribute("archer");
    public static final DeferredHolder<Attribute, Attribute> ARCANE_SKILL_RESIST = newResistanceAttribute("arcane");
    public static final DeferredHolder<Attribute, Attribute> ARCANE_SKILL_POWER = newSkillAttribute("arcane");
    public static final DeferredHolder<Attribute, Attribute> NECROMANCY_SKILL_RESIST = newResistanceAttribute("necromancy");
    public static final DeferredHolder<Attribute, Attribute> NECROMANCY_SKILL_POWER = newSkillAttribute("necromancy");


    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent e) {
        e.getTypes().forEach(entity -> ATTRIBUTES.getEntries().forEach(attribute -> e.add(entity, attribute)));
    }

    private static DeferredHolder<Attribute, Attribute> newResistanceAttribute(String id) {
        return (DeferredHolder<Attribute, Attribute>) ATTRIBUTES.register(id + "_spell_resist", () -> (new MagicPercentAttribute("attribute.cqs_armory." + id + "_spell_resist", 1.0D, -100, 100).setSyncable(true)));
    }

    private static DeferredHolder<Attribute, Attribute> newSkillAttribute(String id) {
        return ATTRIBUTES.register(id + "_spell_power", () -> (new MagicPercentAttribute("attribute.cqs_armory." + id + "_spell_power", 1.0D, -100, 100).setSyncable(true)));
    }

}