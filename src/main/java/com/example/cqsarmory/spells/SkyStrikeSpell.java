package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.api.AbilityAnimations;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.ice_block.IceBlockProjectile;
import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@AutoSpellConfig
public class SkyStrikeSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "sky_strike_spell");

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(20)
            .build();

    public SkyStrikeSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 5;
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
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return 2;
    }

  /*  @Override
    public void onClientPreCast(Level level, int spellLevel, LivingEntity entity, InteractionHand hand, @Nullable MagicData playerMagicData) {
        super.onClientPreCast(level, spellLevel, entity, hand, playerMagicData);
        if (MagicData.getPlayerMagicData(entity).getPlayerRecasts() != null) {
            if (entity instanceof Player player && MagicData.getPlayerMagicData(entity).getPlayerRecasts().getRemainingRecastsForSpell(this) == 1) {
                ClientSpellCastHelper.animatePlayerStart(player, AbilityAnimations.UPPERCUT_ANIMATION.getForPlayer().get());
            }
        }
    }*/

    @Override
    public AnimationHolder getCastStartAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.empty();
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
        int radius = 4;
        Vec3 direction = new Vec3(entity.getForward().x, 1, entity.getForward().z);
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity), 40, castSource, null), playerMagicData);

            var entities = level.getEntities(entity, entity.getBoundingBox().inflate(radius));
            var damageSource = CQSpellRegistry.SKY_STRIKE_SPELL.get().getDamageSource(entity);
            for (Entity target : entities) {
                if (target instanceof LivingEntity && (Utils.checkEntityIntersecting(target, entity.getEyePosition(), entity.getEyePosition().add(entity.getForward().scale(radius)), 1f).getType() != HitResult.Type.MISS)) {
                    target.hurt(damageSource, (float) entity.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                    //((LivingEntity) target).addEffect(new MobEffectInstance(MobEffectRegistry.BLEED, 100 * spellLevel, spellLevel, false, false, true));
                    target.push(direction.multiply(0.5, 1.3, 0.5));
                    target.hurtMarked = true;
                }
            }
            entity.push(direction.multiply(0.5, 2, 0.5));
            entity.hurtMarked = true;
        } else {
            entity.setDeltaMovement(direction.multiply(1, -1, 1));
            entity.hurtMarked = true;
            entity.addEffect(new MobEffectInstance(MobEffectRegistry.SKY_STRIKE, 40, 0, false, false, false));
        }


    }
}
