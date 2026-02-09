package com.example.cqsarmory.mixin;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.utils.CQtils;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

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
