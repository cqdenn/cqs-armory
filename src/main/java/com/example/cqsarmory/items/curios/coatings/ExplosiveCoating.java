package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.OnHitCoating;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.network.particles.FieryExplosionParticlesPacket;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.PacketDistributor;

public class ExplosiveCoating extends OnHitCoating {
    public ExplosiveCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
        var x = target.getX();
        var y = target.getY();
        var z = target.getZ();
        float damage = hitDamage / 5;
        var hitPos = target.position().add(0, 1.5, 0);
        Level level = attacker.level();
        float radius = 2 + (hitDamage / 50);
        for (LivingEntity livingentity : level.getEntitiesOfClass(LivingEntity.class, new AABB(hitPos, hitPos).inflate(radius))) {
            if (livingentity.isAlive() && livingentity.isPickable() && Utils.hasLineOfSight(level, hitPos, livingentity.getBoundingBox().getCenter(), true)) {
                DamageSources.applyDamage(livingentity, damage, new DamageSource(level.damageSources().damageTypes.getHolder(DamageTypes.EXPLOSION).get(), null, attacker));
            }
        }
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(target, new FieryExplosionParticlesPacket(target.getBoundingBox().getCenter(), radius));
        //MagicManager.spawnParticles(level, ParticleTypes.EXPLOSION, x, y, z, 3, 0.1, 0.1, 0.1, 0.3, true);
        level.playSound(null, target.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS);
        //MagicManager.spawnParticles(level, new BlastwaveParticleOptions(1, 1, 1, radius * 1.2f), x, y, z, 1, 0, 0, 0, 0, true);
    }
}
