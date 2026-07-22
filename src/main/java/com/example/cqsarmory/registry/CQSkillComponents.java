package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.mojang.serialization.Codec;
import io.redspace.skillcasting.Skillcasting;
import io.redspace.skillcasting.data.component.ComponentType;
import io.redspace.skillcasting.registry.SkillcastingRegistries;
import io.redspace.skillcasting.util.StreamCodecUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CQSkillComponents {

    private static final DeferredRegister<ComponentType<?>> COMPONENT_TYPES =
            DeferredRegister.create(SkillcastingRegistries.COMPONENT_TYPE_REGISTRY_KEY, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        COMPONENT_TYPES.register(eventBus);
    }


    public static final DeferredHolder<ComponentType<?>, ComponentType<Integer>> PROJECTILE_TRACKER =
            COMPONENT_TYPES.register("projectile_tracker", () -> ComponentType.<Integer>builder()
                    .persisted(Codec.INT)
                    .synced(StreamCodecUtils.INT)
                    .build());

    public static final DeferredHolder<ComponentType<?>, ComponentType<Float>> PROJECTILE_SPREAD =
            COMPONENT_TYPES.register("projectile_spread", () -> ComponentType.<Float>builder()
                    .persisted(Codec.FLOAT)
                    .synced(StreamCodecUtils.FLOAT)
                    .build());

}
