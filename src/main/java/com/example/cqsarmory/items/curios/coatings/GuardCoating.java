package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.items.curios.OnBlockCoating;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class GuardCoating extends OnBlockCoating {
    public GuardCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnBlockEffect(Player blocker, Entity directEntity, Entity causingEntity, float hitDamage, int blockLength, boolean isPerfect) {
        if (isPerfect) {
            AbilityData.get(blocker).cancelNextShieldDropCooldown = true;
        }
    }
}
