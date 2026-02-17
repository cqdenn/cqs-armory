package com.example.cqsarmory.data.effects;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CQMobEffectInstance extends MobEffectInstance {
    public CQMobEffectInstance(Holder<MobEffect> effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon, Entity owner, boolean triggersEvent) {
        super(effect, duration, amplifier, ambient, visible, showIcon);
        setOwner(owner);
        setTriggersEvent(triggersEvent);
    }

    private Entity owner;

    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public Entity getOwner() {
        return owner;
    }

    private boolean triggersEvent; //should be true for all applications of bleed other than the merging of stacks in the event.

    public void setTriggersEvent(boolean triggersEvent) {
        this.triggersEvent = triggersEvent;
    }

    public boolean getTriggersEvent() {
        return triggersEvent;
    }
}
