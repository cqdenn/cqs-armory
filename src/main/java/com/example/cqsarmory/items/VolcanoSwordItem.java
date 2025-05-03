package com.example.cqsarmory.items;

import com.example.cqsarmory.registry.DamageTypes;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
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
    }


    public static void damage(Vec3 pos, Level level, Entity entity) {
        float radius = 4;
        DamageSource volcano = new DamageSource(level.damageSources().damageTypes.getHolder(DamageTypes.VOLCANO).get(), entity, entity);
        if (!level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) level;
            var entities = level.getEntities(entity, new AABB(pos, pos).inflate(radius, radius, radius), (targeted) -> !DamageSources.isFriendlyFireBetween(targeted, entity));
            for (Entity target : entities) {
                if (target instanceof LivingEntity) {
                    target.hurt(volcano, 20);
                }
            }
            entities.clear();
        }
    }


    private boolean canHit(Entity owner, Entity target) {
        return target.isAlive() && target.isPickable() && !target.isSpectator();
    }
}

