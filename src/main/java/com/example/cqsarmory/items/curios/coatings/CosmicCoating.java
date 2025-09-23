package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.OnHitCoating;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.CooldownInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CosmicCoating extends OnHitCoating {
    public CosmicCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
        if (attacker instanceof ServerPlayer serverPlayer) {
            var cooldowns = MagicData.getPlayerMagicData(serverPlayer).getPlayerCooldowns();
            cooldowns.getSpellCooldowns().forEach((key, value) -> cooldowns.decrementCooldown(value, 5));
            cooldowns.syncToPlayer(serverPlayer);
        }
    }
}
