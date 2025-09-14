package com.example.cqsarmory.mixin;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.network.SyncQuiverArrowsPacket;
import com.example.cqsarmory.registry.ItemRegistry;
import io.redspace.bowattributes.registry.BowAttributes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.item.BowItem.getPowerForTime;

@Mixin(BowItem.class)
public abstract class BowItemMixin {

    /*@Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$bowUse (Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean flag = !player.getProjectile(itemstack).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.neoforged.neoforge.event.EventHooks.onArrowNock(itemstack, level, player, hand, flag);
        if (ret != null) cir.setReturnValue(ret);

        if (player.hasInfiniteMaterials() || flag) {
            player.startUsingItem(hand);
            cir.setReturnValue(InteractionResultHolder.consume(itemstack));
        } else if (AbilityData.get(player).quiverArrowCount >= 1 && ItemRegistry.BASIC_QUIVER.get().isEquippedBy(player)) {
            player.startUsingItem(hand);
            cir.setReturnValue(InteractionResultHolder.consume(itemstack));
        } else  {
            cir.setReturnValue(InteractionResultHolder.fail(itemstack));
        }
    }*/

    /*@Inject(method = "releaseUsing", at = @At("HEAD"), cancellable = true)
    public void cqs_armory$bowRelease(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft, CallbackInfo ci) {

        if (!(entityLiving instanceof Player player)) return;

        // Check if no real arrows and quiver has arrows
        ItemStack projectile = player.getProjectile(stack);
        if (projectile.isEmpty() && AbilityData.get(player).quiverArrowCount > 0) {

            // Create a virtual arrow stack
            ItemStack virtualArrow = new ItemStack(Items.ARROW);

            // Manually create and shoot the arrow
            ArrowItem arrowItem = (ArrowItem) (virtualArrow.getItem() instanceof ArrowItem ? virtualArrow.getItem() : Items.ARROW);
            AbstractArrow arrowEntity = arrowItem.createArrow(level, virtualArrow, player, player.getUseItem());

            arrowEntity.setBaseDamage(player.getAttribute(BowAttributes.ARROW_DAMAGE).getValue());

            // Calculate power
            int charge = stack.getUseDuration(player) - timeLeft;
            float velocity = BowItem.getPowerForTime(charge);
            arrowEntity.setCritArrow(velocity == 1.0f);
            if (velocity < 0.1F) return;

            arrowEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity * 3.0F, 1.0F);
            level.addFreshEntity(arrowEntity);

            // Sound & effects
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);

            // Damage bow
            stack.hurtAndBreak(1, player, player.getSlotForHand(player.getUsedItemHand()));

            // Consume from quiver
            if (!player.level().isClientSide) {
                int newArrowCount = AbilityData.get(player).quiverArrowCount - 1;
                AbilityData.get(player).quiverArrowCount = newArrowCount;
                PacketDistributor.sendToPlayer((ServerPlayer) player, new SyncQuiverArrowsPacket(newArrowCount));
            }

            // Cancel original method so vanilla doesn't interfere
            ci.cancel();
        }
    }*/

}
