package com.example.cqsarmory.mixin;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {

    @Accessor("inGround")
    abstract boolean cqs_armory$isInGround();

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;isCritArrow()Z",
                    ordinal = 0
            )
    )
    private boolean cqs_armory$customCritTrail(AbstractArrow proj) {
        if (!proj.isCritArrow()) return false;
        if (proj instanceof AbilityArrow arrow) {
            arrow.customCritParticles();
        } else {
            Vec3 vec3 = proj.getDeltaMovement();
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;

            for (int i = 0; i < 4; i++) {
                proj.level()
                        .addParticle(
                                ParticleTypes.CRIT,
                                proj.getX() + d5 * (double)i / 4.0,
                                proj.getY() + d6 * (double)i / 4.0,
                                proj.getZ() + d1 * (double)i / 4.0,
                                -d5,
                                -d6 + 0.2,
                                -d1
                        );
            }
        }

        return false;
    }

    @Inject(method = "onHitBlock", at = @At("TAIL"))
    private void cqs_armory$huntersBoosterMissedArrowCheck(BlockHitResult result, CallbackInfo ci) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        if (result.getType() == HitResult.Type.BLOCK && (arrow.isRemoved() || cqs_armory$isInGround()) && arrow.getOwner() != null) {
            AbilityData.get(arrow.getOwner()).huntersMarkConsecutiveArrowsHit = 0;
        }
    }

}
