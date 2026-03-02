package com.example.cqsarmory.items.curios;

import io.redspace.ironsspellbooks.item.curios.PassiveAbilityCurio;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PassiveAbilityCoating extends PassiveAbilityCurio {
    public PassiveAbilityCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    protected int getCooldownTicks() {
        return 0;
    }
}
