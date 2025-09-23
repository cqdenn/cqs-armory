package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.FireworkProjectile;
import com.example.cqsarmory.items.curios.QuiverItem;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.entity.spells.ExtendedFireworkRocket;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class FireworkQuiver extends QuiverItem {
    public FireworkQuiver(Properties properties, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        FireworkProjectile firework = new FireworkProjectile(shooter.level(), randomFireworkRocket(), shooter, shooter.getX(), shooter.getEyeY() - 0.15F, shooter.getZ(), true, arrowDmg * 0.6f);
        firework.copyStats(arrow, shooter, arrowDmg);
        return firework;
    }

    //praise Iron431
    private ItemStack randomFireworkRocket() {
        Random random = new Random();
        ItemStack rocket = new ItemStack(Items.FIREWORK_ROCKET);
        FireworkExplosion.Shape shape;
        byte type = (byte) (random.nextInt(3) * 2);
        if (random.nextFloat() < .08f) { //8% chance for creeper face explosion
            type = 3;
        }
        shape = FireworkExplosion.Shape.values()[type];
        var fireworks = new Fireworks(-1, List.of(new FireworkExplosion(shape, new IntImmutableList(randomColors()), new IntImmutableList(new int[0]), random.nextInt(3) == 0, random.nextInt(3) == 0)));
        rocket.set(DataComponents.FIREWORKS, fireworks);
        return rocket;
    }

    private int[] randomColors() {
        int[] colors = new int[3];
        Random random = new Random();

        for (int i = 0; i < colors.length; i++) {
            colors[i] = DYE_COLORS[random.nextInt(DYE_COLORS.length)];
        }
        return colors;
    }

    //https://minecraft.fandom.com/wiki/Dye#Color_values
    private static final int[] DYE_COLORS = {
            //1908001,
            11546150,
            6192150,
            //8606770,
            3949738,
            8991416,
            1481884,
            //10329495,
            //4673362,
            15961002,
            8439583,
            16701501,
            3847130,
            13061821,
            16351261,
            16383998
    };
}
