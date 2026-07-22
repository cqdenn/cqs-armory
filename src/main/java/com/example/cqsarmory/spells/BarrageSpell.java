package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.HitscanArcaneBeam;
import com.example.cqsarmory.data.entity.renderers.HitscanArcaneBeamRenderer;
import com.example.cqsarmory.items.curios.QuiverItem;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.utils.CQRaycaster;
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
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.PlayableSound;
import io.redspace.skillcasting.data.cast.CastSource;
import io.redspace.skillcasting.data.cast.CastType;
import io.redspace.skillcasting.data.recast.RecastConfig;
import io.redspace.skillcasting.registry.SkillcastingComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class BarrageSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.ARCHER_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(40)
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
        return AnimationHolder.stop();
    }

    @Override
    public Optional<PlayableSound> getCastStartSound(CastContext castContext) {
        return PlayableSound.standard(SoundRegistry.ARROW_VOLLEY_PREPARE.get()).toOpt();
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
    public Optional<RecastConfig> provideRecastConfig(CastContext castContext) {
        return Optional.of(new RecastConfig(castContext.getSkillLevel(), 80));
    }

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", 20),
                Component.literal(castContext.getOrDefault(SkillcastingComponentTypes.PROJECTILE_COUNT, 0) + " Projectiles"),
                Component.literal("Pushes you backwards"),
                Component.translatable("ui.irons_spellbooks.recast_count", castContext.find(SkillcastingComponentTypes.RECAST_CONFIG).map(RecastConfig::totalCasts).orElse(0))
        );
    }

    @Override
    public void buildContextComponents(CastContext castContext) {
        super.buildContextComponents(castContext);
        castContext.set(SkillcastingComponentTypes.CAST_TIME, CQtils.getEffectiveBowCastTime(castContext));
        castContext.set(SkillcastingComponentTypes.PROJECTILE_COUNT, 15);
    }

    @Override
    public void onCast(ServerLevel world, CastContext castContext) {
        int arrowCount = castContext.getOrDefault(SkillcastingComponentTypes.PROJECTILE_COUNT, 0);
        if (!(castContext.asEntityCaster() instanceof LivingEntity entity)) return;
        for (int i=0;i<arrowCount;i++) {
            Vec3 origin = entity.getEyePosition().add(entity.getForward().normalize().scale(.2f));
            float dmg = (float) entity.getAttributeValue(BowAttributes.ARROW_DAMAGE) * 0.2f;
            AbilityArrow projectile = new AbilityArrow(world);
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
            //projectile.setNoGravity(false);
            Vec3 pos = origin.subtract(0, projectile.getBbHeight() * 0.5f, 0);
            projectile.setPos(pos);
            shootFromRandom(projectile.getMovementToShoot(entity.getForward().x, entity.getForward().y, entity.getForward().z, 3f, 0.05f), .2f, projectile);
            if (projectile instanceof HitscanArcaneBeam beam) {
                beam.raycast = CQRaycaster.begin(entity.level(), entity)
                        .rangeFromStart(64, pos, beam.getDeltaMovement())//if changes in arcane quiver, update here
                        .checkForBlocks(true)
                        .bbInflation(.15f)
                        .buildList();
                int pierce = 0;
                pierce += beam.getPierceLevel();
                pierce += (int) entity.getAttributeValue(AttributeRegistry.ARROW_PIERCING);
                Vec3 end = beam.raycast.get(Math.min(pierce, beam.raycast.size() - 1)).getLocation();
                beam.distance = (float) pos.distanceTo(end);
            }
            Vec3 vec3 = projectile.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            projectile.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
            projectile.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / (float)Math.PI));
            projectile.yRotO = projectile.getYRot();
            projectile.xRotO = projectile.getXRot();
            //projectile.setBaseDamage(dmg);
            projectile.setCritArrow(true);
            //projectile.setShotFromAbility(true); too op
            projectile.setWeaponItem(wepaonItem);


            //world.playSound(null, origin.x, origin.y, origin.z, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0f, 1.0f);
            world.addFreshEntity(projectile);
        }
        if (entity instanceof Player player && !CQtils.getPlayerCurioStack(player, "quiver").isEmpty()) {
            ((QuiverItem) CQtils.getPlayerCurioStack(player, "quiver").getItem()).playCustomBowShootSound(world, player, entity.getX(), entity.getY(), entity.getZ());
        }

        entity.push(entity.getForward().scale(-1).multiply(1, 0, 1).add(0, Math.max(entity.getForward().scale(-1).y, 0.3), 0));
        entity.hurtMarked = true;
    }

    public void shootFromRandom(Vec3 rotation, float inaccuracy, AbilityArrow arrow) {
        var speed = rotation.length();
        Vec3 offset = Utils.getRandomVec3(1).normalize().scale(inaccuracy);
        var motion = rotation.normalize().add(offset).normalize().scale(speed);
        arrow.setDeltaMovement(motion);
    }
}
