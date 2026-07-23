package com.example.cqsarmory.items;

import com.example.cqsarmory.registry.ExtendedWeaponTier;
import com.example.cqsarmory.registry.WeaponPower;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;


public class ExtendedWeaponItem extends MagicSwordItem {

    public ExtendedWeaponItem(Tier tier, Item.Properties properties, SkillData... spellDataRegistryHolder) {
        super(tier, properties, spellDataRegistryHolder);
    }

    public static ItemAttributeModifiers createAttributes(ExtendedWeaponTier material, WeaponPower power, float attackDamage, float attackSpeed, AttributeContainer[] attributes) {


        var builder = ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, (double)((float)attackDamage + material.getAttackDamageBonus() + power.attackDamage()), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, (double)attackSpeed + material.getSpeed() + power.attackSpeed(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                );

        for (AttributeContainer holder : attributes) {
            builder.add(holder.attribute(), holder.createModifier(EquipmentSlot.MAINHAND.getName()), EquipmentSlotGroup.MAINHAND);
        }
        return builder.build();
    }
}

