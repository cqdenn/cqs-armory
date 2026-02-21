package com.example.cqsarmory.spells;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.api.AbilityAnimations;
import com.example.cqsarmory.data.entity.ability.ScytheProjectile;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.MobEffectRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoSpellConfig
public class ChainWhipSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "chain_whip_spell");

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

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
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cqs_armory.weapon_damage", 50),
                Component.translatable("ui.cqs_armory.chained_duration", getDuration(spellLevel, caster)/20 + "s")
        );
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
        if (playerMagicData != null) {
            ItemStack item = playerMagicData.getCastingEquipmentSlot().equals(SpellSelectionManager.OFFHAND) ? entity.getOffhandItem() : entity.getMainHandItem();
            ScytheProjectile scythe = new ScytheProjectile(level, item, 0, 1, spellLevel);
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



    public static float getRange(int level, LivingEntity caster) {
        return 5 * level;
    }

    public static int getDuration(int level, LivingEntity caster) {
        return 60 * level;
    }

    public static float getDamage(LivingEntity caster) {
        return (float) caster.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f;
    }
}
