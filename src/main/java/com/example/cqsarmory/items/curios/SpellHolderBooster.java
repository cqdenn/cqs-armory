package com.example.cqsarmory.items.curios;

import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import io.redspace.skillcasting.data.skill.ISkillContainer;
import io.redspace.skillcasting.data.skill.SkillData;

public class SpellHolderBooster extends BoosterBaseItem {

    public SpellHolderBooster(Properties properties, SkillData... spellDataRegistryHolders) {
        super(properties.component(ComponentRegistry.IMBUED_SPELL_CONTAINER, ISkillContainer.create(true, spellDataRegistryHolders)));
    }
}
