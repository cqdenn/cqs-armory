package com.example.cqsarmory.events;

import com.example.cqsarmory.CqsArmory;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

@EventBusSubscriber(value = Dist.CLIENT, modid = CqsArmory.MODID, bus = EventBusSubscriber.Bus.MOD)
public class KeyMappings {

    public static final String KEY_BIND_GENERAL_CATEGORY = "key.cqs_armory.group_1";
    private static String getResourceName(String name) {
        return String.format("key.cqs_armory.%s", name);
    }

    public static final KeyMapping DODGE_KEYMAP = new KeyMapping(getResourceName("dodge"), KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, InputConstants.KEY_CAPSLOCK, KEY_BIND_GENERAL_CATEGORY);

    @SubscribeEvent
    public static void onRegisterKeybinds(RegisterKeyMappingsEvent event) {
        event.register(DODGE_KEYMAP);
    }

}
