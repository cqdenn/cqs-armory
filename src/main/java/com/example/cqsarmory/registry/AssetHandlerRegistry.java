package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import io.redspace.atlasapi.api.AssetHandler;
import io.redspace.atlasapi.api.AtlasApiRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AssetHandlerRegistry {
        private static final DeferredRegister<AssetHandler> HANDLERS = DeferredRegister.create(AtlasApiRegistry.ASSET_HANDLER_REGISTRY_KEY, CqsArmory.MODID);

        public static final Supplier<WeaponsetAssetHandler> WEAPONSET_HANDLER = HANDLERS.register("weaponset_handler", WeaponsetAssetHandler::new);

        public static void register(IEventBus eventBus) {
            HANDLERS.register(eventBus);
        }
    }
