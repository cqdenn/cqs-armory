package com.example.cqsarmory.mixin;

import io.redspace.ironsspellbooks.entity.spells.ChainLightning;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ChainLightning.class)
public interface ChainLightningMixin {

    @Accessor("allVictims")
    List<Entity> cqs_armory$getAllVictims();

    @Accessor("lastVictims")
    List<Entity> cqs_armory$getLastVictims();

    @Accessor("initialVictim")
    Entity cqs_armory$getInitialVictim();

    @Accessor("hits")
    int cqs_armory$getHits();

}
