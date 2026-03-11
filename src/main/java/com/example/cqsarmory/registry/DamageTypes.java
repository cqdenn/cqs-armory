package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class DamageTypes {
    public static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, name ));
    }

    public static final ResourceKey<DamageType> VOLCANO = register("volcano");
    public static final ResourceKey<DamageType> MELEE_SKILL = register("melee_skill");
    public static final ResourceKey<DamageType> ARCHER_SKILL = register("archer_skill");
    public static final ResourceKey<DamageType> ARCANE_SKILL = register("arcane_skill");
    public static final ResourceKey<DamageType> NECROMANCY_SKILL = register("necromancy_skill");
    public static final ResourceKey<DamageType> BLEEDING = register("bleeding");
    public static final ResourceKey<DamageType> FIREWORK_PROJECTILE = register("firework_projectile");
    public static final ResourceKey<DamageType> BAT_PROJECTILE = register("bat_projectile");
    public static final ResourceKey<DamageType> ICE = register("ice");
    public static final ResourceKey<DamageType> THROWN_ITEM = register("thrown_item");
    public static final ResourceKey<DamageType> MAGE_AOE = register("mage_aoe");
    public static final ResourceKey<DamageType> HELLFIRE_MAGE_AOE = register("hellfire_mage_aoe");
    public static final ResourceKey<DamageType> BLIZZARD_MAGE_AOE = register("blizzard_mage_aoe");
    public static final ResourceKey<DamageType> SHOCKWAVE_MAGE_AOE = register("shockwave_mage_aoe");
}
