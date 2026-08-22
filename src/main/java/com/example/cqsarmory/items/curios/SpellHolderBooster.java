package com.example.cqsarmory.items.curios;

import com.example.cqsarmory.registry.CQComponentRegistry;
import io.redspace.skillcasting.data.skill.ISkillContainer;
import io.redspace.skillcasting.data.skill.SkillData;

public class SpellHolderBooster extends BoosterBaseItem {

    public SpellHolderBooster(Properties properties, SkillData... spellDataRegistryHolders) {
        super(properties.component(CQComponentRegistry.WEAPON_SKILL_CONTAINER, ISkillContainer.create(true, spellDataRegistryHolders)));
    }
}
