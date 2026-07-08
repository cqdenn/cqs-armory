package com.example.cqsarmory.mixin;

import com.example.cqsarmory.data.entity.living.Dwarf;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSpellCastingMob.class)
public abstract class AbstractSpellCastingMobMixin {

    @Redirect(
            method = "finishDrinkingPotion",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"
            )
    )
    private void cqs_armory$dwarfDrinkingSound(Level instance, Player player, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch) {
        if (((AbstractSpellCastingMob) (Object) this) instanceof Dwarf dwarf) {
            instance.playSound(player, x, y, z, dwarf.getDrinkingSoundPublic(), category);
        } else {
            instance.playSound(player, x, y, z, sound, category);
        }
    }

}
