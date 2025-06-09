package com.example.cqsarmory.items;

import com.example.cqsarmory.data.AbilityData;
import com.llamalad7.mixinextras.lib.apache.commons.ObjectUtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.Log;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.checkerframework.checker.units.qual.A;
import org.openjdk.nashorn.internal.runtime.Undefined;

import java.util.List;

public class CosmicArkItem extends SwordItem {
    /*
    TODO
    fix texture/model (they suck)
    add gui for abilityStacks
     */


    public CosmicArkItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        Level level = attacker.level();
        AbilityData.get(attacker).cosmicArk.abilityStacks++;
        switch (AbilityData.get(attacker).cosmicArk.abilityStacks) {
            case 1 ->
                    level.playSound(null, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 0.4f);
            case 2 ->
                    level.playSound(null, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 0.8f);
            case 3 ->
                    level.playSound(null, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 1.2f);
            case 4 ->
                    level.playSound(null, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 1.6f);
            default ->
                    level.playSound(null, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 2.0f);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        DamageSource genericDamage = level.damageSources().indirectMagic(player, player);
        if (!player.level().isClientSide()) {
            if (AbilityData.get(player).cosmicArk.abilityStacks >= 5) {
                AbilityData.get(player).cosmicArk.abilityStacks = 0;

                ServerLevel serverLevel = (ServerLevel) level;
                Vec3 start = player.position();
                Vec3 startParticles = start.add(0, 1, 0);
                Vec3 end = new Vec3(teleLoc(level, player, 7f).getX(), teleLoc(level, player, 7f).getY(), teleLoc(level, player, 7f).getZ());
                AABB range = player.getBoundingBox().expandTowards(end).inflate(1, 2, 1);
                AABB test = new AABB(start, end).inflate(2);
                List<? extends Entity> entities = player.level().getEntities(player, test);
                player.teleportTo(teleLoc(level, player, 7f).getX(), teleLoc(level, player, 7f).getY() + 1, teleLoc(level, player, 7f).getZ());
                for (int i = 0; i <= 70; i++) {
                    MagicManager.spawnParticles(level, ParticleHelper.UNSTABLE_ENDER, startParticles.x + (player.getForward().x * (0.1 * i)), startParticles.y + (player.getForward().y * (0.1 * i)), startParticles.z + (player.getForward().z * (0.1 * i)), 3, 0, 0, 0, 0, false);
                }
                level.playSound(null, player.blockPosition(), SoundRegistry.ABYSSAL_TELEPORT.get(), SoundSource.MASTER, 0.5f, 1f);
                for (Entity target : entities) {
                    target.hurt(genericDamage, (float) (player.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 2));
                    if (player.killedEntity(serverLevel, (LivingEntity) target)) {
                        refresh(player);
                    }
                }
                entities.clear();

            } else {
                level.playSound(null, player.blockPosition(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.MASTER, 0.5f, 0.8f);
            }
        }
        return super.use(level, player, usedHand);
    }

    public void refresh(LivingEntity entity) {
        AbilityData.get(entity).cosmicArk.abilityStacks = 5;
    }

    public BlockPos teleLoc(Level level, LivingEntity entity, float distance) {
        var blockHitResult = Utils.getTargetBlock(level, entity, ClipContext.Fluid.NONE, distance);
        var pos = blockHitResult.getBlockPos();
        return pos;
    }
}

