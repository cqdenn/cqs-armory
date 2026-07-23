package com.example.cqsarmory.items.curios;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import io.redspace.skillcasting.data.skill.ISkillContainer;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class QuiverItem extends CurioBaseItem {

    public QuiverItem(Properties properties, SkillData... spellDataRegistryHolders) {
        super(properties.component(ComponentRegistry.IMBUED_SPELL_CONTAINER, ISkillContainer.create(true, spellDataRegistryHolders)));
    }

    public CurioBaseItem withQuiverAttributes(int capacity) {
        return withAttributes("quiver", new AttributeContainer(AttributeRegistry.QUIVER_CAPACITY, capacity, AttributeModifier.Operation.ADD_VALUE));
    }

    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        AbilityArrow abilityArrow = new AbilityArrow(shooter.level());
        abilityArrow.copyStats(arrow, shooter, arrowDmg);
        return abilityArrow;
    }

    public void playCustomBowShootSound (Level level, Player player, double x, double y, double z) {
        level.playSound(
                null,
                x,
                y,
                z,
                SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS,
                1.0F,
                1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + AbilityData.get(player).bowVelocity * 0.5F
        );
    }
}
