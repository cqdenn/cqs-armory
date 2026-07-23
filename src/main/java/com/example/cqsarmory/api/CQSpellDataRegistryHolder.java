package com.example.cqsarmory.api;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.skillcasting.data.skill.SkillData;

import java.util.function.Supplier;

public class CQSpellDataRegistryHolder {

    Supplier<AbstractSpell> registrySpell;
    int spellLevel;

    public CQSpellDataRegistryHolder(Supplier<AbstractSpell> registrySpell, int spellLevel) {
        this.registrySpell = registrySpell;
        this.spellLevel = spellLevel;
    }

    public Supplier<AbstractSpell> getSpellSupplier() {return this.registrySpell;}

    public int getLevel() {return this.spellLevel;}

    public SkillData getSpellData() {
        return new SkillData(registrySpell.get(), spellLevel);
    }

    public static CQSpellDataRegistryHolder[] of(CQSpellDataRegistryHolder... args) {
        return args;
    }
}
