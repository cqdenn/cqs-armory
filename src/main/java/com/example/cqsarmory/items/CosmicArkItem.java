package com.example.cqsarmory.items;

import com.llamalad7.mixinextras.lib.apache.commons.ObjectUtils;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import org.openjdk.nashorn.internal.runtime.Undefined;

public class CosmicArkItem extends SwordItem {
    int abilityStacks = 0;

    public CosmicArkItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        Level level = attacker.level();
        abilityStacks++;
        //FIXME sound doesnt play
        if (abilityStacks == 1) {level.playSound(attacker, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 0.4f);}
        else if (abilityStacks == 2) {level.playSound(attacker, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 0.8f);}
        else if (abilityStacks == 3) {level.playSound(attacker, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 1.2f);}
        else if (abilityStacks == 4) {level.playSound(attacker, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 1.6f);}
        else {level.playSound(attacker, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 2.0f);}

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        boolean refresh = false;
        DamageSource genericDamage = level.damageSources().indirectMagic(player, player);
        if (abilityStacks >= 5) {
            var start = player.getEyePosition().subtract(0, 1, 0);
            if (!player.level().isClientSide()) {
                //ServerLevel serverLevel = (ServerLevel) level;
                var proj = player.getForward().scale(7).multiply(1, 0, 1);
                var entities = level.getEntities(player, player.getBoundingBox().expandTowards(proj));
                player.teleportRelative((player.getForward().x) * 7, 0, (player.getForward().z) * 7);
                for (Entity target : entities) {
                    target.hurt(genericDamage, 20);
                    if (!target.isAlive() && !entities.isEmpty() && !refresh) {
                        refresh = true;
                    }
                }
                entities.clear();
                //FIXME refresh takes 2-3 uses to be false
                refresh(refresh);
            }
            //var end = player.getEyePosition().subtract(0, 1, 0);
            for (int i = 0; i <= 70; i++) {
                level.addParticle(ParticleHelper.UNSTABLE_ENDER, start.x + (player.getForward().x * (0.1 * i)), start.y, start.z + (player.getForward().z * (0.1 * i)), 0, 0, 0);
                level.addParticle(ParticleHelper.UNSTABLE_ENDER, start.x + (player.getForward().x * (0.1 * i)), start.y, start.z + (player.getForward().z * (0.1 * i)), 0, 0, 0);
                level.addParticle(ParticleHelper.UNSTABLE_ENDER, start.x + (player.getForward().x * (0.1 * i)), start.y, start.z + (player.getForward().z * (0.1 * i)), 0, 0, 0);
            }
        } else if (abilityStacks < 5) {
            level.playSound(player, player.blockPosition(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.MASTER, 1f, 0.8f);
        }
        return super.use(level, player, usedHand);
    }

    public void refresh (boolean refresh) {
        if (refresh) {abilityStacks = 5;} else {abilityStacks = 0;}
    }

}

