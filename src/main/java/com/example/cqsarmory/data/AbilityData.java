package com.example.cqsarmory.data;

import com.example.cqsarmory.registry.EntityDataAttachmentRegistry;
import io.redspace.ironsspellbooks.api.events.ChangeManaEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.neoforge.common.NeoForge;

public class AbilityData {
    public Mjolnir mjolnirData = new Mjolnir();
    public CosmicArk cosmicArk = new CosmicArk();
    public MomentumOrbEffects momentumOrbEffects = new MomentumOrbEffects();
    public float currentShieldDamage;
    private float rage;
    private float momentum;
    public int combatEndRage;
    public int combatEndMomentum;
    public boolean focused;
    public float manaSpentSinceLastAOE;
    public int fireAspectCooldownEnd;
    public int quiverArrowCount;
    public float bowVelocity;
    public int poisonStacks;

    public float getRage() {
        return rage;
    }

    public void setRage(float rage) {this.rage = rage;}

    public float getMomentum() {
        return momentum;
    }

    public void setMomentum(float momentum) {this.momentum = momentum;}

    public static boolean inCombatRage(Player player) {return get(player).combatEndRage > player.tickCount;}

    public static boolean inCombatMomentum(Player player) {return get(player).combatEndMomentum > player.tickCount;}

    public class MomentumOrbEffects {
        public int speedEnd;
        public int speedStacks;

        public int arrowDamageEnd;
        public int arrowDamageStacks;
    }

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
