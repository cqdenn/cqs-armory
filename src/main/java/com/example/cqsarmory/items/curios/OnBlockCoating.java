package com.example.cqsarmory.items.curios;

import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class OnBlockCoating extends SimpleDescriptiveCoating{
    public OnBlockCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    public void doOnBlockEffect(Player blocker, Entity directEntity, Entity causingEntity, float hitDamage, int blockLength, boolean isPerfect) {
    }
}
