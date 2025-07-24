package com.example.cqsarmory.items;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.ExtendedWeaponTier;
import com.example.cqsarmory.registry.WeaponPower;
import io.redspace.bowattributes.registry.BowAttributes;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.Arrays;
import java.util.List;

public class ExtendedShieldItem extends ShieldItem implements IPresetSpellContainer {

    List<SpellData> spellData = null;
    SpellDataRegistryHolder[] spellDataRegistryHolders;

    public ExtendedShieldItem(Properties pProperties, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(pProperties);
        this.spellDataRegistryHolders = spellDataRegistryHolders;

    }

    public List<SpellData> getSpells() {
        if (spellData == null) {
            spellData = Arrays.stream(spellDataRegistryHolders).map(SpellDataRegistryHolder::getSpellData).toList();
            spellDataRegistryHolders = null;
        }
        return spellData;
    }


    public void initializeSpellContainer(ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        if (!ISpellContainer.isSpellContainer(itemStack)) {
            var spells = getSpells();
            var spellContainer = ISpellContainer.create(spells.size(), true, false).mutableCopy();
            spells.forEach(spellData -> spellContainer.addSpell(spellData.getSpell(), spellData.getLevel(), true));
            itemStack.set(ComponentRegistry.SPELL_CONTAINER, spellContainer.toImmutable());
        }
    }


    public static ItemAttributeModifiers createAttributes(int blockStrength, AttributeContainer[] attributes) {



        var builder = ItemAttributeModifiers.builder()
                .add(
                AttributeRegistry.BLOCK_STRENGTH,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "base_block_strength"), blockStrength, AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.HAND
        );

        for (AttributeContainer holder : attributes) {
            builder.add(holder.attribute(), holder.createModifier(EquipmentSlotGroup.HAND.name()), EquipmentSlotGroup.HAND);
        }
        return builder.build();
    }
}
