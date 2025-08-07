package com.example.cqsarmory.items;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.ArmorMaterialsRegistry;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.google.common.base.Suppliers;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;
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

    public static AttributeContainer[] genericArcherArmorAttributes (float arrowDamage, float drawSpeed, int momentumOnHit, float dodgeChance, float moveSpeed, AttributeContainer extraAttribute) {
        return new AttributeContainer[]{new AttributeContainer(BowAttributes.ARROW_DAMAGE, arrowDamage, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(BowAttributes.DRAW_SPEED, drawSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(AttributeRegistry.MOMENTUM_ON_HIT, momentumOnHit, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(AttributeRegistry.DODGE_CHANCE, dodgeChance, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(Attributes.MOVEMENT_SPEED, moveSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), extraAttribute};
    }

    public static AttributeContainer[] genericArcherArmorAttributes (float arrowDamage, float drawSpeed, int momentumOnHit, float dodgeChance, float moveSpeed) {
        return new AttributeContainer[]{new AttributeContainer(BowAttributes.ARROW_DAMAGE, arrowDamage, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(BowAttributes.DRAW_SPEED, drawSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(AttributeRegistry.MOMENTUM_ON_HIT, momentumOnHit, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(AttributeRegistry.DODGE_CHANCE, dodgeChance, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(Attributes.MOVEMENT_SPEED, moveSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)};
    }

    public static AttributeContainer[] genericMeleeArmorAttributes (float attackDamage, float attackSpeed, int maxRage, float rageDamage, float maxHealth, float blockStrength, AttributeContainer extraAttribute) {
        return new AttributeContainer[]{new AttributeContainer(Attributes.ATTACK_DAMAGE, attackDamage, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(Attributes.ATTACK_SPEED, attackSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(AttributeRegistry.MAX_RAGE, maxRage, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(AttributeRegistry.RAGE_DAMAGE, rageDamage, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(Attributes.MAX_HEALTH, maxHealth, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(AttributeRegistry.BLOCK_STRENGTH, blockStrength, AttributeModifier.Operation.ADD_VALUE), extraAttribute};
    }

    public static AttributeContainer[] genericMeleeArmorAttributes (float attackDamage, float attackSpeed, int maxRage, float rageDamage, float maxHealth, float blockStrength) {
        return new AttributeContainer[]{new AttributeContainer(Attributes.ATTACK_DAMAGE, attackDamage, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(Attributes.ATTACK_SPEED, attackSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(AttributeRegistry.MAX_RAGE, maxRage, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(AttributeRegistry.RAGE_DAMAGE, rageDamage, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(Attributes.MAX_HEALTH, maxHealth, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(AttributeRegistry.BLOCK_STRENGTH, blockStrength, AttributeModifier.Operation.ADD_VALUE)};
    }

    public static AttributeContainer[] genericMageArmorAttributes (float spellPower, float manaRegen, int mana, float castTimeReduction, float cdReduction, AttributeContainer extraAttribute) {
        return new AttributeContainer[]{new AttributeContainer(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER, spellPower, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MANA_REGEN, manaRegen, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA, mana, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.COOLDOWN_REDUCTION, cdReduction, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.CAST_TIME_REDUCTION, castTimeReduction, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), extraAttribute};
    }

    public static AttributeContainer[] genericMageArmorAttributes (float spellPower, float manaRegen, int mana, float castTimeReduction, float cdReduction) {
        return new AttributeContainer[]{new AttributeContainer(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER, spellPower, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MANA_REGEN, manaRegen, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA, mana, AttributeModifier.Operation.ADD_VALUE), new AttributeContainer(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.COOLDOWN_REDUCTION, cdReduction, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(io.redspace.ironsspellbooks.api.registry.AttributeRegistry.CAST_TIME_REDUCTION, castTimeReduction, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)};
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

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        String layerNum = slot == EquipmentSlot.LEGS ? "2" : "1";
        switch (((ArmorItem) stack.getItem()).getMaterial().getRegisteredName().split(":")[1]) {
            case "hunter", "scout" -> {
                return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/models/armor/archer_power_1_layer_" + layerNum + ".png");
            }
            case "tracker", "ranger" -> {
                return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/models/armor/archer_power_2_layer_" + layerNum + ".png");
            }
            case "marksman", "skirmisher" -> {
                return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/models/armor/archer_power_3_layer_" + layerNum + ".png");
            }
            case "warrior", "rampart", "seer", "apprentice" -> {
                return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/models/armor/melee_power_1_layer_" + layerNum + ".png");
            }
            case "soldier", "bastion", "mystic", "magus" -> {
                return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/models/armor/melee_power_2_layer_" + layerNum + ".png");
            }
            case "champion", "juggernaut", "archmage", "sage" -> {
                return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/models/armor/melee_power_3_layer_" + layerNum + ".png");
            }
            default -> {
                return ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/models/armor/melee_power_1_layer_" + layerNum + ".png");
            }
        }
    }
}
