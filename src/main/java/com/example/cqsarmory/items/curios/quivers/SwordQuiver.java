package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.ThrownItemProjectile;
import com.example.cqsarmory.items.curios.SimpleDescriptiveQuiver;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SwordQuiver extends SimpleDescriptiveQuiver {
    public SwordQuiver(Properties properties, String slotIdentifier, SkillData... spellDataRegistryHolders) {
        super(properties, slotIdentifier, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        double newDamage = (arrowDmg * 0.5) + ((shooter.getAttributeValue(Attributes.ATTACK_DAMAGE) - 1) * 0.75);
        ThrownItemProjectile projectile = new ThrownItemProjectile(shooter.level(), new ItemStack(Items.IRON_SWORD), 0.1, 0.75);
        projectile.copyStats(arrow, shooter, (float) arrowDmg);
        return projectile;
    }
}
