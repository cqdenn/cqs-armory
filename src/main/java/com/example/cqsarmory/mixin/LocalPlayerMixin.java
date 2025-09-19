package com.example.cqsarmory.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

    @Inject(method = "canStartSprinting", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$canStartSprinting(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(
                !((LocalPlayer) (Object)this).isSprinting()
                && ((LocalPlayer) (Object)this).hasEnoughImpulseToStartSprinting()
                && ((LocalPlayer) (Object)this).hasEnoughFoodToStartSprinting()
                && !((LocalPlayer) (Object)this).isUsingItem()
                //&& !((LocalPlayer) (Object)this).hasEffect(MobEffects.BLINDNESS) removed for blindness brand (and its just annoying)
                && (!((LocalPlayer) (Object)this).isPassenger() || ((LocalPlayer) (Object)this).vehicleCanSprint(((LocalPlayer) (Object)this).getVehicle()))
                && !((LocalPlayer) (Object)this).isFallFlying()
        );
    }

    @Redirect(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/core/Holder;)Z",
                    ordinal = 0
            )
    )
    private boolean cqs_armory$aiStep(LocalPlayer instance, Holder holder) {
        return false;
    }

}