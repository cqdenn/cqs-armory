package com.example.cqsarmory.items.curios.brands;

import com.example.cqsarmory.items.curios.AOEBrandItem;
import com.example.cqsarmory.registry.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;

public class HellfireAOEBrand extends AOEBrandItem {
    public HellfireAOEBrand(boolean fireRes) {
        super(fireRes);
    }

    @Override
    public MobEffectInstance aoeEffect (int seconds) {
        return new MobEffectInstance(MobEffectRegistry.HELLFIRE_MAGE_AOE, seconds * 20, 0, false, false, true);
    }
}
