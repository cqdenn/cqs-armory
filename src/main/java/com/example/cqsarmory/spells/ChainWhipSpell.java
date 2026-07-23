package com.example.cqsarmory.spells;

import com.example.cqsarmory.data.entity.ability.ScytheProjectile;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.PlayableSound;
import io.redspace.skillcasting.data.cast.CastType;
import io.redspace.skillcasting.registry.SkillcastingComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class ChainWhipSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(30)
            .build();

    public ChainWhipSpell() {
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
    public Optional<PlayableSound> getOnCastSound(CastContext castContext) {
        return Optional.of(PlayableSound.standard(SoundRegistry.THROW_DAGGER.get()));
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.SLASH_ANIMATION;
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", 50),
                Component.translatable("ui.cqs_armory.chained_duration", getDuration(castContext)/20 + "s")
        );
    }

    @Override
    public void buildContextComponents(CastContext castContext) {
        super.buildContextComponents(castContext);
        castContext.set(SkillcastingComponentTypes.EFFECT_DURATION_TICKS, 60 * castContext.getSkillLevel());
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        if (castContext.asEntityCaster() instanceof LivingEntity entity) {
            ItemStack item = castContext.getCastSource().isFromSlot(EquipmentSlot.OFFHAND) ? entity.getOffhandItem() : entity.getMainHandItem();;
            ScytheProjectile scythe = new ScytheProjectile(level, item, 0, 1, getDuration(castContext));
            scythe.setBaseDamage(entity.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5);
            scythe.setOwner(entity);
            scythe.setNoGravity(false);
            scythe.setScale(1f);
            scythe.setPos(entity.position().add(0, entity.getEyeHeight() - scythe.getBoundingBox().getYsize() * .5f, 0).add(entity.getForward()));
            scythe.setDeltaMovement(scythe.getMovementToShoot(entity.getForward().x, entity.getForward().y, entity.getForward().z, (float) scythe.getSpeed(), 0.00f));
            Vec3 vec3 = scythe.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            scythe.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
            scythe.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / (float)Math.PI));
            scythe.yRotO = scythe.getYRot();
            scythe.xRotO = scythe.getXRot();
            scythe.setPierceLevel((byte) 0);
            scythe.setCritArrow(false);
            scythe.setShotFromAbility(true);
            scythe.setWeaponItem(item);
            level.addFreshEntity(scythe);
        }
    }

    public static int getDuration(CastContext castContext) {
        return castContext.getOrDefault(SkillcastingComponentTypes.EFFECT_DURATION_TICKS, 0);
    }

    public static float getDamage(LivingEntity caster) {
        return (float) caster.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f;
    }
}
