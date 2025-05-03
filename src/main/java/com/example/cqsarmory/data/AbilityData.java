package com.example.cqsarmory.data;

import com.example.cqsarmory.registry.EntityDataAttachmentRegistry;
import net.minecraft.world.entity.Entity;

public class AbilityData {
    public Mjolnir mjolnirData = new Mjolnir();
    public CosmicArk cosmicArk = new CosmicArk();

    public class Mjolnir {
        public boolean doDamage;
        public double speed;
    }

    public class CosmicArk {
        public int abilityStacks;
    }

    public static AbilityData get (Entity entity) {
        return entity.getData(EntityDataAttachmentRegistry.ABILITY_DATA);
    }
}
