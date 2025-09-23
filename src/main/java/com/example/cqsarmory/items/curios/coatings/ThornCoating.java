package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.OnHitCoating;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.root.RootEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ThornCoating extends OnHitCoating {
    public ThornCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
        float chance =(hitDamage / 400);
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
    }
}
