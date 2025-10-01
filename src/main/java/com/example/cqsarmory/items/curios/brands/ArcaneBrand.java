package com.example.cqsarmory.items.curios.brands;

import com.example.cqsarmory.items.curios.PassiveAbilityBrand;

public class ArcaneBrand extends PassiveAbilityBrand {
    public ArcaneBrand() {
        super(new Properties().stacksTo(1), "brand");
        //this.showHeader = false; // prevent generative header since we have attributes
    }

    @Override
    protected int getCooldownTicks() {
        return 60 * 20;
    }
}
