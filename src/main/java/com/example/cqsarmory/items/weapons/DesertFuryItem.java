package com.example.cqsarmory.items.weapons;

import com.example.cqsarmory.items.ExtendedWeaponItem;
import io.redspace.skillcasting.data.skill.SkillData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class DesertFuryItem extends ExtendedWeaponItem {

    public DesertFuryItem(Tier tier, Item.Properties properties, SkillData... spellDataRegistryHolder) {
        super(tier, properties, spellDataRegistryHolder);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        return super.use(level, player, usedHand);
    }
}
