package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.FireArrow;
import com.example.cqsarmory.items.curios.QuiverItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class FireQuiver extends QuiverItem {
    public FireQuiver(Properties properties, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        FireArrow abilityArrow = new FireArrow(shooter.level());
        abilityArrow.copyStats(arrow, shooter);
        return abilityArrow;
    }
}
