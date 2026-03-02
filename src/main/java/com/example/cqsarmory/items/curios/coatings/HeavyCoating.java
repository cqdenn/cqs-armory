package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.PassiveAbilityCoating;

public class HeavyCoating extends PassiveAbilityCoating {
    public HeavyCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    protected int getCooldownTicks() {
        return 40;
    }
}
