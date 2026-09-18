package com.example.cqsarmory.items.curios.brands;

import com.example.cqsarmory.items.curios.AOEBrandItem;
import com.example.cqsarmory.registry.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;

public class BlizzardAOEBrand extends AOEBrandItem {
    public BlizzardAOEBrand(boolean fireRes) {
        super(fireRes);
    }

    @Override
    public MobEffectInstance aoeEffect (int seconds) {
        return new MobEffectInstance(MobEffectRegistry.BLIZZARD_MAGE_AOE, seconds * 20, 0, false, false, true);
    }
}
