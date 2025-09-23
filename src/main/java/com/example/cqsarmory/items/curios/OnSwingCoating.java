package com.example.cqsarmory.items.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class OnSwingCoating extends SimpleDescriptiveCoating{
    public OnSwingCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    public void doOnSwingEffect(Player attacker, float hitDamage) {}
}
