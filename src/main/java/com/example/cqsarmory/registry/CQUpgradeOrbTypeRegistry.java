package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import io.redspace.ironsspellbooks.item.armor.UpgradeOrbType;
import io.redspace.ironsspellbooks.registries.UpgradeOrbTypeRegistry;
import net.minecraft.resources.ResourceKey;

public class CQUpgradeOrbTypeRegistry {

    public static ResourceKey<UpgradeOrbType> MAX_RAGE = ResourceKey.create(UpgradeOrbTypeRegistry.UPGRADE_ORB_REGISTRY_KEY, CqsArmory.id("max_rage"));
    public static ResourceKey<UpgradeOrbType> MAX_MOMENTUM = ResourceKey.create(UpgradeOrbTypeRegistry.UPGRADE_ORB_REGISTRY_KEY, CqsArmory.id("max_momentum"));
    public static ResourceKey<UpgradeOrbType> BLOCK_STRENGTH = ResourceKey.create(UpgradeOrbTypeRegistry.UPGRADE_ORB_REGISTRY_KEY, CqsArmory.id("block_strength"));
    public static ResourceKey<UpgradeOrbType> MAX_HEALTH = ResourceKey.create(UpgradeOrbTypeRegistry.UPGRADE_ORB_REGISTRY_KEY, CqsArmory.id("max_health"));
    public static ResourceKey<UpgradeOrbType> ARCANIST = ResourceKey.create(UpgradeOrbTypeRegistry.UPGRADE_ORB_REGISTRY_KEY, CqsArmory.id("arcanist"));
    public static ResourceKey<UpgradeOrbType> NECROMANCY = ResourceKey.create(UpgradeOrbTypeRegistry.UPGRADE_ORB_REGISTRY_KEY, CqsArmory.id("necromancy"));

}
