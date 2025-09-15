package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.items.curios.QuiverItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FireworkQuiver extends QuiverItem {
    public FireworkQuiver(Properties properties, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, spellDataRegistryHolders);
    }

    @Override
    public Projectile getCustomProjectile(Projectile arrow, Player shooter, ItemStack projectileStack, ItemStack weaponStack) {
        ItemStack fireworkStack = new ItemStack(Items.FIREWORK_ROCKET);
        return new FireworkRocketEntity(shooter.level(), fireworkStack, shooter, shooter.getX(), shooter.getEyeY() - 0.15F, shooter.getZ(), true);
    }
}
