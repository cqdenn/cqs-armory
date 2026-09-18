package com.example.cqsarmory.items.curios.brands;

import com.example.cqsarmory.items.curios.AOEBrandItem;
import com.example.cqsarmory.registry.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;

public class HealingAOEBrand extends AOEBrandItem {
    public HealingAOEBrand(boolean fireRes) {
        super(fireRes);
    }

    @Override
    public MobEffectInstance aoeEffect (int seconds) {
        return new MobEffectInstance(MobEffectRegistry.HEALING_MAGE_AOE, seconds * 20, 0, false, false, true);
    }
}
