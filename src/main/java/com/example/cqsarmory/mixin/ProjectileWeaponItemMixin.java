package com.example.cqsarmory.mixin;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.items.curios.QuiverItem;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.bowattributes.registry.BowAttributes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
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

    @Inject(method = "createProjectile", at = @At("RETURN"), cancellable = true)
    private void cqs_armory$createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit, CallbackInfoReturnable<Projectile> cir) {
        if (shooter instanceof Player player) {
            var quiverSlot = CQtils.getPlayerCurioStack(player, "quiver");
            if (!quiverSlot.isEmpty() && quiverSlot.getItem() instanceof QuiverItem quiver) {
                double damage = shooter.getAttributeValue(BowAttributes.ARROW_DAMAGE);
                if (cir.getReturnValue() instanceof AbstractArrow abstractArrow) {
                    if (!abstractArrow.isCritArrow()) {
                        damage = damage * AbilityData.get(shooter).bowVelocity;
                    }
                }
                cir.setReturnValue(quiver.getCustomProjectile(cir.getReturnValue(), player, (float) damage));
            }
        }
    }

}
