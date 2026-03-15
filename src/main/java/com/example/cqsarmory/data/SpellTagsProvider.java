package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.Tags;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class SpellTagsProvider extends TagsProvider<AbstractSpell> {
    protected SpellTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, SpellRegistry.SPELL_REGISTRY_KEY, lookupProvider, CqsArmory.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.AbstractSpells.SUMMONING_SPELLS).add(spell(SpellRegistry.RAISE_DEAD_SPELL));
        tag(Tags.AbstractSpells.SUMMONING_SPELLS).add(spell(SpellRegistry.SUMMON_HORSE_SPELL));
        tag(Tags.AbstractSpells.SUMMONING_SPELLS).add(spell(SpellRegistry.SUMMON_VEX_SPELL));
        tag(Tags.AbstractSpells.SUMMONING_SPELLS).add(spell(SpellRegistry.SUMMON_ENDER_CHEST_SPELL));
        tag(Tags.AbstractSpells.SUMMONING_SPELLS).add(spell(SpellRegistry.SUMMON_POLAR_BEAR_SPELL));
        tag(Tags.AbstractSpells.SUMMONING_SPELLS).add(spell(SpellRegistry.SUMMON_SWORDS));
    }

    private ResourceKey<AbstractSpell> spell (Supplier<AbstractSpell> spell) {
        return ResourceKey.create(SpellRegistry.SPELL_REGISTRY_KEY, spell.get().getSpellResource());
    }
}
