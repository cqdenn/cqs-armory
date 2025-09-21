package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.*;
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

    public static final DeferredHolder<EntityType<?>, EntityType<GenericMageAOE>> GENERIC_MAGE_AOE =
            ENTITIES.register("generic_mage_aoe", () -> EntityType.Builder.<GenericMageAOE>of(GenericMageAOE::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "generic_mage_aoe").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<MomentumOrb>> MOMENTUM_ORB =
            ENTITIES.register("momentum_orb", () -> EntityType.Builder.<MomentumOrb>of(MomentumOrb::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "momentum_orb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ExplosiveMomentumOrb>> EXPLOSIVE_MOMENTUM_ORB =
            ENTITIES.register("explosive_momentum_orb", () -> EntityType.Builder.<ExplosiveMomentumOrb>of(ExplosiveMomentumOrb::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "explosive_momentum_orb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ArrowDamageMomentumOrb>> ARROW_DAMAGE_MOMENTUM_ORB =
            ENTITIES.register("arrow_damage_momentum_orb", () -> EntityType.Builder.<ArrowDamageMomentumOrb>of(ArrowDamageMomentumOrb::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "arrow_damage_momentum_orb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<SpeedMomentumOrb>> SPEED_MOMENTUM_ORB =
            ENTITIES.register("speed_momentum_orb", () -> EntityType.Builder.<SpeedMomentumOrb>of(SpeedMomentumOrb::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "speed_momentum_orb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<InstaDrawMomentumOrb>> INSTA_DRAW_MOMENTUM_ORB =
            ENTITIES.register("insta_draw_momentum_orb", () -> EntityType.Builder.<InstaDrawMomentumOrb>of(InstaDrawMomentumOrb::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "insta_draw_momentum_orb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<DodgeMomentumOrb>> DODGE_MOMENTUM_ORB =
            ENTITIES.register("dodge_momentum_orb", () -> EntityType.Builder.<DodgeMomentumOrb>of(DodgeMomentumOrb::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "dodge_momentum_orb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<BlackHoleMomentumOrb>> BLACK_HOLE_MOMENTUM_ORB =
            ENTITIES.register("black_hole_momentum_orb", () -> EntityType.Builder.<BlackHoleMomentumOrb>of(BlackHoleMomentumOrb::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "black_hole_momentum_orb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ChainLightningMomentumOrb>> CHAIN_LIGHTNING_MOMENTUM_ORB =
            ENTITIES.register("chain_lightning_momentum_orb", () -> EntityType.Builder.<ChainLightningMomentumOrb>of(ChainLightningMomentumOrb::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "chain_lightning_momentum_orb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<IceExplosionMomentumOrb>> ICE_EXPLOSIVE_MOMENTUM_ORB =
            ENTITIES.register("ice_explosive_momentum_orb", () -> EntityType.Builder.<IceExplosionMomentumOrb>of(IceExplosionMomentumOrb::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "ice_explosive_momentum_orb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<RootMomentumOrb>> ROOT_MOMENTUM_ORB =
            ENTITIES.register("root_momentum_orb", () -> EntityType.Builder.<RootMomentumOrb>of(RootMomentumOrb::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "root_momentum_orb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<OrbExplosion>> ORB_EXPLOSION =
            ENTITIES.register("orb_explosion", () -> EntityType.Builder.<OrbExplosion>of(OrbExplosion::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "orb_explosion").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<IceOrbExplosion>> ICE_ORB_EXPLOSION =
            ENTITIES.register("ice_orb_explosion", () -> EntityType.Builder.<IceOrbExplosion>of(IceOrbExplosion::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "ice_orb_explosion").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<AbilityArrow>> ABILITY_ARROW =
            ENTITIES.register("ability_arrow", () -> EntityType.Builder.<AbilityArrow>of(AbilityArrow::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "ability_arrow").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<FireworkProjectile>> FIREWORK_PROJECTILE =
            ENTITIES.register("firework_projectile", () -> EntityType.Builder.<FireworkProjectile>of(FireworkProjectile::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "firework_projectile").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<FireArrow>> FIRE_ARROW =
            ENTITIES.register("fire_arrow", () -> EntityType.Builder.<FireArrow>of(FireArrow::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "fire_arrow").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<IceArrow>> ICE_ARROW =
            ENTITIES.register("ice_arrow", () -> EntityType.Builder.<IceArrow>of(IceArrow::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "ice_arrow").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<BatProjectile>> BAT_PROJECTILE =
            ENTITIES.register("bat_projectile", () -> EntityType.Builder.<BatProjectile>of(BatProjectile::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "bat_projectile").toString()));
}
