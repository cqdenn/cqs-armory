package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.OnHitCoating;
import io.redspace.skillcasting.data.SkillcastingData;
import io.redspace.skillcasting.data.cast.CasterRef;
import io.redspace.skillcasting.network.SkillcastingNetwork;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CosmicCoating extends OnHitCoating {
    public CosmicCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    public final int COOLDOWN_TICK_DECREMENT = 5;

    @Override
    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
        if (attacker instanceof ServerPlayer serverPlayer) {
            var cooldowns = SkillcastingData.get(serverPlayer).cooldowns();
            cooldowns.view().forEach((key, value) -> {
                for (int i=0;i<COOLDOWN_TICK_DECREMENT;i++) { //Iron431 is cringe and removed the decrement cooldowns method admins plz fix
                    value.tick();
                }
            });
            SkillcastingNetwork.syncAllCooldowns(CasterRef.entity(serverPlayer), SkillcastingData.get(serverPlayer));
        }
    }
}
