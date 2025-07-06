package com.example.cqsarmory.data;

import com.example.cqsarmory.registry.EntityDataAttachmentRegistry;
import io.redspace.ironsspellbooks.api.events.ChangeManaEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.neoforge.common.NeoForge;

public class AbilityData {
    public Mjolnir mjolnirData = new Mjolnir();
    public CosmicArk cosmicArk = new CosmicArk();
    public float currentShieldDamage;
    private float rage;
    public int combatEnd;

    public float getRage() {
        return rage;
    }

    public void setRage(float rage) {this.rage = rage;}

    public static boolean inCombat(Player player) {return get(player).combatEnd < player.tickCount;}

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
