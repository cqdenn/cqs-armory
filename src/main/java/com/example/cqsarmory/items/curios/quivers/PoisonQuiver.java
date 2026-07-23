package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.items.curios.SimpleDescriptiveQuiver;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class PoisonQuiver extends SimpleDescriptiveQuiver {
    public PoisonQuiver(Properties properties, String slotIdentifier, SkillData... spellDataRegistryHolders) {
        super(properties, slotIdentifier, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        AbilityArrow abilityArrow = new AbilityArrow(shooter.level());
        abilityArrow.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 1));
        abilityArrow.copyStats(arrow, shooter, arrowDmg);
        return abilityArrow;
    }
}
