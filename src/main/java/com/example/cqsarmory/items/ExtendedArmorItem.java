package com.example.cqsarmory.items;

import com.example.cqsarmory.registry.AttributeRegistry;
import com.google.common.base.Suppliers;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Supplier;

public abstract class ExtendedArmorItem extends ArmorItem implements GeoItem {
    private final Supplier<ItemAttributeModifiers> defaultModifiers;

    public ExtendedArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties, AttributeContainer[] attributes) {
        super(material, type, properties);

        this.defaultModifiers = Suppliers.memoize(
                () -> {
                    int i = material.value().getDefense(type);
                    float f = material.value().toughness();
                    ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
                    EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(type.getSlot());
                    ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
                    builder.add(
                            Attributes.ARMOR, new AttributeModifier(resourcelocation, i, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup
                    );
                    builder.add(
                            Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourcelocation, f, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup
                    );
                    float f1 = material.value().knockbackResistance();
                    if (f1 > 0.0F) {
                        builder.add(
                                Attributes.KNOCKBACK_RESISTANCE,
                                new AttributeModifier(resourcelocation, f1, AttributeModifier.Operation.ADD_VALUE),
                                equipmentslotgroup
                        );
                    }

                    for (AttributeContainer holder : attributes) {
                        builder.add(holder.attribute(), holder.createModifier(type.getSlot().getName()), equipmentslotgroup);
                    }
                    return builder.build();
                }
        );
    }

    public static AttributeContainer[] genericArcherArmorAttributes (float arrowDamage, float drawSpeed, int momentumOnHit, AttributeContainer extraAttribute) {
        return new AttributeContainer[]{new AttributeContainer(BowAttributes.ARROW_DAMAGE, arrowDamage, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(BowAttributes.DRAW_SPEED, drawSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(AttributeRegistry.MOMENTUM_ON_HIT, momentumOnHit, AttributeModifier.Operation.ADD_VALUE), extraAttribute};
    }

    public static AttributeContainer[] genericArcherArmorAttributes (float arrowDamage, float drawSpeed, int momentumOnHit) {
        return new AttributeContainer[]{new AttributeContainer(BowAttributes.ARROW_DAMAGE, arrowDamage, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(BowAttributes.DRAW_SPEED, drawSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(AttributeRegistry.MOMENTUM_ON_HIT, momentumOnHit, AttributeModifier.Operation.ADD_VALUE)};
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.defaultModifiers.get();
    }


}
