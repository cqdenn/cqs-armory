package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.DamageTypes;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@AutoSpellConfig
public class WindBurstSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "wind_burst_spell");

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

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
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.literal("Burst into the air")
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
        float kb = 1.15f + (spellLevel * 0.35f);

        ExplosionDamageCalculator explosionDamageCalc = new SimpleExplosionDamageCalculator(
                true, false, Optional.of(kb), BuiltInRegistries.BLOCK.getTag(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
        );

        Vec3 vec3 = entity.position().add(0, 0.1, 0);
        level.explode(
                null, //source
                CQSpellRegistry.WIND_BURST_SPELL.get().getDamageSource(entity), //damage source
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
        DamageData.get(entity).cancelNextFall = true;

    }
}