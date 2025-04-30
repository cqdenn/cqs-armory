package com.example.cqsarmory.items;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.mobs.SummonedZombie;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class FangSwordItem extends SwordItem {
    public FangSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            EvokerFangs evokerFangs = new EvokerFangs(attacker.level(), attacker.getX(), attacker.getY(), attacker.getZ(), random.nextFloat(), 0, attacker);
            evokerFangs.moveTo(Utils.moveToRelativeGroundLevel(attacker.level(),
                    (attacker.position().add(Utils.getRandomVec3(3).add(0, 0.1, 0))), 4));
            evokerFangs.addTag("fang_sword");
            attacker.level().addFreshEntity(evokerFangs);
        }
    }
}
