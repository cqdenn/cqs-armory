package com.example.cqsarmory.items.curios;

import com.example.cqsarmory.registry.MobEffectRegistry;
import com.example.cqsarmory.utils.CQItemPropertyHelper;
import net.minecraft.world.effect.MobEffectInstance;

public class AOEBrandItem extends SimpleDescriptiveBrand{
    public AOEBrandItem(boolean fireRes) {
        super(CQItemPropertyHelper.weaponsetItem(fireRes).stacksTo(1), "brand");
    }

    public MobEffectInstance aoeEffect (int seconds) {
        return new MobEffectInstance(MobEffectRegistry.GENERIC_MAGE_AOE, seconds * 20, 0, false, false, true);
    }
}
