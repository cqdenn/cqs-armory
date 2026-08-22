package com.example.cqsarmory.mixin;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.fire_arrow.FireArrowProjectile;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireField;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireArrowProjectile.class)
public class FireArrowProjectileMixin {

    @Inject(method = "onHit", at = @At("TAIL"))
    private void cqs_armory$fireFieldToFireArrow(HitResult hitResult, CallbackInfo ci) {
        FireArrowProjectile arrow = (FireArrowProjectile) (Object) this;
        createFireField(arrow.level(), Utils.moveToRelativeGroundLevel(arrow.level(), hitResult.getLocation(), 2, 6));
    }

    public void createFireField(Level level, Vec3 location) {
        if (!level.isClientSide) {
            FireArrowProjectile arrow = (FireArrowProjectile) (Object) this;
            FireField fire = new FireField(level);
            fire.setOwner(arrow.getOwner());
            fire.setDuration(160);
            fire.setDamage(2);
            fire.setRadius(4);
            fire.setCircular();
            fire.moveTo(location);
            level.addFreshEntity(fire);
        }
    }

}
