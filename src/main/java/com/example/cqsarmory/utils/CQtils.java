package com.example.cqsarmory.utils;

import net.minecraft.world.entity.player.Player;

public class CQtils {

    public static void disableShield(Player player, int ticks) {
        player.getCooldowns().addCooldown(player.getUseItem().getItem(), ticks);
        player.stopUsingItem();
        player.level().broadcastEntityEvent(player, (byte)30);
    }
}
