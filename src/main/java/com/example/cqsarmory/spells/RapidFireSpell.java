package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.items.curios.QuiverItem;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.utils.CQtils;
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
import net.minecraft.world.entity.projectile.Projectile;
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
            float dmg = (float) entity.getAttributeValue(BowAttributes.ARROW_DAMAGE);
            AbilityArrow projectile = new AbilityArrow(level);
            if (entity instanceof Player player && !CQtils.getPlayerCurioStack(player, "quiver").isEmpty()) {
                if (CQtils.getPlayerCurioStack(player, "quiver").getItem() instanceof QuiverItem quiver) {
                    projectile = quiver.getCustomProjectile(projectile, player, dmg);
                }
            } else {
                projectile.setBaseDamage(dmg);
            }
            projectile.setOwner(entity);
            projectile.setNoGravity(false);
            projectile.setScale(1f);
            projectile.setPos(entity.position().add(0, entity.getEyeHeight() - projectile.getBoundingBox().getYsize() * .5f, 0).add(entity.getForward()));
            projectile.setDeltaMovement(projectile.getMovementToShoot(entity.getForward().x, entity.getForward().y, entity.getForward().z, 3f, 0.05f));
            Vec3 vec3 = projectile.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            projectile.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
            projectile.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / (float)Math.PI));
            projectile.yRotO = projectile.getYRot();
            projectile.xRotO = projectile.getXRot();
            projectile.setPierceLevel((byte) 0);
            projectile.setCritArrow(true);
            projectile.setShotFromAbility(true);
            projectile.setWeaponItem(playerMagicData.getPlayerCastingItem());

            level.playSound(null, origin.x, origin.y, origin.z, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0f, 1.0f);
            level.addFreshEntity(projectile);
        }
    }
}

