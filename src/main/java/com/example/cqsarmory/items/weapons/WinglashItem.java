package com.example.cqsarmory.items.weapons;

import com.example.cqsarmory.items.ExtendedWeaponItem;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class WinglashItem extends ExtendedWeaponItem {

    public WinglashItem(Tier tier, Item.Properties properties, SkillData... spellDataRegistryHolder) {
        super(tier, properties, spellDataRegistryHolder);
    }
}
