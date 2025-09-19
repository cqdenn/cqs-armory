package com.example.cqsarmory.items.curios;

import io.redspace.ironsspellbooks.item.curios.PassiveAbilityCurio;

public class PassiveAbilityBrand extends PassiveAbilityCurio {
    public PassiveAbilityBrand(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    protected int getCooldownTicks() {
        return 0;
    }
}
