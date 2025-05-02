package com.example.cqsarmory.items;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class VolcanoSwordItem extends SwordItem {

    boolean hurt;
    boolean chain;
    Vec3 pos_chain;
    Vec3 pos;
    List<Vec3> chain_particles;

    public VolcanoSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
        this.hurt = false;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        float radius = 8;
        if (!attacker.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) attacker.level();
            if (!target.isAlive()) {
                this.hurt = true;
                this.pos = target.position();
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (this.hurt) {
            if (entity.level().isClientSide) {
                particles(this.pos, entity.level());
                this.hurt = false;
            }
            damage(this.pos, level, entity);
        }
    }

    public void damage(Vec3 pos, Level level, Entity entity) {
        float radius = 8;
        DamageSource genericDamage = level.damageSources().indirectMagic(entity, entity);
        if (!level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) level;
            var entities = level.getEntities(entity, new AABB(pos, pos.add(1, 1, 1)).inflate(radius, radius, radius), (targeted) -> !DamageSources.isFriendlyFireBetween(targeted, entity));
            for (Entity target : entities) {
                if (target instanceof LivingEntity) {
                    target.hurt(genericDamage, 50);
                    if (!target.isAlive()) {
                        this.pos_chain = target.position();
                        this.chain = true;
                        damage(this.pos_chain, level, entity);
                        this.chain_particles.add(target.position());
                    }
                }
            }
            entities.clear();
        }
        if (this.chain) {
            if (level.isClientSide) {
                if (!this.chain_particles.isEmpty()) {
                    for (Vec3 spot : this.chain_particles) {
                        particles(spot, entity.level());
                    }
                }
            }
            this.chain_particles.clear();
            this.chain = false;
        }
    }

    public void particles(Vec3 pos, Level level) {
        Random random = new Random();
        double x = pos.x;
        double y = pos.y;
        double z = pos.z;

        for (int i = 0; i < 240; i++) {
            float randDirection = random.nextFloat();
            float direction;
            if (randDirection <= 0.5f) {
                direction = -1f;
            } else {
                direction = 1f;
            }
            level.addParticle(ParticleHelper.FIRE, x, y, z, random.nextFloat() * direction, random.nextFloat(), random.nextFloat() * direction);
            level.addParticle(ParticleHelper.FIRE, x, y, z, random.nextFloat() * -direction, random.nextFloat(), random.nextFloat() * direction);
            level.addParticle(ParticleHelper.FIRE, x, y, z, random.nextFloat() * direction, random.nextFloat(), random.nextFloat() * -direction);
        }
    }

    private boolean canHit(Entity owner, Entity target) {
        return target.isAlive() && target.isPickable() && !target.isSpectator();
    }
}

