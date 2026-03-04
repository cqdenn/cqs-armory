package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.ScatterProjectile;
import com.example.cqsarmory.items.curios.QuiverItem;
import com.example.cqsarmory.items.curios.SimpleDescriptiveQuiver;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class ScatterQuiver extends SimpleDescriptiveQuiver {
    public ScatterQuiver(Properties properties, String slotIdentifier, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, slotIdentifier, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        ScatterProjectile scatter = new ScatterProjectile(shooter.level(), 1, 5, true);
        scatter.copyStats(arrow, shooter, arrowDmg);
        return scatter;
    }


}
