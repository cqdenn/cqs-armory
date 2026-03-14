package com.example.cqsarmory.mixin;

import com.example.cqsarmory.registry.DamageTypes;
import io.redspace.ironsspellbooks.entity.spells.ice_tomb.IceTombEntity;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IceTombEntity.class)
public abstract class IceTombEntityMixin {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$blizzard_aoe_tomb_damage_cancel(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.is(DamageTypes.BLIZZARD_MAGE_AOE)) cir.setReturnValue(false);
    }
}
