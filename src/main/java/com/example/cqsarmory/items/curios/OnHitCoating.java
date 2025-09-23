package com.example.cqsarmory.items.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class OnHitCoating extends SimpleDescriptiveCoating{
    public OnHitCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
    }
}
