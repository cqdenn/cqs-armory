package com.example.cqsarmory.items.curios;

import io.redspace.ironsspellbooks.item.curios.PassiveAbilityCurio;

public class PassiveAbilityBooster extends PassiveAbilityCurio {
    public PassiveAbilityBooster(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    protected int getCooldownTicks() {
        return 0;
    }
}
