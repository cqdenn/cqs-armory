package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.items.ExtendedWeaponItem;
import com.example.cqsarmory.registry.ItemRegistry;
import com.example.cqsarmory.registry.Tags;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.example.cqsarmory.registry.ItemRegistry.WEAPONSETS;

public class CQItemTagsProvider extends IntrinsicHolderTagsProvider<Item> {


    protected CQItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, lookupProvider, item -> ResourceKey.create(Registries.ITEM, BuiltInRegistries.ITEM.getKey(item)), CqsArmory.MODID, existingFileHelper);
    }

    public static final TagKey<Item> swordTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", "swords"));
    public static final TagKey<Item> bowTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", "enchantable/bow"));
    public static final TagKey<Item> curioTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "booster"));
    public static final TagKey<Item> axeTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", "axes"));

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (ItemRegistry.Weaponset weaponset : WEAPONSETS) {
            for (DeferredItem weapon : weaponset) {
                if (weapon.get() instanceof SwordItem) {
                    tag(swordTag).add((Item) weapon.get());
                }
                else if (weapon.get() instanceof BowItem) {
                    tag(bowTag).add((Item) weapon.get());
                }
            }
        }
        for (DeferredHolder item : ItemRegistry.ITEMS.getEntries()) {
            if (item.get() instanceof SwordItem) {
                tag(swordTag).add((Item)item.get());
            }else if (item.get() instanceof CurioBaseItem) {
                tag(curioTag).add((Item)item.get());
            }
        }
        tag(swordTag).add(ItemRegistry.MJOLNIR.get());



        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.AMETHYST_SHARD);
        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.IRON_INGOT);
        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.GOLD_INGOT);
        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.COPPER_INGOT);
        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.OBSIDIAN);
        tag(Tags.Items.MATERIALS_POWER_ONE).add(Items.DIAMOND);

        tag(Tags.Items.MATERIALS_POWER_TWO).add(ItemRegistry.SCULK_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_TWO).add(ItemRegistry.LIVING_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_TWO).add(ItemRegistry.UMBRITE_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_TWO).add(ItemRegistry.SILVERSTEEL_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_TWO).add(ItemRegistry.DWARVEN_STEEL_WEAPONSET.ingot().get().asItem());

        tag(Tags.Items.MATERIALS_POWER_THREE).add(ItemRegistry.WITHERSTEEL_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_THREE).add(ItemRegistry.BLAZING_WEAPONSET.ingot().get().asItem());
        tag(Tags.Items.MATERIALS_POWER_THREE).add(Items.NETHERITE_INGOT);

        tag(Tags.Items.ARROWS).add(Items.ARROW);
        tag(Tags.Items.ARROWS).add(Items.TIPPED_ARROW);

    }
}
