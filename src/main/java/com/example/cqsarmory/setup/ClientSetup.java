package com.example.cqsarmory.setup;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.renderers.*;
import com.example.cqsarmory.registry.EntityRegistry;
import com.example.cqsarmory.registry.ItemRegistry;
import io.redspace.ironsspellbooks.effect.PlanarSightEffect;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.WaywardCompass;
import io.redspace.ironsspellbooks.item.weapons.AutoloaderCrossbow;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import io.redspace.ironsspellbooks.util.IMinecraftInstanceHelper;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(modid = CqsArmory.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent e) {
        //Item Properties
        e.enqueueWork(() -> {
            MinecraftInstanceHelper.instance = new IMinecraftInstanceHelper() {
                @Nullable
                @Override
                public Player player() {
                    return Minecraft.getInstance().player;
                }
            };

            ItemProperties.register(
                    ItemRegistry.IRONWALL.get(),
                    ResourceLocation.withDefaultNamespace("blocking"),
                    (p_174575_, p_174576_, p_174577_, p_174578_) -> p_174577_ != null && p_174577_.isUsingItem() && p_174577_.getUseItem() == p_174575_ ? 1.0F : 0.0F
            );

            ItemProperties.register(
                    ItemRegistry.THORNBARK.get(),
                    ResourceLocation.withDefaultNamespace("blocking"),
                    (p_174575_, p_174576_, p_174577_, p_174578_) -> p_174577_ != null && p_174577_.isUsingItem() && p_174577_.getUseItem() == p_174575_ ? 1.0F : 0.0F
            );

            ItemProperties.register(
                    ItemRegistry.FLASHGUARD.get(),
                    ResourceLocation.withDefaultNamespace("blocking"),
                    (p_174575_, p_174576_, p_174577_, p_174578_) -> p_174577_ != null && p_174577_.isUsingItem() && p_174577_.getUseItem() == p_174575_ ? 1.0F : 0.0F
            );

            for (var item : ItemRegistry.ITEMS.getEntries()) {
                if (item.get() instanceof BowItem) {
                    ItemProperties.register(
                            item.get(),
                            ResourceLocation.withDefaultNamespace("pulling"),
                            (p_174575_, p_174576_, p_174577_, p_174578_) -> p_174577_ != null && p_174577_.isUsingItem() && p_174577_.getUseItem() == p_174575_ ? 1.0F : 0.0F
                    );

                    ItemProperties.register(item.get(), ResourceLocation.withDefaultNamespace("pull"), (p_344163_, p_344164_, p_344165_, p_344166_) -> {
                        if (p_344165_ == null) {
                            return 0.0F;
                        } else {
                            return p_344165_.getUseItem() != p_344163_ ? 0.0F : (float)(p_344163_.getUseDuration(p_344165_) - p_344165_.getUseItemRemainingTicks()) / 20.0F;
                        }
                    });
                }
            }

        });

    }

    @SubscribeEvent
    public static void rendererRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.VOLCANO_EXPLOSION.get(), NoopRenderer::new);
        event.registerEntityRenderer(EntityRegistry.MOMENTUM_ORB.get(), MomentumOrbRenderer::new);
        event.registerEntityRenderer(EntityRegistry.EXPLOSIVE_MOMENTUM_ORB.get(), ExplosiveMomentumOrbRenderer::new);
        event.registerEntityRenderer(EntityRegistry.ARROW_DAMAGE_MOMENTUM_ORB.get(), ArrowDamageMomentumOrbRenderer::new);
        event.registerEntityRenderer(EntityRegistry.DODGE_MOMENTUM_ORB.get(), DodgeMomentumOrbRenderer::new);
        event.registerEntityRenderer(EntityRegistry.INSTA_DRAW_MOMENTUM_ORB.get(), InstaDrawMomentumOrbRenderer::new);
        event.registerEntityRenderer(EntityRegistry.SPEED_MOMENTUM_ORB.get(), SpeedMomentumOrbRenderer::new);
        event.registerEntityRenderer(EntityRegistry.BLACK_HOLE_MOMENTUM_ORB.get(), BlackHoleMomentumOrbRenderer::new);
        event.registerEntityRenderer(EntityRegistry.ICE_EXPLOSIVE_MOMENTUM_ORB.get(), IceExplosionMomentumOrbRenderer::new);
        event.registerEntityRenderer(EntityRegistry.ROOT_MOMENTUM_ORB.get(), RootMomentumOrbRenderer::new);
        event.registerEntityRenderer(EntityRegistry.CHAIN_LIGHTNING_MOMENTUM_ORB.get(), ChainLightningMomentumOrbRenderer::new);
        event.registerEntityRenderer(EntityRegistry.ORB_EXPLOSION.get(), NoopRenderer::new);
        event.registerEntityRenderer(EntityRegistry.ICE_ORB_EXPLOSION.get(), NoopRenderer::new);
    }
}
