package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.data.effects.CQMobEffectInstance;
import com.example.cqsarmory.items.curios.OnHitCoating;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class BleedCoating extends OnHitCoating implements IPresetSpellContainer {
    public BleedCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    List<SpellData> spellData = null;
    SpellDataRegistryHolder[] spellDataRegistryHolders;

    public BleedCoating(Properties properties, String slot, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, slot);
        this.spellDataRegistryHolders = spellDataRegistryHolders;
    }

    public List<SpellData> getSpells() {
        if (spellData == null) {
            spellData = Arrays.stream(spellDataRegistryHolders).map(SpellDataRegistryHolder::getSpellData).toList();
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
            var spellContainer = ISpellContainer.create(spells.size(), true, true).mutableCopy();
            spells.forEach(spellData -> spellContainer.addSpell(spellData.getSpell(), spellData.getLevel(), true));
            itemStack.set(ComponentRegistry.SPELL_CONTAINER, spellContainer.toImmutable());
        }
    }

    @Override
    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
        /*if (target.getEffect(MobEffectRegistry.BLEED) instanceof CQMobEffectInstance effectInstance) {
            int amplifier = effectInstance.getAmplifier();
            if (amplifier < 4) {
                target.addEffect(new CQMobEffectInstance(MobEffectRegistry.BLEED, 100, amplifier + 1, false, false, true, attacker));
            } else {
                target.addEffect(new CQMobEffectInstance(MobEffectRegistry.BLEED, 100, 4, false, false, true, attacker));
            }
        } else {
            target.addEffect(new CQMobEffectInstance(MobEffectRegistry.BLEED, 100, 0, false, false, true, attacker));
        }*/
        target.addEffect(new CQMobEffectInstance(MobEffectRegistry.BLEED, 40, 0, false, false, true, attacker, true));
    }
}
