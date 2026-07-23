package com.example.cqsarmory.items;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import io.redspace.skillcasting.data.skill.ISkillContainer;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ExtendedShieldItem extends ShieldItem {

    public ExtendedShieldItem(Properties pProperties, SkillData... spellDataRegistryHolders) {
        super(pProperties.component(ComponentRegistry.IMBUED_SPELL_CONTAINER, ISkillContainer.create(false, spellDataRegistryHolders)));
    }

    public static ItemAttributeModifiers createAttributes(int blockStrength, AttributeContainer[] attributes) {



        var builder = ItemAttributeModifiers.builder()
                .add(
                AttributeRegistry.BLOCK_STRENGTH,
                new AttributeModifier(
                        CqsArmory.BASE_BLOCK_STRENGTH_ID, blockStrength, AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.HAND
        );

        for (AttributeContainer holder : attributes) {
            builder.add(holder.attribute(), holder.createModifier("hand"), EquipmentSlotGroup.HAND);
        }
        return builder.build();
    }
}
