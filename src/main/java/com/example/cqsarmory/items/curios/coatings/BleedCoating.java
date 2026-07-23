package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.OnHitCoating;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import io.redspace.skillcasting.data.skill.ISkillContainer;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class BleedCoating extends OnHitCoating {
    public BleedCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    public BleedCoating(Properties properties, String slot, SkillData... spellDataRegistryHolders) {
        super(properties.component(ComponentRegistry.IMBUED_SPELL_CONTAINER, ISkillContainer.create(true, spellDataRegistryHolders)), slot);
    }

    @Override
    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
        CQtils.addBleedStacks(attacker, target, 1, 80);
    }
}
