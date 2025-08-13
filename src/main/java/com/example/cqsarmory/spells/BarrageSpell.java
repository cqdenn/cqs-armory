package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
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
public class BarrageSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "barrage_spell");

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

    public BarrageSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 20;
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
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel * 2;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", 20),
                Component.literal("15 Projectiles"),
                Component.translatable("ui.irons_spellbooks.recast_count", getRecastCount(spellLevel, caster))
        );
    }

    @Override
    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        double entityCastTimeModifier = 1;
        if (entity != null) {
            entityCastTimeModifier = 2 - Utils.softCapFormula(entity.getAttributeValue(BowAttributes.DRAW_SPEED));
        }
        return Math.round(this.getCastTime(spellLevel) * (float) entityCastTimeModifier);
    }

    @Override
    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity), 80, castSource, null), playerMagicData);
        }
        int arrowCount = 15; //tbd fixme
        for (int i=0;i<arrowCount;i++) {
            Vec3 origin = entity.getEyePosition().add(entity.getForward().normalize().scale(.2f));
            AbilityArrow arrow = new AbilityArrow(world);
            arrow.setOwner(entity);
            arrow.setNoGravity(false);
            arrow.setScale(1f);
            arrow.setPos(origin.subtract(0, arrow.getBbHeight() * 0.5f, 0));
            shootFromRandom(entity.getLookAngle(), .1f, arrow);
            Vec3 vec3 = arrow.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            arrow.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
            arrow.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / (float)Math.PI));
            arrow.yRotO = arrow.getYRot();
            arrow.xRotO = arrow.getXRot();
            arrow.setBaseDamage(entity.getAttributeValue(BowAttributes.ARROW_DAMAGE) * 0.2f);
            arrow.setPierceLevel((byte) 0);
            arrow.setCritArrow(true);
            world.playSound(null, origin.x, origin.y, origin.z, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0f, 1.0f);
            world.addFreshEntity(arrow);
        }

        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }

    public void shootFromRandom(Vec3 rotation, float inaccuracy, AbilityArrow arrow) {
        var speed = rotation.length();
        Vec3 offset = Utils.getRandomVec3(1).normalize().scale(inaccuracy);
        var motion = rotation.normalize().add(offset).normalize().scale(speed);
        arrow.setDeltaMovement(motion.scale(4));
    }
}
