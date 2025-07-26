package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ArmorMaterialsRegistry {
    private static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }

    public static DeferredHolder<ArmorMaterial, ArmorMaterial> HUNTER = register("hunter",
            makeArmorMap(2, 6, 5, 2),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(Tags.Items.MATERIALS_POWER_ONE),
            0,
            0);

    public static DeferredHolder<ArmorMaterial, ArmorMaterial> TRACKER = register("tracker",
            makeArmorMap(3, 8, 6, 3),
            15,
            SoundEvents.ARMOR_EQUIP_CHAIN,
            () -> Ingredient.of(Tags.Items.MATERIALS_POWER_TWO),
            1,
            0);

    public static DeferredHolder<ArmorMaterial, ArmorMaterial> MARKSMAN = register("marksman",
            makeArmorMap(4, 9, 7, 4),
            20,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Tags.Items.MATERIALS_POWER_THREE),
            2,
            0);

    public static DeferredHolder<ArmorMaterial, ArmorMaterial> WARRIOR = register("warrior",
            makeArmorMap(3, 8, 6, 3),
            15,
            SoundEvents.ARMOR_EQUIP_CHAIN,
            () -> Ingredient.of(Tags.Items.MATERIALS_POWER_ONE),
            2,
            0.05f);

    public static DeferredHolder<ArmorMaterial, ArmorMaterial> SOLDIER = register("soldier",
            makeArmorMap(4, 9, 7, 4),
            15,
            SoundEvents.ARMOR_EQUIP_IRON,
            () -> Ingredient.of(Tags.Items.MATERIALS_POWER_TWO),
            3,
            0.1f);

    public static DeferredHolder<ArmorMaterial, ArmorMaterial> CHAMPION = register("champion",
            makeArmorMap(5, 10, 8, 5),
            20,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> Ingredient.of(Tags.Items.MATERIALS_POWER_THREE),
            4,
            0.15f);


    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(
            String name,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            Supplier<Ingredient> repairIngredient,
            float toughness,
            float knockbackResistance
    ) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(CqsArmory.id(name)));
        return ARMOR_MATERIALS.register(name, ()-> new ArmorMaterial(defense, enchantmentValue, equipSound, repairIngredient, list, toughness, knockbackResistance));
    }

    static public EnumMap<ArmorItem.Type, Integer> makeArmorMap(int helmet, int chestplate, int leggings, int boots) {
        return Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266655_) -> {
            p_266655_.put(ArmorItem.Type.BOOTS, boots);
            p_266655_.put(ArmorItem.Type.LEGGINGS, leggings);
            p_266655_.put(ArmorItem.Type.CHESTPLATE, chestplate);
            p_266655_.put(ArmorItem.Type.HELMET, helmet);
        });
    }
}
