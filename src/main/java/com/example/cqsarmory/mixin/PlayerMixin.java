package com.example.cqsarmory.mixin;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Inject(method = "disableShield", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$cancelShield (CallbackInfo ci) {
        ci.cancel();
    }

}
