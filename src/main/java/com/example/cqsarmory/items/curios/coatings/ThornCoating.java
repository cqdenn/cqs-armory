package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.OnBlockCoating;
import com.example.cqsarmory.items.curios.OnHitCoating;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.root.RootEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ThornCoating extends OnBlockCoating {
    public ThornCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnBlockEffect(Player blocker, Entity directEntity, Entity causingEntity, float hitDamage, int blockLength, boolean isPerfect) {
        if (isPerfect) {
            float damage = (float) blocker.getAttributeValue(Attributes.ATTACK_DAMAGE);
            DamageSource damageSource = blocker.level().damageSources().mobAttack(blocker);
            causingEntity.hurt(damageSource, damage);
        }
    }

    /*@Override
    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
        float chance = Math.min(0.1f + (hitDamage / 200), 0.35f); //caps at 50 damage
        if (Utils.random.nextFloat() <= chance) {
            Vec3 spawn = target.position();
            Level level = attacker.level();
            RootEntity rootEntity = new RootEntity(level, attacker);
            rootEntity.setDuration(40);
            rootEntity.setTarget(target);
            rootEntity.moveTo(spawn);
            level.addFreshEntity(rootEntity);
            target.stopRiding();
            target.startRiding(rootEntity, true);
        }
    }*/
}
