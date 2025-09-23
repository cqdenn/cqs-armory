package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.items.curios.QuiverItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class GravityQuiver extends QuiverItem {
    public GravityQuiver(Properties properties, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        AbilityArrow abilityArrow = new AbilityArrow(shooter.level());
        abilityArrow.copyStats(arrow, shooter, arrowDmg);
        abilityArrow.setNoGravity(true);
        return abilityArrow;
    }
}
