package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.BatProjectile;
import com.example.cqsarmory.items.curios.QuiverItem;
import com.example.cqsarmory.items.curios.SimpleDescriptiveQuiver;
import com.example.cqsarmory.registry.AttributeRegistry;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class BatQuiver extends SimpleDescriptiveQuiver {
    public BatQuiver(Properties properties, String slotIdentifier, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, slotIdentifier, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        BatProjectile bat = new BatProjectile(shooter.level(), shooter, arrowDmg * 0.33f);
        //bat.copyStats(arrow, shooter);
        bat.setPos(arrow.position());
        bat.setWeaponItem(arrow.getWeaponItem());
        return bat;
    }
}
