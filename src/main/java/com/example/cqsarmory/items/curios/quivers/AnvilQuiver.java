package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.AnvilProjectile;
import com.example.cqsarmory.items.curios.SimpleDescriptiveQuiver;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class AnvilQuiver extends SimpleDescriptiveQuiver {
    public AnvilQuiver(Properties properties, String slotIdentifier, SkillData... spellDataRegistryHolders) {
        super(properties, slotIdentifier, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        AnvilProjectile projectile = new AnvilProjectile(shooter.level());
        projectile.copyStats(arrow, shooter, arrowDmg);
        return projectile;
    }
}
