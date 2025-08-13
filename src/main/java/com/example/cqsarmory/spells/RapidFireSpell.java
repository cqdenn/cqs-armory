package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.fireball.SmallMagicFireball;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class RapidFireSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "rapid_fire_spell");

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.ARCHER_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(20)
            .build();

    public RapidFireSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 40;
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
        return CastType.CONTINUOUS;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.BOW_CHARGE_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.none();
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundRegistry.ARROW_VOLLEY_PREPARE.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundEvents.ARROW_SHOOT);
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", 100),
                Component.translatable("ui.cqs_armory.arrows_per_second", Utils.stringTruncation(20d / Math.max(5 - spellLevel, 1), 1))
        );
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public void onServerCastTick(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        int speed = Math.max(5 - spellLevel, 1); //4, 3, 2, 1 == 5, 6.6, 10, 20 times per second
        if (playerMagicData != null && (playerMagicData.getCastDurationRemaining() + 1) % speed == 0) {
            Vec3 origin = entity.getEyePosition().add(entity.getForward().normalize().scale(.2f));
            AbilityArrow arrow = new AbilityArrow(level);
            arrow.setOwner(entity);
            arrow.setBaseDamage(entity.getAttributeValue(BowAttributes.ARROW_DAMAGE));
            arrow.setNoGravity(false);
            arrow.setScale(1f);
            arrow.setPos(entity.position().add(0, entity.getEyeHeight() - arrow.getBoundingBox().getYsize() * .5f, 0).add(entity.getForward()));
            arrow.setDeltaMovement(entity.getForward().scale(4));
            Vec3 vec3 = arrow.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            arrow.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
            arrow.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / (float)Math.PI));
            arrow.yRotO = arrow.getYRot();
            arrow.xRotO = arrow.getXRot();
            arrow.setPierceLevel((byte) 0);
            arrow.setCritArrow(true);
            level.playSound(null, origin.x, origin.y, origin.z, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0f, 1.0f);
            level.addFreshEntity(arrow);
        }
    }
}

