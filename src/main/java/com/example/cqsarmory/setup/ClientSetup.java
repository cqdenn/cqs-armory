package com.example.cqsarmory.setup;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.api.AbilityAnimations;
import com.example.cqsarmory.registry.EntityRegistry;
import dev.kosmx.playerAnim.api.TransformType;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AdjustmentModifier;
import dev.kosmx.playerAnim.api.layered.modifier.MirrorModifier;
import dev.kosmx.playerAnim.core.util.Vec3f;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.compat.tetra.TetraProxy;
import io.redspace.ironsspellbooks.effect.PlanarSightEffect;
import io.redspace.ironsspellbooks.entity.spells.magic_missile.MagicMissileRenderer;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.WaywardCompass;
import io.redspace.ironsspellbooks.item.weapons.AutoloaderCrossbow;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import io.redspace.ironsspellbooks.setup.IronsAdjustmentModifier;
import io.redspace.ironsspellbooks.util.IMinecraftInstanceHelper;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.Optional;

@EventBusSubscriber(modid = CqsArmory.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void rendererRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.VOLCANO_EXPLOSION.get(), NoopRenderer::new);
    }

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent e) {
        //Item Properties
/*
        e.enqueueWork(() -> {
            MinecraftInstanceHelper.instance = new IMinecraftInstanceHelper() {
                @Nullable
                @Override
                public Player player() {
                    return Minecraft.getInstance().player;
                }
            };
            ItemProperties.register(ItemRegistry.WAYWARD_COMPASS.get(), ResourceLocation.withDefaultNamespace("angle"),
                    new CompassItemPropertyFunction((level, itemStack, entity) -> WaywardCompass.getCatacombsLocation(entity, itemStack)));

            ItemProperties.register(ItemRegistry.AUTOLOADER_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("pull"), (itemStack, clientLevel, livingEntity, i) -> CrossbowItem.isCharged(itemStack) ? 0.0F : AutoloaderCrossbow.getLoadingTicks(itemStack) / (float) AutoloaderCrossbow.getChargeDuration(itemStack, livingEntity));
            ItemProperties.register(ItemRegistry.AUTOLOADER_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("pulling"), (itemStack, clientLevel, livingEntity, i) -> AutoloaderCrossbow.isLoading(itemStack) && !CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
            ItemProperties.register(ItemRegistry.AUTOLOADER_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("charged"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
            ItemProperties.register(ItemRegistry.AUTOLOADER_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("firework"), (itemStack, clientLevel, livingEntity, i) -> {
                ChargedProjectiles chargedprojectiles = itemStack.get(DataComponents.CHARGED_PROJECTILES);
                return chargedprojectiles != null && chargedprojectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
            });
            ItemRegistry.getIronsItems().stream().filter(item -> item.get() instanceof SpellBook).forEach((item) -> CuriosRendererRegistry.register(item.get(), SpellBookCurioRenderer::new));
        });

 */



        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                AbilityAnimations.ANIMATION_RESOURCE,
                42,
                (player) -> {
                    var animation = new ModifierLayer<>();
                    IronsAdjustmentModifier.INSTANCE = new IronsAdjustmentModifier((partName, partialTick) -> {
                        boolean handleHead = animation.getAnimation() != null && !animation.getAnimation().get3DTransform("head", TransformType.ROTATION, 0.5f, Vec3f.ZERO).equals(Vec3f.ZERO);
                        switch (partName) {
                            case "head" -> {
                                if (handleHead) {
                                    return Optional.of(new AdjustmentModifier.PartModifier(new Vec3f(0, Mth.lerp(partialTick, (player.yHeadRotO - player.yBodyRotO), (player.yHeadRot - player.yBodyRot)) * Mth.DEG_TO_RAD, 0), Vec3f.ZERO));
                                } else {
                                    return Optional.empty();
                                }
                            }
                            case "rightArm", "leftArm" -> {
                                float x = Mth.lerp(partialTick, player.xRotO, player.getXRot());
                                float y = Mth.lerp(partialTick, (player.yHeadRotO - player.yBodyRotO), (player.yHeadRot - player.yBodyRot));
                                return Optional.of(new AdjustmentModifier.PartModifier(new Vec3f(x * Mth.DEG_TO_RAD, y * Mth.DEG_TO_RAD, 0), Vec3f.ZERO));
                            }
                            default -> {
                                return Optional.empty();
                            }
                        }
                    });
                    animation.addModifier(IronsAdjustmentModifier.INSTANCE,0);
                    animation.addModifierLast(new MirrorModifier() {
                        @Override
                        public boolean isEnabled() {
                            return ClientMagicData.getSyncedSpellData(player).getCastingEquipmentSlot().equals(SpellSelectionManager.OFFHAND);
                        }
                    });

                    return animation;
                });

        TetraProxy.PROXY.initClient();

    }

}
