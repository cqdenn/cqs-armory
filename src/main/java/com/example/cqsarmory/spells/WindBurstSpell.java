package com.example.cqsarmory.spells;

import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.SoundRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.PlayableSound;
import io.redspace.skillcasting.data.cast.CastType;
import io.redspace.skillcasting.data.recast.RecastConfig;
import io.redspace.skillcasting.registry.SkillcastingComponentTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class WindBurstSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(20)
            .build();

    public WindBurstSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 0;
    }

    @Override
    public boolean allowCrafting() {
        return false;
    }

    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ONE_HANDED_VERTICAL_UPSWING_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public Optional<PlayableSound> getOnCastSound(CastContext castContext) {
        return Optional.of(PlayableSound.standard(SoundRegistry.MACE_CAST_SOUND.get()));
    }

    @Override
    public Optional<RecastConfig> provideRecastConfig(CastContext castContext) {
        return Optional.of(new RecastConfig(castContext.getSkillLevel(), 100));
    }

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.literal("Burst into the air"),
                Component.translatable("ui.irons_spellbooks.recast_count", castContext.find(SkillcastingComponentTypes.RECAST_CONFIG).map(RecastConfig::totalCasts).orElse(0))
        );
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        Entity entity = castContext.asEntityCaster();
        float kb = 1.5f;

        ExplosionDamageCalculator explosionDamageCalc = new SimpleExplosionDamageCalculator(
                true, false, Optional.of(kb), BuiltInRegistries.BLOCK.getTag(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
        );

        Vec3 vec3 = entity.position().add(0, 0.1, 0);
        level.explode(
                null, //source
                CQSpellRegistry.WIND_BURST_SPELL.get().getDamageSource(entity.level(), null, entity), //damage source
                explosionDamageCalc, //dmg calc
                vec3.x(), //location x, y, z
                vec3.y(),
                vec3.z(),
                3.5f, //radius
                false, // fire
                Level.ExplosionInteraction.TRIGGER, //explosion interaction
                ParticleTypes.GUST_EMITTER_SMALL, //small particle
                ParticleTypes.GUST_EMITTER_LARGE, //big particle
                SoundEvents.WIND_CHARGE_BURST //sound
        );
        //DamageData.get(entity).cancelNextFall = level.getGameTime();

    }
}