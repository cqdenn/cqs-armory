package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.MomentumOrb;
import com.example.cqsarmory.data.entity.ability.OrbExplosion;
import com.example.cqsarmory.data.entity.ability.VolcanoExplosion;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.entity.spells.wisp.WispEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

    public static final DeferredHolder<EntityType<?>, EntityType<VolcanoExplosion>> VOLCANO_EXPLOSION =
            ENTITIES.register("volcano_explosion", () -> EntityType.Builder.<VolcanoExplosion>of(VolcanoExplosion::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "volcano_explosion").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<MomentumOrb>> MOMENTUM_ORB =
            ENTITIES.register("momentum_orb", () -> EntityType.Builder.<MomentumOrb>of(MomentumOrb::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "momentum_orb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<OrbExplosion>> ORB_EXPLOSION =
            ENTITIES.register("orb_explosion", () -> EntityType.Builder.<OrbExplosion>of(OrbExplosion::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "orb_explosion").toString()));
}
