package com.example.cqsarmory.items;

import com.example.cqsarmory.api.CQSpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.item.weapons.ExtendedSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import java.util.Arrays;
import java.util.List;

public class MagicStaffItem extends ExtendedSwordItem implements IPresetSpellContainer {

    List<SpellData> spellData = null;
    CQSpellDataRegistryHolder[] spellDataRegistryHolders;

    public MagicStaffItem(Tier pTier, Properties pProperties, CQSpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(pTier, pProperties);
        this.spellDataRegistryHolders = spellDataRegistryHolders;

    }

    public List<SpellData> getSpells() {
        if (spellData == null) {
            spellData = Arrays.stream(spellDataRegistryHolders).map(CQSpellDataRegistryHolder::getSpellData).toList();
            spellDataRegistryHolders = null;
        }
        return spellData;
    }

    @Override
    public void initializeSpellContainer(ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        if (!ISpellContainer.isSpellContainer(itemStack)) {
            var spells = getSpells();
            var spellContainer = ISpellContainer.create(spells.size(), true, false).mutableCopy();
            spells.forEach(spellData -> spellContainer.addSpell(spellData.getSpell(), spellData.getLevel(), true));
            itemStack.set(ComponentRegistry.SPELL_CONTAINER, spellContainer.toImmutable());
        }
    }
}
