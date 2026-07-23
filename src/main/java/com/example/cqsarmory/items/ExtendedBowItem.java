package com.example.cqsarmory.items;

import com.example.cqsarmory.registry.ExtendedWeaponTier;
import com.example.cqsarmory.registry.WeaponPower;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import io.redspace.skillcasting.data.skill.ISkillContainer;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import static io.redspace.bowattributes.BowAttributeLib.BASE_ARROW_DAMAGE_ID;
import static io.redspace.bowattributes.BowAttributeLib.BASE_DRAWSPEED_ID;

public class ExtendedBowItem extends BowItem {

    public ExtendedBowItem(Properties pProperties, SkillData... spellDataRegistryHolders) {
        super(pProperties.component(ComponentRegistry.IMBUED_SPELL_CONTAINER, ISkillContainer.create(false, spellDataRegistryHolders)));
    }

    public static ItemAttributeModifiers createAttributes(ExtendedWeaponTier material, WeaponPower power, float arrowDamage, float drawSpeed, AttributeContainer[] attributes) {



        var builder = ItemAttributeModifiers.builder()
                .add(
                        BowAttributes.ARROW_DAMAGE,
                        new AttributeModifier(
                                BASE_ARROW_DAMAGE_ID, (double)((float)arrowDamage + material.getAttackDamageBonus() + power.attackDamage()), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.HAND
                )
                .add(
                        BowAttributes.DRAW_SPEED,
                        new AttributeModifier(BASE_DRAWSPEED_ID, (double)drawSpeed + material.getDrawSpeed() + power.drawspeed(), AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.HAND
                );

        for (AttributeContainer holder : attributes) {
            builder.add(holder.attribute(), holder.createModifier("hand"), EquipmentSlotGroup.HAND);
        }
        return builder.build();
    }
}
