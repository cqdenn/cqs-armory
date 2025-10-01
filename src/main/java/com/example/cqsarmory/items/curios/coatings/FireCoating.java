package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.OnHitCoating;
import com.example.cqsarmory.items.curios.OnSwingCoating;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.particle.FlameStrikeParticleOptions;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FireCoating extends OnSwingCoating {
    public FireCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnSwingEffect(Player attacker, float hitDamage) {
        Level level = attacker.level();
        float radius = 3.25f;
        float distance = 1.9f;
        MagicData playerMagicData = MagicData.getPlayerMagicData(attacker);
        double damage = (hitDamage * 0.25) * attacker.getAttributeValue(AttributeRegistry.FIRE_SPELL_POWER);
        Vec3 forward = attacker.getForward();
        Vec3 hitLocation = attacker.position().add(0, attacker.getBbHeight() * .3f, 0).add(forward.scale(distance));
        var entities = level.getEntities(attacker, AABB.ofSize(hitLocation, radius * 2, radius, radius * 2));
        var damageSource = new DamageSource(level.damageSources().damageTypes.getHolder(ISSDamageTypes.FIRE_MAGIC).get(), null, attacker);
        for (Entity targetEntity : entities) {
            if (targetEntity instanceof LivingEntity && targetEntity.isAlive() && attacker.isPickable() && targetEntity.position().subtract(attacker.getEyePosition()).dot(forward) >= 0 && attacker.distanceToSqr(targetEntity) < radius * radius && Utils.hasLineOfSight(level, attacker.getEyePosition(), targetEntity.getBoundingBox().getCenter(), true)) {
                Vec3 offsetVector = targetEntity.getBoundingBox().getCenter().subtract(attacker.getEyePosition());
                if (offsetVector.dot(forward) >= 0) {
                    if (DamageSources.applyDamage(targetEntity,(float) damage, damageSource)) {
                        MagicManager.spawnParticles(level, ParticleHelper.FIRE, targetEntity.getX(), targetEntity.getY() + targetEntity.getBbHeight() * .5f, targetEntity.getZ(), 30, targetEntity.getBbWidth() * .5f, targetEntity.getBbHeight() * .5f, targetEntity.getBbWidth() * .5f, .03, false);
                        EnchantmentHelper.doPostAttackEffects((ServerLevel) level, targetEntity, damageSource);
                    }
                }
            }
        }
        boolean mirrored = playerMagicData.getCastingEquipmentSlot().equals(SpellSelectionManager.MAINHAND);
        MagicManager.spawnParticles(level, new FlameStrikeParticleOptions((float) forward.x, (float) forward.y, (float) forward.z, mirrored, false, 1f), hitLocation.x, hitLocation.y+.3, hitLocation.z, 1, 0, 0, 0, 0, true);
    }
}
