package com.example.cqsarmory.data.effects;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

public class NonCurableEffect extends MobEffect {
    public NonCurableEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public NonCurableEffect(MobEffectCategory category, int color, ParticleOptions particle) {
        super(category, color, particle);
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        return;
    }
}
