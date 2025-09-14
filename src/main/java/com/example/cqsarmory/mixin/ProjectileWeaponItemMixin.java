package com.example.cqsarmory.mixin;

import com.example.cqsarmory.data.AbilityData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(ProjectileWeaponItem.class)
public abstract class ProjectileWeaponItemMixin {

    @Inject(method = "useAmmo", at = @At("HEAD"), cancellable = true)
    private static void cqs_armory$useAmmo(ItemStack weapon, ItemStack ammo, LivingEntity shooter, boolean intangable, CallbackInfoReturnable<ItemStack> cir) {
        if (!ammo.is(Items.ARROW)) return;
        if (AbilityData.get(shooter).quiverArrowCount <= 0) return;
        if (intangable) return;
        AbilityData.get(shooter).quiverArrowCount--;
        ItemStack arrow = new ItemStack(Items.ARROW);
        arrow.set(DataComponents.INTANGIBLE_PROJECTILE, Unit.INSTANCE);
        cir.setReturnValue(arrow);
    }

}
