package com.example.cqsarmory.items.curios.brands;

import com.example.cqsarmory.items.curios.AOEBrandItem;
import com.example.cqsarmory.registry.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;

public class GravityBrand extends AOEBrandItem {
    public GravityBrand(boolean fireRes) {
        super(fireRes);
    }

    @Override
    public MobEffectInstance aoeEffect(int seconds) {
        return new MobEffectInstance(MobEffectRegistry.GRAVITY_MAGE_AOE, 2, 0, false, false, true);
    }
}