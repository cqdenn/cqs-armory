package com.example.cqsarmory.items;

import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class WinglashItem extends ExtendedWeaponItem {

    public WinglashItem(Tier tier, Item.Properties properties, SpellDataRegistryHolder[] spellDataRegistryHolder) {
        super(tier, properties, spellDataRegistryHolder);
    }
}
