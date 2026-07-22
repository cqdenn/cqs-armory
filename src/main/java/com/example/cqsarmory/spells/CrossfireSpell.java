package com.example.cqsarmory.spells;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.HitscanArcaneBeam;
import com.example.cqsarmory.items.curios.QuiverItem;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.CQSkillComponents;
import com.example.cqsarmory.utils.CQRaycaster;
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
import io.redspace.skillcasting.data.recast.RecastConfig;
import io.redspace.skillcasting.registry.SkillcastingComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class CrossfireSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.ARCHER_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(40)
            .build();

    public CrossfireSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 100;
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
        return Optional.of(new RecastConfig(2, 100));
    }

    public float getWeaponDamagePercent() {
        return 0.2f;
    }

    public final float MAX_SPREAD = 1.5f;
    public final float MIN_SPREAD = 0.5f;

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", getWeaponDamagePercent() * 100),
                Component.literal(castContext.getOrDefault(SkillcastingComponentTypes.PROJECTILE_COUNT, 0) + " Projectiles"),
                Component.literal("Cast to charge, recast to fire"),
                Component.literal("Reduce spread the longer you charge")
        );
    }

    @Override
    public void buildContextComponents(CastContext castContext) {
        super.buildContextComponents(castContext);
        castContext.set(SkillcastingComponentTypes.PROJECTILE_COUNT, 20);
        castContext.set(SkillcastingComponentTypes.CAST_TIME, castContext.getOrDefault(SkillcastingComponentTypes.PROJECTILE_COUNT, 0) / 2 * continuousInterval());
        castContext.set(CQSkillComponents.PROJECTILE_TRACKER, 0);
        castContext.set(CQSkillComponents.PROJECTILE_SPREAD, MAX_SPREAD);
        if (castContext.asEntityCaster() instanceof LivingEntity living) {
            castContext.set(SkillcastingComponentTypes.PROJECTILE_PIERCE, (int) living.getAttributeValue(AttributeRegistry.ARROW_PIERCING));
            castContext.set(SkillcastingComponentTypes.DAMAGE, (float) living.getAttributeValue(BowAttributes.ARROW_DAMAGE) * getWeaponDamagePercent());
        }
    }

    @Override
    public int continuousInterval() {
        return 2;
    }

    public void placeX (CastContext castContext, AbilityArrow arrow, boolean flip) {
        //place with offset of spread, move down and right with projectile count tracking, or down and left with flip == true FIXME
        int count = castContext.getOrDefault(SkillcastingComponentTypes.PROJECTILE_COUNT, 0);
        count = count % 2 == 0 ? count : count - 1; //this leaves out 1 arrow on odd numbers, but keeps the corresponding arrows at equal positions
        float spread = castContext.getOrDefault(CQSkillComponents.PROJECTILE_SPREAD, 0f);
        float delta = (float) count / (float) castContext.getOrDefault(SkillcastingComponentTypes.PROJECTILE_COUNT, 0);
        float lerpY = Mth.lerp(delta, spread, -spread); //arrows start at top and go down
        float lerpXZ = Mth.lerp(delta, spread, -spread); //arrows start at top and go down
        if (flip) lerpXZ *= -1;
        arrow.setPos(arrow.getPosition(0).add(0, lerpY, 0));//figure out vector math for XZ placement FIXME
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        //do nothing but save spread (1.5 -> 0.5?) on first cast, then logic below on second cast
        if (castContext.getRecastsRemaining() >= 1 && castContext.asEntityCaster() != null) {
            AbilityData.get(castContext.asEntityCaster()).crossfireChargeTime += continuousInterval();
            return;
        }
        int arrowCount = castContext.getOrDefault(SkillcastingComponentTypes.PROJECTILE_COUNT, 0); // add extra projectile if odd? FIXME
        if (!(castContext.asEntityCaster() instanceof LivingEntity entity)) return;
        castContext.set(CQSkillComponents.PROJECTILE_SPREAD, Mth.lerp(AbilityData.get(castContext.asEntityCaster()).crossfireChargeTime, MIN_SPREAD, MAX_SPREAD));
        for (int i = 0; i < 2; i++) {
            Vec3 origin = entity.getEyePosition().add(entity.getForward().normalize().scale(.2f));
            float dmg = castContext.getOrDefault(SkillcastingComponentTypes.DAMAGE, 0f);
            AbilityArrow projectile = new AbilityArrow(level);

            ItemStack weaponItem = castContext.getCastSource().isFromSlot(EquipmentSlot.OFFHAND) ? entity.getOffhandItem() : entity.getMainHandItem();
            Holder.Reference<Enchantment> flameHolder = entity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FLAME);
            if (weaponItem.getEnchantmentLevel(flameHolder) > 0) {
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
            Vec3 pos = origin.subtract(0, projectile.getBbHeight() * 0.5f, 0);
            projectile.setPos(pos);
            projectile.setDeltaMovement(entity.getForward());
            placeX(castContext, projectile, i==1);
            if (projectile instanceof HitscanArcaneBeam beam) {
                beam.raycast = CQRaycaster.begin(entity.level(), entity)
                        .rangeFromStart(64, pos, beam.getDeltaMovement())//if changes in arcane quiver, update here
                        .checkForBlocks(true)
                        .bbInflation(.15f)
                        .buildList();
                int pierce = 0;
                pierce += beam.getPierceLevel();
                pierce += castContext.getOrDefault(SkillcastingComponentTypes.PROJECTILE_PIERCE, 0);
                Vec3 end = beam.raycast.get(Math.min(pierce, beam.raycast.size() - 1)).getLocation();
                beam.distance = (float) pos.distanceTo(end);
            }
            Vec3 vec3 = projectile.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            projectile.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * 180.0F / (float) Math.PI));
            projectile.setXRot((float) (Mth.atan2(vec3.y, d0) * 180.0F / (float) Math.PI));
            projectile.yRotO = projectile.getYRot();
            projectile.xRotO = projectile.getXRot();
            //projectile.setBaseDamage(dmg);
            projectile.setCritArrow(true);
            //projectile.setShotFromAbility(true); too op
            projectile.setWeaponItem(weaponItem);


            //world.playSound(null, origin.x, origin.y, origin.z, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0f, 1.0f);
            level.addFreshEntity(projectile);
            castContext.set(CQSkillComponents.PROJECTILE_TRACKER, castContext.getOrDefault(CQSkillComponents.PROJECTILE_TRACKER, 0) + 1);
        }
        if (entity instanceof Player player && !CQtils.getPlayerCurioStack(player, "quiver").isEmpty()) {
            ((QuiverItem) CQtils.getPlayerCurioStack(player, "quiver").getItem()).playCustomBowShootSound(level, player, entity.getX(), entity.getY(), entity.getZ());
        }
    }
}
