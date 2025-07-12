package com.example.cqsarmory.data.entity.ability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MomentumOrb extends Entity {

    private Player creator = null;

    public MomentumOrb(EntityType<?> entityType, Level level, Player creator) {
        super(entityType, level);
        this.creator = creator;
    }

    public MomentumOrb(EntityType<MomentumOrb> momentumOrbEntityType, Level level) {
        super(momentumOrbEntityType, level);
    }

    public Player getCreator() { return this.creator; }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {

    }

    public final int waitTime = 200;

    @Override
    public void tick() {
        super.tick();

        if (tickCount == waitTime) {
            discard();
        }

    }
}
