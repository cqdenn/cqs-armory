package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.util.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class CQSchoolRegistry {
    public static final ResourceKey<Registry<SchoolType>> SCHOOL_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "schools"));
    private static final DeferredRegister<SchoolType> SCHOOLS = DeferredRegister.create(SCHOOL_REGISTRY_KEY, IronsSpellbooks.MODID);
    public static final Registry<SchoolType> REGISTRY = new RegistryBuilder<>(SCHOOL_REGISTRY_KEY).create();

    public static void register(IEventBus eventBus) {
        SCHOOLS.register(eventBus);
    }

    public static void registerRegistry(NewRegistryEvent event) {
        IronsSpellbooks.LOGGER.debug("SchoolRegistry.registerRegistry");
        event.register(REGISTRY);
    }


    private static Supplier<SchoolType> registerSchool(SchoolType schoolType) {
        return SCHOOLS.register(schoolType.getId().getPath(), () -> schoolType);
    }

    public static SchoolType getSchool(ResourceLocation resourceLocation) {
        return REGISTRY.get(resourceLocation);
    }


    public static final ResourceLocation MELEE_RESOURCE = CqsArmory.id("melee");
    public static final ResourceLocation ARCHER_RESOURCE = CqsArmory.id("archer");

    public static final Supplier<SchoolType> MELEE = registerSchool(new SchoolType(
            MELEE_RESOURCE,
            Tags.Items.INGOTS_IRON,
            Component.translatable("school.cqs_armory.melee").withStyle(ChatFormatting.DARK_RED),
            AttributeRegistry.MELEE_SKILL_POWER,
            AttributeRegistry.MELEE_SKILL_RESIST,
            SoundRegistry.MELEE_CAST_SOUND,
            DamageTypes.MELEE_SKILL
    ));

    public static final Supplier<SchoolType> ARCHER = registerSchool(new SchoolType(
            ARCHER_RESOURCE,
            com.example.cqsarmory.registry.Tags.Items.ARROWS,
            Component.translatable("school.cqs_armory.archer").withStyle(ChatFormatting.DARK_GREEN),
            AttributeRegistry.ARCHER_SKILL_POWER,
            AttributeRegistry.ARCHER_SKILL_RESIST,
            SoundRegistry.ARROW_SHOOT,
            DamageTypes.ARCHER_SKILL
    ));


}
