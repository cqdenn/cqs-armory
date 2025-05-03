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
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("cqsarmory", name ));
    }

    public static final ResourceKey<DamageType> VOLCANO = register("volcano");
}
