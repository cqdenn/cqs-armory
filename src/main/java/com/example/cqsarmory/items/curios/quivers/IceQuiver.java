package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.FireArrow;
import com.example.cqsarmory.data.entity.ability.IceArrow;
import com.example.cqsarmory.items.curios.QuiverItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class IceQuiver extends QuiverItem {
    public IceQuiver(Properties properties, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        IceArrow abilityArrow = new IceArrow(shooter.level());
        abilityArrow.copyStats(arrow, shooter, arrowDmg);
        return abilityArrow;
    }
}
