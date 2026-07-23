package com.example.cqsarmory.spells;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.items.curios.QuiverItem;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.bowattributes.registry.BowAttributes;
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
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class PiercingArrowSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.ARCHER_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(20)
            .build();

    public PiercingArrowSpell() {
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
        return AnimationHolder.stop();
    }

    @Override
    public Optional<PlayableSound> getCastStartSound(CastContext castContext) {
        return Optional.of(PlayableSound.standard(SoundRegistry.ARROW_VOLLEY_PREPARE.get()));
    }

    @Override
    public Optional<PlayableSound> getOnCastSound(CastContext castContext) {
        return Optional.empty();
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", getWeaponDamagePercent(castContext.getSkillLevel()) * 100),
                Component.literal((getBonusDmgPerMomentumPercent() * 100) + "% Bonus Damage per Momentum"),
                Component.literal("Pierces 10 Times")
        );
    }

    @Override
    public void buildContextComponents(CastContext castContext) {
        super.buildContextComponents(castContext);
        castContext.set(SkillcastingComponentTypes.CAST_TIME, CQtils.getEffectiveBowCastTime(castContext));
    }

    public float getWeaponDamagePercent(int spellLevel) {
        return 1 + (0.5f * spellLevel);
    }

    public float getBonusDmgPerMomentumPercent() {
        return 0.025f;
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        if (!(castContext.asEntityCaster() instanceof LivingEntity entity)) return;
        float dmg = (float) entity.getAttributeValue(BowAttributes.ARROW_DAMAGE) * getWeaponDamagePercent(castContext.getSkillLevel());
        dmg *= 1 + (AbilityData.get(entity).getMomentum() * getBonusDmgPerMomentumPercent());
        float scale = 6f;
        AbilityArrow projectile = new AbilityArrow(level);
        ItemStack wepaonItem = castContext.getCastSource().isFromSlot(EquipmentSlot.OFFHAND) ? entity.getOffhandItem() : entity.getMainHandItem();
        Holder.Reference<Enchantment> flameHolder = entity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FLAME);
        if (wepaonItem.getEnchantmentLevel(flameHolder) > 0) {
            projectile.igniteForSeconds(100);
        }
        if (entity instanceof Player player && !CQtils.getPlayerCurioStack(player, "quiver").isEmpty()) {
            if (CQtils.getPlayerCurioStack(player, "quiver").getItem() instanceof QuiverItem quiver) {
                projectile = quiver.getCustomProjectile(projectile, player, dmg);
            }
        } else {
            projectile.setBaseDamage(dmg);
        }
        projectile.setOwner(entity);
        //projectile.setBaseDamage(dmg);
        projectile.setNoGravity(true);
        projectile.setScale(scale);
        projectile.setPos(entity.position().add(0, entity.getEyeHeight() - projectile.getBoundingBox().getYsize() * .5f, 0).add(entity.getForward()));
        projectile.setDeltaMovement(projectile.getMovementToShoot(entity.getForward().x, entity.getForward().y, entity.getForward().z, 3f, 0.05f));
        Vec3 vec3 = projectile.getDeltaMovement();
        double d0 = vec3.horizontalDistance();
        projectile.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
        projectile.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / (float)Math.PI));
        projectile.yRotO = projectile.getYRot();
        projectile.xRotO = projectile.getXRot();
        projectile.setPierceLevel((byte) 10);
        projectile.setCritArrow(true);
        projectile.setShotFromAbility(true);
        projectile.setWeaponItem(wepaonItem);


        level.addFreshEntity(projectile);
        if (entity instanceof Player player && !CQtils.getPlayerCurioStack(player, "quiver").isEmpty()) {
            ((QuiverItem) CQtils.getPlayerCurioStack(player, "quiver").getItem()).playCustomBowShootSound(level, player, entity.getX(), entity.getY(), entity.getZ());
        }
    }
}
