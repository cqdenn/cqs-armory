package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.AnvilProjectile;
import com.example.cqsarmory.data.entity.ability.ThrownItemProjectile;
import com.example.cqsarmory.items.curios.QuiverItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AnvilQuiver extends QuiverItem {
    public AnvilQuiver(Properties properties, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        AnvilProjectile projectile = new AnvilProjectile(shooter.level());
        projectile.copyStats(arrow, shooter, arrowDmg);
        return projectile;
    }
}
