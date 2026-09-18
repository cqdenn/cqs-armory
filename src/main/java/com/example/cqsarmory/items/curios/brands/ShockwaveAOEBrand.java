package com.example.cqsarmory.items.curios.brands;

import com.example.cqsarmory.items.curios.AOEBrandItem;
import com.example.cqsarmory.registry.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;

public class ShockwaveAOEBrand extends AOEBrandItem {
    public ShockwaveAOEBrand(boolean fireRes) {
        super(fireRes);
    }

    @Override
    public MobEffectInstance aoeEffect (int seconds) {
        return new MobEffectInstance(MobEffectRegistry.SHOCKWAVE_MAGE_AOE, 5, 0, false, false, true);
    }
}
