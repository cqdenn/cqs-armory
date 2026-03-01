package com.example.cqsarmory.mixin;

import com.example.cqsarmory.utils.CQtils;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractSpell.class)
public abstract class AbstractSpellMixin {


    @Inject(method = "getSchoolType", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$getSchoolType (CallbackInfoReturnable<SchoolType> cir) {
        var self = (AbstractSpell) (Object) this;

        if (CQtils.schoolMap.containsKey(self)) {
            cir.setReturnValue(CQtils.schoolMap.get(self).get());
        }
    }

    @Inject(method = "getEffectiveCastTime", at = @At("RETURN"), cancellable = true)
    private void cqs_armory$getEffectiveCastTime (int spellLevel, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        var self = (AbstractSpell) (Object) this;

        if (self.getSpellId().equals("irons_spellbooks:poison_arrow") || self.getSpellId().equals("irons_spellbooks:fire_arrow") || self.getSpellId().equals("irons_spellbooks:magic_arrow")) {
            double entityCastTimeModifier = 1;
            if (entity != null && entity.getAttributeValue(BowAttributes.DRAW_SPEED) > 0) {
                entityCastTimeModifier = entity.getAttributeValue(BowAttributes.DRAW_SPEED);
            }
            cir.setReturnValue(Math.round(cir.getReturnValue() / (float) entityCastTimeModifier));
        }
    }
}
