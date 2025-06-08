package com.example.cqsarmory.data.enchants;

import com.example.cqsarmory.CqsArmory;
import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import software.bernie.geckolib.loading.math.Operator;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class RottingCurse extends LootModifier {
    public static final Supplier<MapCodec<RottingCurse>> CODEC = Suppliers.memoize(()
            ->  RecordCodecBuilder.mapCodec(builder -> codecStart(builder).apply(builder,RottingCurse::new)));

    protected RottingCurse(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Holder.Reference<Enchantment> rottingHolder = context.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "rotting_curse")));
        if (context.getParamOrNull(LootContextParams.ATTACKING_ENTITY) instanceof Player player && (player.getMainHandItem().getEnchantmentLevel(rottingHolder) > 0)) {
            for (int i = 0; i < generatedLoot.size(); i++) {
               if (generatedLoot.get(i).getFoodProperties((LivingEntity) context.getParamOrNull(LootContextParams.ATTACKING_ENTITY)) != null) {
                   generatedLoot.remove(i);
                   generatedLoot.add(i, Items.ROTTEN_FLESH.getDefaultInstance());
               }
           }
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
