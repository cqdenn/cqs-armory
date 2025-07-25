package com.example.cqsarmory.items.weapons;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.SummonedZombie;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class SummonSwordItem extends SwordItem {
    public SummonSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }


    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        if (attacker.level().isClientSide) {
            return;
        }
        //Pig Bob = EntityType.PIG.create(attacker.level());
        var zombies = attacker.level().getEntitiesOfClass(SummonedZombie.class, attacker.getBoundingBox().inflate(32), Zombie->Zombie.getTags().contains("sword") && Objects.equals(attacker, Zombie.getSummoner()));
        if (zombies.size() < 5){
            SummonedZombie Bob = new SummonedZombie(attacker.level(), attacker, true);
            Bob.moveTo(Utils.moveToRelativeGroundLevel(attacker.level(),
                    (target.position().add(Utils.getRandomVec3(3).add(0, 0.1, 0))), 4));
            Bob.addTag("sword");
            attacker.level().addFreshEntity(Bob);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var zombies = player.level().getEntitiesOfClass(SummonedZombie.class, player.getBoundingBox().inflate(32), Zombie->Zombie.getTags().contains("sword") && Objects.equals(player, Zombie.getSummoner()));
        zombies.forEach(this::explode);
        return super.use(level, player, usedHand);
    }

    private void explode (SummonedZombie zombie){
        var level = zombie.level();
        float damage = 10 + zombie.getHealth() * .5f;
        float explosionRadius = 3f * (1 + .5f * zombie.getHealth() / zombie.getMaxHealth());
        MagicManager.spawnParticles(level, ParticleHelper.BLOOD, zombie.getX(), zombie.getY() + .25f, zombie.getZ(), 100, .03, .4, .03, .4, true);
        MagicManager.spawnParticles(level, ParticleHelper.BLOOD, zombie.getX(), zombie.getY() + .25f, zombie.getZ(), 100, .03, .4, .03, .4, false);
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(SchoolRegistry.BLOOD.get().getTargetingColor(), explosionRadius), zombie.getX(), zombie.getBoundingBox().getCenter().y, zombie.getZ(), 1, 0, 0, 0, 0, true);
        var entities = level.getEntities(zombie, zombie.getBoundingBox().inflate(explosionRadius));
        for (Entity victim : entities) {
            double distanceSqr = victim.distanceToSqr(zombie.position());
            if (distanceSqr < explosionRadius * explosionRadius && Utils.hasLineOfSight(level, zombie.getBoundingBox().getCenter(), victim.getBoundingBox().getCenter(), true)) {
                float p = (float) (distanceSqr / (explosionRadius * explosionRadius));
                p = 1 - p * p * p;
                //IronsSpellbooks.LOGGER.debug("sacrifice spell damage: distance: {}, p: {}, damage: {}/{}", Math.sqrt(distanceSqr), p, damage * p, damage);
                DamageSources.applyDamage(victim, damage * p, SpellRegistry.SACRIFICE_SPELL.get().getDamageSource(zombie, zombie.getSummoner()));
            }
        }
        //CameraShakeManager.addCameraShake(new CameraShakeData(10, zombie.position(), 20));
        zombie.remove(Entity.RemovalReason.KILLED);
        level.playSound(null, zombie.blockPosition(), SoundRegistry.BLOOD_EXPLOSION.get(), SoundSource.PLAYERS, 3, Utils.random.nextIntBetweenInclusive(8, 12) * .1f);
    }

}
