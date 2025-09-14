package com.example.cqsarmory.mixin;

import com.example.cqsarmory.data.AbilityData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(method = "disableShield", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$cancelShield(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "getProjectile", at = @At("RETURN"), cancellable = true)
    private void cqs_armory$getProjectile(ItemStack shootable, CallbackInfoReturnable<ItemStack> cir) {
        if (cir.getReturnValue().isEmpty() && AbilityData.get((Player) (Object) this).quiverArrowCount >= 1) {
            ItemStack arrow = new ItemStack(Items.ARROW);
            arrow.set(DataComponents.INTANGIBLE_PROJECTILE, Unit.INSTANCE);
            cir.setReturnValue(arrow);
        }
    }

}
