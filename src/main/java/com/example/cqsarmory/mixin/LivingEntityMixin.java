package com.example.cqsarmory.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "isBlocking", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$shieldBlockDelayRemoved(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity) (Object) this;
        if (living.isUsingItem() && !living.getUseItem().isEmpty()) {
            cir.setReturnValue(living.getUseItem().canPerformAction(net.neoforged.neoforge.common.ItemAbilities.SHIELD_BLOCK));
        } else {
            cir.setReturnValue(false);
        }
    }
}
