package com.example.cqsarmory.mixin;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractSpell.class)
public abstract class AbstractSpellMixin {


    /*@Inject(method = "canBeCastedBy", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$overchargeBrandMana (int spellLevel, CastSource castSource, MagicData playerMagicData, Player player, CallbackInfoReturnable<CastResult> cir) {
        if (ServerConfigs.DISABLE_ADVENTURE_MODE_CASTING.get()) {
            if (player instanceof ServerPlayer serverPlayer && serverPlayer.gameMode.getGameModeForPlayer() == GameType.ADVENTURE) {
                cir.setReturnValue(new CastResult(CastResult.Type.FAILURE, Component.translatable("ui.irons_spellbooks.cast_error_adventure").withStyle(ChatFormatting.RED)));
            }
        }
        AbstractSpell spell = (AbstractSpell) (Object) this;
        var playerMana = playerMagicData.getMana();
        var event = new SpellOnCastEvent((ServerPlayer) player, spell.getSpellId(), spellLevel, spell.getManaCost(spellLevel), spell.getSchoolType(), castSource);
        NeoForge.EVENT_BUS.post(event);

        boolean hasEnoughMana = playerMana - event.getManaCost() >= 0;
        boolean isSpellOnCooldown = playerMagicData.getPlayerCooldowns().isOnCooldown(spell);
        boolean hasRecastForSpell = playerMagicData.getPlayerRecasts().hasRecastForSpell(spell.getSpellId());
        if (spell.requiresLearning() && !spell.isLearned(player)) {
            cir.setReturnValue(new CastResult(CastResult.Type.FAILURE, Component.translatable("ui.irons_spellbooks.cast_error_unlearned").withStyle(ChatFormatting.RED)));
        } else if (castSource == CastSource.SCROLL && spell.getRecastCount(spellLevel, player) > 0) {
            cir.setReturnValue(new CastResult(CastResult.Type.FAILURE, Component.translatable("ui.irons_spellbooks.cast_error_scroll", spell.getDisplayName(player)).withStyle(ChatFormatting.RED)));
        } else if ((castSource == CastSource.SPELLBOOK || castSource == CastSource.SWORD) && isSpellOnCooldown && !(player.isCreative() && !ServerConfigs.CREATIVE_COOLDOWN.get())) {
            cir.setReturnValue(new CastResult(CastResult.Type.FAILURE, Component.translatable("ui.irons_spellbooks.cast_error_cooldown", spell.getDisplayName(player)).withStyle(ChatFormatting.RED)));
        } else if (!hasRecastForSpell && castSource.consumesMana() && !hasEnoughMana && !(player.isCreative() && !ServerConfigs.CREATIVE_MANA_COST.get())) {
            cir.setReturnValue(new CastResult(CastResult.Type.FAILURE, Component.translatable("ui.irons_spellbooks.cast_error_mana", spell.getDisplayName(player)).withStyle(ChatFormatting.RED)));
        } else {
            cir.setReturnValue(new CastResult(CastResult.Type.SUCCESS));
        }
    }*/

    /*@Inject(method = "getSchoolType", at = @At("HEAD"), cancellable = true)
    private void cqs_armory$getSchoolType (CallbackInfoReturnable<SchoolType> cir) {
        var self = (AbstractSpell) (Object) this;

        if (CQtils.schoolMap.containsKey(self)) {
            cir.setReturnValue(CQtils.schoolMap.get(self).get());
        }
    }*/

    /*@Inject(method = "getEffectiveCastTime", at = @At("RETURN"), cancellable = true)
    private void cqs_armory$getEffectiveCastTime (int spellLevel, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        var self = (AbstractSpell) (Object) this;

        if (self.getSpellId().equals("irons_spellbooks:poison_arrow") || self.getSpellId().equals("irons_spellbooks:fire_arrow") || self.getSpellId().equals("irons_spellbooks:magic_arrow")) {
            double entityCastTimeModifier = 1;
            if (entity != null && entity.getAttributeValue(BowAttributes.DRAW_SPEED) > 0) {
                entityCastTimeModifier = entity.getAttributeValue(BowAttributes.DRAW_SPEED);
            }
            cir.setReturnValue(Math.round(cir.getReturnValue() / (float) entityCastTimeModifier));
        }
    }*/
}
