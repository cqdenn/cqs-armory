package com.example.cqsarmory.mixin;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.registry.ItemRegistry;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow public abstract void playNotifySound(SoundEvent sound, SoundSource source, float volume, float pitch);

    @Inject(method = "getFlyingSpeed", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$overrideAirStrafing(CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        if (player.getAbilities().flying && !player.isPassenger()) {
            cir.setReturnValue(player.isSprinting() ? player.getAbilities().getFlyingSpeed() * 2.0F : player.getAbilities().getFlyingSpeed());
        } else if (ItemRegistry.SLIPSTREAM.get().isEquippedBy(player) && player.getY() >= Utils.findRelativeGroundLevel(player.level(), player.position(), 4) + 3) {//3 or more blocks off ground
            cir.setReturnValue(player.isSprinting() ? 0.1f : 0.08f);
        } else {
            cir.setReturnValue(player.isSprinting() ? 0.025999999F : 0.02F);
        }
    }

    @Inject(method = "disableShield", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$cancelShield(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "getProjectile", at = @At("RETURN"), cancellable = true)
    private void cqs_armory$getProjectile(ItemStack shootable, CallbackInfoReturnable<ItemStack> cir) {
        if (cir.getReturnValue().isEmpty() && AbilityData.get((Player) (Object) this).quiverArrowCount >= 1 && !CQtils.getPlayerCurioStack((Player) (Object) this, "quiver").isEmpty()) {
            ItemStack arrow = new ItemStack(Items.ARROW);
            arrow.set(DataComponents.INTANGIBLE_PROJECTILE, Unit.INSTANCE);
            cir.setReturnValue(arrow);
        }
    }

    @Redirect(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;hasEffect(Lnet/minecraft/core/Holder;)Z"
            )
    )
    private boolean cqs_armory$attack(Player instance, Holder holder) {
        return !holder.is(MobEffects.BLINDNESS);
    }


}
