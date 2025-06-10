package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundRegistry {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    public static DeferredHolder<SoundEvent, SoundEvent> SNEEZE_SOUND = registerSoundEvent("sneeze");
    public static DeferredHolder<SoundEvent, SoundEvent> DODGE_SOUND = registerSoundEvent("dodge");
    public static DeferredHolder<SoundEvent, SoundEvent> RIPOSTE_CAST_SOUND = registerSoundEvent("riposte_cast");
    public static DeferredHolder<SoundEvent, SoundEvent> RIPOSTE_HIT_SOUND = registerSoundEvent("riposte_hit");




    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, name)));
    }
}
