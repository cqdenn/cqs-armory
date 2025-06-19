package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.api.AbilityAnimations;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.ice_block.IceBlockProjectile;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
@AutoSpellConfig
public class IceSmashSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "ice_smash_spell");

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(20)
            .build();

    public IceSmashSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 16;
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
        return CastType.LONG;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundRegistry.DIVINE_SMITE_WINDUP.get());
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.OVERHEAD_MELEE_SWING_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
        var entities = level.getEntities(entity, entity.getBoundingBox().inflate(5));

        for (Entity target : entities) {
            if (target instanceof LivingEntity livingEntity) {
                if (!DamageSources.isFriendlyFireBetween(entity, target) && !entity.isSpectator()) {
                    Vec3 spawn = livingEntity.position();
                    IceBlockProjectile iceBlock = new IceBlockProjectile(level, entity, livingEntity);
                    iceBlock.moveTo(raiseWithCollision(spawn, 4, level));
                    if (!level.noBlockCollision(iceBlock, iceBlock.getBoundingBox())) {
                        iceBlock.noPhysics = true;
                    }
                    iceBlock.setAirTime(25);
                    iceBlock.setDamage(40);
                    level.addFreshEntity(iceBlock);
                }
            }
        }

    }

    private Vec3 raiseWithCollision(Vec3 start, int blocks, Level level) {
        for (int i = 0; i < blocks; i++) {
            Vec3 raised = start.add(0, 1, 0);
            if (level.getBlockState(BlockPos.containing(raised)).isAir())
                start = raised;
            else
                break;
        }
        return start;
    }
}
