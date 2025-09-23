package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.OnHitCoating;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class LightningCoating extends OnHitCoating {
    public LightningCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
        int amplifier = 1 + (int) ((hitDamage / 10) * attacker.getAttributeValue(AttributeRegistry.LIGHTNING_SPELL_POWER));
        attacker.addEffect(new MobEffectInstance(MobEffectRegistry.THUNDERSTORM, 40, amplifier, false, false, false));
    }
}
