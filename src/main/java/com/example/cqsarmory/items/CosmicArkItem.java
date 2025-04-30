package com.example.cqsarmory.items;

import com.llamalad7.mixinextras.lib.apache.commons.ObjectUtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.util.Utils;
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

public class CosmicArkItem extends SwordItem {
    /*
    TODO
    fix texture/model (they suck)
    add gui for abilityStacks
     */

    public int abilityStacks;

    public CosmicArkItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
        this.abilityStacks = 0;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        Level level = attacker.level();
        this.abilityStacks++;
        if (attacker.level().isClientSide) {
            //FIXME sound doesnt play
            switch (this.abilityStacks) {
                case 1 ->
                        level.playSound(attacker, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 0.4f);
                case 2 ->
                        level.playSound(attacker, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 0.8f);
                case 3 ->
                        level.playSound(attacker, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 1.2f);
                case 4 ->
                        level.playSound(attacker, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 1.6f);
                default ->
                        level.playSound(attacker, attacker.blockPosition(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.MASTER, 1f, 2.0f);
            }
        }

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        DamageSource genericDamage = level.damageSources().indirectMagic(player, player);
        if (this.abilityStacks >= 5) {
            var start = player.getEyePosition().subtract(0, 1, 0);
            if (!player.level().isClientSide()) {
                ServerLevel serverLevel = (ServerLevel) level;
                var proj = player.getForward().scale(7);
                var entities = level.getEntities(player, player.getBoundingBox().expandTowards(proj).inflate(1, 2, 1).inflate(-1, -2, -1));
                player.teleportTo(teleLoc(level, player, 7f).getX(), teleLoc(level, player, 7f).getY() + 1, teleLoc(level, player, 7f).getZ());
                this.abilityStacks = 0;
                for (Entity target : entities) {
                    target.hurt(genericDamage, 20);
                    if (player.killedEntity(serverLevel, (LivingEntity) target)) {
                        refresh();
                    }
                }
                entities.clear();
            }
            //var end = player.getEyePosition().subtract(0, 1, 0);
            for (int i = 0; i <= 70; i++) {
                level.addParticle(ParticleHelper.UNSTABLE_ENDER, start.x + (player.getForward().x * (0.1 * i)), start.y + (player.getForward().y * (0.1 * i)), start.z + (player.getForward().z * (0.1 * i)), 0, 0, 0);
                level.addParticle(ParticleHelper.UNSTABLE_ENDER, start.x + (player.getForward().x * (0.1 * i)), start.y + (player.getForward().y * (0.1 * i)), start.z + (player.getForward().z * (0.1 * i)), 0, 0, 0);
                level.addParticle(ParticleHelper.UNSTABLE_ENDER, start.x + (player.getForward().x * (0.1 * i)), start.y + (player.getForward().y * (0.1 * i)), start.z + (player.getForward().z * (0.1 * i)), 0, 0, 0);
                level.playSound(player, player.blockPosition(), SoundRegistry.ABYSSAL_TELEPORT.get(), SoundSource.MASTER, 0.5f, 1f);
            }
        } else {
            level.playSound(player, player.blockPosition(), SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.MASTER, 0.5f, 0.8f);
        }
        return super.use(level, player, usedHand);
    }

    public void refresh() {
        this.abilityStacks = 5;
    }

    public BlockPos teleLoc (Level level, LivingEntity entity, float distance) {
        var blockHitResult = Utils.getTargetBlock(level, entity, ClipContext.Fluid.NONE, distance);
        var pos = blockHitResult.getBlockPos();
        return  pos;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (isSelected) {

        }

    }

}

