package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.Tags;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.skillcasting.data.AbstractSkill;
import io.redspace.skillcasting.registry.SkillcastingRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SpellTagsProvider extends TagsProvider<AbstractSkill> {
    protected SpellTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, SkillcastingRegistries.SKILL_REGISTRY_KEY, lookupProvider, CqsArmory.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.AbstractSkills.SUMMONING_SPELLS).add(spell(SpellRegistry.RAISE_DEAD_SPELL));
        tag(Tags.AbstractSkills.SUMMONING_SPELLS).add(spell(SpellRegistry.SUMMON_HORSE_SPELL));
        tag(Tags.AbstractSkills.SUMMONING_SPELLS).add(spell(SpellRegistry.SUMMON_VEX_SPELL));
        tag(Tags.AbstractSkills.SUMMONING_SPELLS).add(spell(SpellRegistry.SUMMON_ENDER_CHEST_SPELL));
        tag(Tags.AbstractSkills.SUMMONING_SPELLS).add(spell(SpellRegistry.SUMMON_POLAR_BEAR_SPELL));
        tag(Tags.AbstractSkills.SUMMONING_SPELLS).add(spell(SpellRegistry.SUMMON_SWORDS_SPELL));
    }

    private ResourceKey<AbstractSkill> spell (Holder<AbstractSkill> spell) {
        return ResourceKey.create(SkillcastingRegistries.SKILL_REGISTRY_KEY, spell.value().getSkillId());
    }
}
