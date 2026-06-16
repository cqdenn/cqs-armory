package com.example.cqsarmory.data.entity.ability;

import com.example.cqsarmory.mixin.ChainLightningMixin;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.entity.spells.ChainLightning;
import io.redspace.ironsspellbooks.particle.ZapParticleOption;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CQChainLightning extends ChainLightning {
    public CQChainLightning(Level level, Entity owner, Entity initialVictim, Vec3 zapStartPos) {
        super(level, owner, initialVictim);
        this.zapStartPos = zapStartPos;
    }

    public CQChainLightning(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    Vec3 zapStartPos;

    public List<Entity> getAllVictims() {
        return ((ChainLightningMixin) this).cqs_armory$getAllVictims();
    }

    public Entity getinitialVictim() {
        return ((ChainLightningMixin) this).cqs_armory$getInitialVictim();
    }

    public List<Entity> getLastVictims() {
        return ((ChainLightningMixin) this).cqs_armory$getLastVictims();
    }

    public int getHits() {
        return ((ChainLightningMixin) this).cqs_armory$getHits();
    }

    @Override
    public void tick() {
        abstractMagicProjectileTick();
        int f = tickCount - 1;
        if (!this.level().isClientSide && f % 6 == 0) {
            if (f == 0 && !getAllVictims().contains(getinitialVictim())) {
                //First time zap
                doHurt(getinitialVictim());
                if (getOwner() != null) {
                    Vec3 start = this.zapStartPos;
                    var dest = getinitialVictim().position().add(0, getinitialVictim().getBbHeight() / 2, 0);
                    ((ServerLevel) level()).sendParticles(new ZapParticleOption(dest), start.x, start.y, start.z, 1, 0, 0, 0, 0);
                }
                level().playSound(null, this.blockPosition(), SoundRegistry.CHAIN_LIGHTNING_CHAIN.get(), SoundSource.PLAYERS);

            } else {
                int j = getLastVictims().size();
                AtomicInteger zapsThisWave = new AtomicInteger();
                //cannot be enhanced for
                for (int i = 0; i < j; i++) {
                    var entity = getLastVictims().get(i);
                    var entities = level().getEntities(entity, entity.getBoundingBox().inflate(range), this::canHitEntity);
                    entities.sort(Comparator.comparingDouble(o -> o.distanceToSqr(entity)));
                    entities.forEach((victim) -> {
                        if (zapsThisWave.get() < maxConnectionsPerWave && getHits() < maxConnections && victim.distanceToSqr(entity) < range * range && Utils.hasLineOfSight(level(), entity.getEyePosition(), victim.getEyePosition(), true)) {
                            doHurt(victim);
                            victim.playSound(SoundRegistry.CHAIN_LIGHTNING_CHAIN.get(), 2, 1);
                            zapsThisWave.getAndIncrement();
                            Vec3 start = new Vec3(entity.xOld, entity.yOld, entity.zOld).add(0, entity.getBbHeight() / 2, 0);
                            var dest = victim.position().add(0, victim.getBbHeight() / 2, 0);
                            ((ServerLevel) level()).sendParticles(new ZapParticleOption(dest), start.x, start.y, start.z, 1, 0, 0, 0, 0);
                        }
                    });
                }
                getLastVictims().removeAll(getAllVictims());
                if (getLastVictims().isEmpty()) {
                    // no more procs, remove self
                    discard();
                }
            }
            getAllVictims().addAll(getLastVictims());
        }
    }

    public void abstractMagicProjectileTick() {
        projectileTick();
        if (tickCount == 1) {
            // prevent first-tick flicker due to deltaMoveOld being "uninitialized" on our first tick
            deltaMovementOld = getDeltaMovement();
        }
        if (tickCount > EXPIRE_TIME) {
            discard();
            return;
        }
        if (level().isClientSide) {
            trailParticles();
        }
        handleEntityHoming();
        handleCursorHoming();
        handleHitDetection();
        travel();
        deltaMovementOld = getDeltaMovement();
        rotateWithMotion();
    }

    public void projectileTick() {
        if (!this.hasBeenShot) {
            this.gameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner());
            this.hasBeenShot = true;
        }

        if (!this.leftOwner) {
            this.leftOwner = this.checkLeftOwner();
        }

        this.baseTick();
    }
}
