package com.example.cqsarmory.items;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.joml.Random;
import org.joml.Vector3f;

import java.util.List;

public class MjolnirItem extends TridentItem {
    double speed;
    boolean doDamage;


    public MjolnirItem(Tier tier, Properties properties) {
        super(properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        if (!attacker.hasEffect(MobEffectRegistry.THUNDERSTORM)) {
            attacker.addEffect(new MobEffectInstance(MobEffectRegistry.THUNDERSTORM, 40, 20, false, false, false));
        }

    }


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        Random random = new Random();
        Vector3f center = new Vector3f(1, 1f, 1f);
        float radius = 8;
        Vector3f edge = new Vector3f(.7f, 1f, 1f);
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        if (entity.verticalCollisionBelow) {
            if (speed < 0) {
                //this is immaculately stupid
                level.addParticle(new BlastwaveParticleOptions(center, radius), x, y + .165f, z, 0, 0, 0);
                level.addParticle(new BlastwaveParticleOptions(center, radius), x, y + .135f, z, 0, 0, 0);
                level.addParticle(new BlastwaveParticleOptions(edge, radius * 1.02f), x, y + .135f, z, 0, 0, 0);
                level.addParticle(new BlastwaveParticleOptions(edge, radius * 0.98f), x, y + .135f, z, 0, 0, 0);
                for (int i = 0; i < 10; i++) {
                    float randDirection = random.nextFloat();
                    float direction;
                    if (randDirection <= 0.5f) {
                        direction = -1f;
                    } else {
                        direction = 1f;
                    }
                    level.addParticle(ParticleHelper.ELECTRICITY, x, y + 1f, z, random.nextFloat() * direction, random.nextFloat(), random.nextFloat() * direction);
                    level.addParticle(ParticleHelper.ELECTRICITY, x, y + 1f, z, random.nextFloat() * -direction, random.nextFloat(), random.nextFloat() * direction);
                    level.addParticle(ParticleHelper.ELECTRICITY, x, y + 1f, z, random.nextFloat() * direction, random.nextFloat(), random.nextFloat() * -direction);
                    doDamage = true;
                    speed = 0;
                }
            }
        }
        if (doDamage) {
            var entities = level.getEntities(entity, entity.getBoundingBox().inflate(radius, radius, radius), (target) -> !DamageSources.isFriendlyFireBetween(target, entity) && Utils.hasLineOfSight(level, entity, target, true));
            if (!level.isClientSide()) {
                slamDamage(entities, level, entity);
            }
            entities.clear();
        }
    }

    public void slamDamage (List<Entity> entities, Level level, Entity entity) {
        DamageSource genericDamage = level.damageSources().indirectMagic(entity, entity);
        float radius = 8;
        for (Entity target : entities) {
            if (target instanceof LivingEntity livingEntity && canHit(entity, target) && livingEntity.distanceToSqr(entity) < radius * radius) {
                target.hurt(genericDamage, 20);
            }
        }
        doDamage = false;
    }


    private boolean canHit(Entity owner, Entity target) {
        return target.isAlive() && target.isPickable() && !target.isSpectator();
    }

    public final SpellDamageSource getDamageSource(Entity attacker) {
        return getDamageSource(attacker, attacker);
    }

    public SpellDamageSource getDamageSource(Entity projectile, Entity attacker) {
        return SpellDamageSource.source(projectile, attacker, SpellRegistry.SHOCKWAVE_SPELL.get());
    }


    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return super.use(level, player, hand);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        super.releaseUsing(stack, level, entityLiving, timeLeft);
        if (this.getUseDuration(stack, entityLiving) - timeLeft >= 10) {
            entityLiving.push(entityLiving.getForward().scale(2));
            level.playSound(entityLiving, entityLiving.blockPosition(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.MASTER, 1, 1);
            speed = entityLiving.getDeltaMovement().y;
        }
    }
}
