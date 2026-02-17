package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.spells.*;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CQSpellRegistry {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        SPELLS.register(eventBus);
    }

    public static Supplier<AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    public static final Supplier<AbstractSpell> SPIN_SPELL = registerSpell(new SpinSpell());
    public static final Supplier<AbstractSpell> ICE_SMASH_SPELL = registerSpell(new IceSmashSpell()); // WIP
    public static final Supplier<AbstractSpell> STUN_SPELL = registerSpell(new StunSpell());
    public static final Supplier<AbstractSpell> RUPTURE_SPELL = registerSpell(new RuptureSpell());
    public static final Supplier<AbstractSpell> BERSERK_SPELL = registerSpell(new BerserkSpell());
    public static final Supplier<AbstractSpell> REAP_SPELL = registerSpell(new ReapSpell());
    public static final Supplier<AbstractSpell> WRECKING_BALL_SPELL = registerSpell(new WreckingBallSpell());
    public static final Supplier<AbstractSpell> RIPOSTE_SPELL = registerSpell(new RiposteSpell());
    public static final Supplier<AbstractSpell> SKEWER_SPELL = registerSpell(new SkewerSpell());
    public static final Supplier<AbstractSpell> UPPERCUT_SPELL = registerSpell(new UppercutSpell());
    public static final Supplier<AbstractSpell> TAUNT_SPELL = registerSpell(new TauntSpell());
    public static final Supplier<AbstractSpell> SKY_STRIKE_SPELL = registerSpell(new SkyStrikeSpell());
    public static final Supplier<AbstractSpell> SHIELD_BASH_SPELL = registerSpell(new ShieldBashSpell());
    public static final Supplier<AbstractSpell> PIERCING_ARROW_SPELL = registerSpell(new PiercingArrowSpell());
    public static final Supplier<AbstractSpell> RAPID_FIRE_SPELL = registerSpell(new RapidFireSpell());
    public static final Supplier<AbstractSpell> BARRAGE_SPELL = registerSpell(new BarrageSpell());
    public static final Supplier<AbstractSpell> HUNTERS_MARK_SPELL = registerSpell(new HuntersMarkSpell());
    public static final Supplier<AbstractSpell> ICE_ARROW_SPELL = registerSpell(new IceArrowSpell());
    public static final Supplier<AbstractSpell> GRAVITY_SNARE_SPELL = registerSpell(new GravitySnareSpell());
    public static final Supplier<AbstractSpell> INFINITE_MAGIC_SPELL = registerSpell(new InfiniteMagicSpell());
    public static final Supplier<AbstractSpell> REFILL_SPELL = registerSpell(new RefillSpell());
    public static final Supplier<AbstractSpell> CONSUME_BLEED_SPELL = registerSpell(new ConsumeBleedSpell());
    public static final Supplier<AbstractSpell> WIND_BURST_SPELL = registerSpell(new WindBurstSpell());
    public static final Supplier<AbstractSpell> CHAIN_WHIP_SPELL = registerSpell(new ChainWhipSpell());
}
