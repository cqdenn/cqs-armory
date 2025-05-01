package com.example.cqsarmory.items;

import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class VolcanoSwordItem extends SwordItem {

    public VolcanoSwordItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);

        target.setRemainingFireTicks(target.getRemainingFireTicks() + 80);
        particles(target.getEyePosition(), attacker.level());
    }

    public static void particles(Vec3 pos, Level level) {
        Random random = new Random();
        double x = pos.x;
        double y = pos.y;
        double z = pos.z;

        for (int i = 0; i < 240; i++) {
            float randDirection = random.nextFloat();
            float direction;
            if (randDirection <= 0.5f) {
                direction = -1f;
            } else {
                direction = 1f;
            }
            level.addParticle(ParticleHelper.FIRE, x, y + 1f, z, random.nextFloat() * direction, random.nextFloat(), random.nextFloat() * direction);
            level.addParticle(ParticleHelper.FIRE, x, y + 1f, z, random.nextFloat() * -direction, random.nextFloat(), random.nextFloat() * direction);
            level.addParticle(ParticleHelper.FIRE, x, y + 1f, z, random.nextFloat() * direction, random.nextFloat(), random.nextFloat() * -direction);
        }
    }
}
