package com.example.cqsarmory.mixin;

import com.example.cqsarmory.registry.MobEffectRegistry;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public class GuiMixin {

    @Redirect(
            method = "renderHealthLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;hasEffect(Lnet/minecraft/core/Holder;)Z"
            )
    )
    private boolean cqs_armory$life_steal_gui(Player player, Holder<MobEffect> effect) {
        return player.hasEffect(effect) || player.hasEffect(MobEffectRegistry.LIFE_STEAL);
    }
}
