package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.FireArrow;
import com.example.cqsarmory.items.curios.SimpleDescriptiveQuiver;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;

public class FireQuiver extends SimpleDescriptiveQuiver {

    public FireQuiver(Properties properties, String slotIdentifier, SkillData... spellDataRegistryHolders) {
        super(properties, slotIdentifier, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        FireArrow abilityArrow = new FireArrow(shooter.level());
        abilityArrow.copyStats(arrow, shooter, arrowDmg);
        //abilityArrow.setBaseDamage(arrowDmg * shooter.getAttributeValue(AttributeRegistry.FIRE_SPELL_POWER));
        return abilityArrow;
    }
}
