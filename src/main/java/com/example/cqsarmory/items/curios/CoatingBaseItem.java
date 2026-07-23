package com.example.cqsarmory.items.curios;

import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import io.redspace.skillcasting.data.skill.ISkillContainer;
import io.redspace.skillcasting.data.skill.SkillData;

public class CoatingBaseItem extends CurioBaseItem  {


    public CoatingBaseItem(Properties properties, SkillData... spellDataRegistryHolders) {
        super(properties.component(ComponentRegistry.IMBUED_SPELL_CONTAINER, ISkillContainer.create(true, spellDataRegistryHolders)));
    }
}
