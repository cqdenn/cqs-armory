package com.example.cqsarmory.data.entity;

import com.example.cqsarmory.registry.ItemRegistry;
import io.redspace.ironsspellbooks.player.AdditionalWanderingTrades;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.redspace.ironsspellbooks.api.util.Utils.random;

@EventBusSubscriber
public class CQWanderingTrades {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void addWanderingTrades(WandererTradesEvent event) {

        List<ItemStack> tierOneIngots = List.of(
                Items.AMETHYST_SHARD.getDefaultInstance(),
                Items.IRON_INGOT.getDefaultInstance(),
                Items.GOLD_INGOT.getDefaultInstance(),
                Items.COPPER_INGOT.getDefaultInstance(),
                Items.DIAMOND.getDefaultInstance(),
                Items.OBSIDIAN.getDefaultInstance());

        List<ItemStack> tierTwoIngots = List.of(
                ItemRegistry.LIVING_WEAPONSET.ingot().get().toStack(),
                ItemRegistry.UMBRITE_WEAPONSET.ingot().get().toStack(),
                ItemRegistry.SILVERSTEEL_WEAPONSET.ingot().get().toStack(),
                ItemRegistry.DWARVEN_STEEL_WEAPONSET.ingot().get().toStack());

        List<ItemStack> tierThreeIngots = List.of(
                ItemRegistry.BLAZING_WEAPONSET.ingot().get().toStack(),
                ItemRegistry.WITHERSTEEL_WEAPONSET.ingot().get().toStack());

        List<VillagerTrades.ItemListing> additionalGenericTrades = List.of(
                //repeated method calls to increase odds of appearing
                ingotTrade(tierOneIngots, new ItemCost(Items.EMERALD, 5 + random.nextIntBetweenInclusive(1, 5)), Optional.empty()),
                ingotTrade(tierOneIngots, new ItemCost(Items.EMERALD, 5 + random.nextIntBetweenInclusive(1, 5)), Optional.empty()),
                ingotTrade(tierOneIngots, new ItemCost(Items.EMERALD, 5 + random.nextIntBetweenInclusive(1, 5)), Optional.empty()),
                ingotTrade(tierOneIngots, new ItemCost(Items.EMERALD, 5 + random.nextIntBetweenInclusive(1, 5)), Optional.empty()),

                ingotTrade(tierTwoIngots, new ItemCost(Items.EMERALD, 20 + random.nextIntBetweenInclusive(1, 15)), Optional.empty()),
                ingotTrade(tierTwoIngots, new ItemCost(Items.EMERALD, 20 + random.nextIntBetweenInclusive(1, 15)), Optional.empty()),
                ingotTrade(tierTwoIngots, new ItemCost(Items.EMERALD, 20 + random.nextIntBetweenInclusive(1, 15)), Optional.empty()),

                ingotTrade(tierThreeIngots, new ItemCost(Items.EMERALD_BLOCK, 10 + random.nextIntBetweenInclusive(1, 5)), Optional.of(new ItemCost(Items.EMERALD, 10 + random.nextInt(1, 45)))),
                ingotTrade(tierThreeIngots, new ItemCost(Items.EMERALD_BLOCK, 10 + random.nextIntBetweenInclusive(1, 5)), Optional.of(new ItemCost(Items.EMERALD, 10 + random.nextInt(1, 45))))
        );


        event.getGenericTrades().addAll(additionalGenericTrades.stream().filter(Objects::nonNull).toList());

    }

    public static AdditionalWanderingTrades.SimpleTrade ingotTrade (List<ItemStack> ingotTier, ItemCost costA, Optional<ItemCost> costB) {

        return AdditionalWanderingTrades.SimpleTrade.of((trader, random) -> new MerchantOffer(
                costA,
                costB,
                new ItemStack(ingotTier.get(random.nextInt(0, ingotTier.size())).getItem()),
                16,
                0,
                .05f
        ));

    }

}
