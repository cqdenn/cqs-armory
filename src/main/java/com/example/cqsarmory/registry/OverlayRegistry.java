package com.example.cqsarmory.registry;


import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.gui.overlays.MomentumBarOverlay;
import com.example.cqsarmory.gui.overlays.MomentumDamageOverlay;
import com.example.cqsarmory.gui.overlays.MomentumSpeedOverlay;
import com.example.cqsarmory.gui.overlays.RageBarOverlay;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.gui.overlays.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = CqsArmory.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class OverlayRegistry {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisterOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, CqsArmory.id("rage_overlay"), RageBarOverlay.getInstance());
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, CqsArmory.id("momentum_overlay"), MomentumBarOverlay.getInstance());
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, CqsArmory.id("momentum_speed_overlay"), MomentumSpeedOverlay.getInstance());
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, CqsArmory.id("momentum_damage_overlay"), MomentumDamageOverlay.getInstance());
    }
}
