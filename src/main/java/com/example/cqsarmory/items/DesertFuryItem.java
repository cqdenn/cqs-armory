package com.example.cqsarmory.items;

import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class DesertFuryItem extends MagicSwordItem {

    public DesertFuryItem(Tier tier, Item.Properties properties, SpellDataRegistryHolder[] spellDataRegistryHolder) {
        super(tier, properties, spellDataRegistryHolder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        return super.use(level, player, usedHand);
    }
}
