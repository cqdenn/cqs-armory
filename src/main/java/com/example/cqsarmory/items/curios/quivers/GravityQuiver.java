package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.items.curios.QuiverItem;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class GravityQuiver extends QuiverItem {
    public GravityQuiver(Properties properties, SkillData... spellDataRegistryHolders) {
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
