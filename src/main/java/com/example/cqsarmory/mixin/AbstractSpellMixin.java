package com.example.cqsarmory.mixin;

import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(AbstractSpell.class)
public abstract class AbstractSpellMixin {


    @Inject(method = "getSchoolType", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$getSchoolType (CallbackInfoReturnable<SchoolType> cir) {
        var self = (AbstractSpell) (Object) this;

        if (CQtils.schoolMap.containsKey(self)) {
            cir.setReturnValue(CQtils.schoolMap.get(self).get());
        }
    }
}
