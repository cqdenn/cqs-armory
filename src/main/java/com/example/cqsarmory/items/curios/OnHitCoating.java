package com.example.cqsarmory.items.curios;

import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class OnHitCoating extends SimpleDescriptiveCoating{
    public OnHitCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
    }

}
