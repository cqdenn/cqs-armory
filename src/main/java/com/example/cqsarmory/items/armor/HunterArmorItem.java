package com.example.cqsarmory.items.armor;

import com.example.cqsarmory.items.ExtendedArmorItem;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorMaterial;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;

import javax.annotation.Nullable;

public class HunterArmorItem extends ExtendedArmorItem {
    public HunterArmorItem(Type type, Properties properties, AttributeContainer extraAttribute) {
        super(ArmorMaterialsRegistry.HUNTER, type, properties, genericArcherArmorAttributes(0.1f, 0.05f, 1, 0, extraAttribute));
    }
    public HunterArmorItem(Type type, Properties properties) {
        super(ArmorMaterialsRegistry.HUNTER, type, properties, genericArcherArmorAttributes(0.1f, 0.05f, 1, 0));
    }


}
