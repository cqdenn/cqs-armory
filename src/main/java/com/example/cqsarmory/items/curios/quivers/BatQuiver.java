package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.BatProjectile;
import com.example.cqsarmory.items.curios.SimpleDescriptiveQuiver;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class BatQuiver extends SimpleDescriptiveQuiver {
    public BatQuiver(Properties properties, String slotIdentifier, SkillData... spellDataRegistryHolders) {
        super(properties, slotIdentifier, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        BatProjectile bat = new BatProjectile(shooter.level(), shooter, arrowDmg);
        //bat.copyStats(arrow, shooter);
        if (arrow.isOnFire()) {
            bat.igniteForSeconds(100);
        }
        bat.setPos(arrow.position());
        bat.setWeaponItem(arrow.getWeaponItem());
        return bat;
    }
}
