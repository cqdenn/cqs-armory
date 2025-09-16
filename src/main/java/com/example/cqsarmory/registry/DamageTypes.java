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
}
