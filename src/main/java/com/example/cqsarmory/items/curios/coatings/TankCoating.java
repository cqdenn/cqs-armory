package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.items.curios.OnBlockCoating;
import com.example.cqsarmory.registry.SoundRegistry;
import com.example.cqsarmory.registry.Tags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber
public class TankCoating extends OnBlockCoating {
    public TankCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnBlockEffect(Player blocker, Entity directEntity, Entity causingEntity, float hitDamage, int blockLength, boolean isPerfect) {
        AbilityData.get(blocker).tankCoatingDoubleNextMelee = true;
    }

    @SubscribeEvent
    public static void tankCoatingDoubleNextMelee (LivingDamageEvent.Pre event) {
        Entity sourceEntity = event.getSource().getEntity();
        if (sourceEntity instanceof Player player && event.getSource().is(Tags.DamageTypes.CAUSES_RAGE_GAIN) && AbilityData.get(player).tankCoatingDoubleNextMelee) {
            event.setNewDamage(event.getNewDamage() * 2);
            AbilityData.get(player).tankCoatingDoubleNextMelee = false;
            player.level().playSound(null, player.blockPosition(), SoundRegistry.TANK_COATING_SOUND.get(), SoundSource.PLAYERS);
        }
    }
}
