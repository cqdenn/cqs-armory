package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.LightningArrow;
import com.example.cqsarmory.items.curios.SimpleDescriptiveQuiver;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class LightningQuiver extends SimpleDescriptiveQuiver {
    public LightningQuiver(Properties properties, String slotIdentifier, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, slotIdentifier, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        LightningArrow lightningArrow = new LightningArrow(arrow.level());
        lightningArrow.copyStats(arrow, shooter, arrowDmg);
        return lightningArrow;
    }
}
