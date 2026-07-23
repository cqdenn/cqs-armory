package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static io.redspace.ironsspellbooks.api.registry.SchoolRegistry.SCHOOL_REGISTRY_KEY;

public class CQSchoolRegistry {
    private static final DeferredRegister<SchoolType> SCHOOLS = DeferredRegister.create(SCHOOL_REGISTRY_KEY, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        SCHOOLS.register(eventBus);
    }


    private static DeferredHolder<SchoolType, SchoolType> registerSchool(String name, Supplier<SchoolType> schoolType) {
        return SCHOOLS.register(name, schoolType);
    }

    public static final ResourceLocation MELEE_RESOURCE = CqsArmory.id("melee");
    public static final ResourceLocation ARCHER_RESOURCE = CqsArmory.id("archer");
    public static final ResourceLocation ARCANE_RESOURCE = CqsArmory.id("arcane");
    public static final ResourceLocation NECROMANCY_RESOURCE = CqsArmory.id("necromancy");

    public static final Supplier<SchoolType> MELEE = registerSchool("melee", () -> new SchoolType(
            Tags.Items.INGOTS_IRON,
            Component.translatable("school.cqs_armory.melee").withStyle(ChatFormatting.DARK_RED),
            CQSkillComponents.MELEE_POWER,
            AttributeRegistry.MELEE_SKILL_POWER,
            AttributeRegistry.MELEE_SKILL_RESIST,
            SoundRegistry.MELEE_CAST_SOUND,
            DamageTypes.MELEE_SKILL
    ));

    public static final Supplier<SchoolType> ARCHER = registerSchool("archer", () -> new SchoolType(
            com.example.cqsarmory.registry.Tags.Items.ARROWS,
            Component.translatable("school.cqs_armory.archer").withStyle(ChatFormatting.GREEN),
            CQSkillComponents.ARCHER_POWER,
            AttributeRegistry.ARCHER_SKILL_POWER,
            AttributeRegistry.ARCHER_SKILL_RESIST,
            SoundRegistry.ARROW_DRAW_SOUND,
            DamageTypes.ARCHER_SKILL
    ));

    /*public static final Supplier<SchoolType> ARCANE = registerSchool(new SchoolType(
            ARCANE_RESOURCE,
            Tags.Items.GEMS_AMETHYST,
            Component.translatable("school.cqs_armory.arcane").withStyle(ChatFormatting.DARK_PURPLE),
            AttributeRegistry.ARCANE_SKILL_POWER,
            AttributeRegistry.ARCANE_SKILL_RESIST,
            io.redspace.ironsspellbooks.registries.SoundRegistry.ENDER_CAST,
            DamageTypes.ARCANE_SKILL
    ));*/

    /*public static final Supplier<SchoolType> NECROMANCY = registerSchool(new SchoolType(
            NECROMANCY_RESOURCE,
            Tags.Items.BONES,
            Component.translatable("school.cqs_armory.necromancy").withStyle(ChatFormatting.GRAY),
            AttributeRegistry.NECROMANCY_SKILL_POWER,
            AttributeRegistry.NECROMANCY_SKILL_RESIST,
            io.redspace.ironsspellbooks.registries.SoundRegistry.BLOOD_CAST,
            DamageTypes.NECROMANCY_SKILL
    ));*/


}
