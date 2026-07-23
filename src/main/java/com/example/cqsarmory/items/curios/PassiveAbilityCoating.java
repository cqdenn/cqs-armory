package com.example.cqsarmory.items.curios;

import io.redspace.ironsspellbooks.item.curios.PassiveAbilityCurio;

public class PassiveAbilityCoating extends PassiveAbilityCurio {
    public PassiveAbilityCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    protected int getCooldownTicks() {
        return 0;
    }
}
