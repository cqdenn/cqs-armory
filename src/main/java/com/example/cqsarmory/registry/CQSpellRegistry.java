package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.spells.*;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.skillcasting.data.AbstractSkill;
import io.redspace.skillcasting.registry.SkillcastingRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CQSpellRegistry {
    public static final DeferredRegister<AbstractSkill> SPELLS = DeferredRegister.create(SkillcastingRegistries.SKILL_REGISTRY_KEY, CqsArmory.MODID);

    public static void register(IEventBus eventBus) {
        SPELLS.register(eventBus);
    }

    private static <T extends AbstractSpell> DeferredHolder<AbstractSkill, T> registerSpell(String name, Supplier<T> skill) {
        return SPELLS.register(name, skill);
    }

    public static final DeferredHolder<AbstractSkill, SpinSpell> SPIN_SPELL = registerSpell("spin", SpinSpell::new);
    //public static final DeferredHolder<AbstractSkill, IceSmashSpell> ICE_SMASH_SPELL = registerSpell("ice_smash", IceSmashSpell::new); // WIP
    public static final DeferredHolder<AbstractSkill, StunSpell> STUN_SPELL = registerSpell("stun", StunSpell::new);
    public static final DeferredHolder<AbstractSkill, RuptureSpell> RUPTURE_SPELL = registerSpell("rupture", RuptureSpell::new);
    //public static final DeferredHolder<AbstractSkill, BerserkSpell> BERSERK_SPELL = registerSpell("berserk", BerserkSpell::new);
    //public static final DeferredHolder<AbstractSkill, ReapSpell> REAP_SPELL = registerSpell("reap", ReapSpell::new);
    //public static final DeferredHolder<AbstractSkill, WreckingBallSpell> WRECKING_BALL_SPELL = registerSpell("wrecking_ball", WreckingBallSpell::new);
    public static final DeferredHolder<AbstractSkill, RiposteSpell> RIPOSTE_SPELL = registerSpell("riposte", RiposteSpell::new);
    public static final DeferredHolder<AbstractSkill, SkewerSpell> SKEWER_SPELL = registerSpell("skewer", SkewerSpell::new);
    //public static final DeferredHolder<AbstractSkill, UppercutSpell> UPPERCUT_SPELL = registerSpell("uppercut", UppercutSpell::new);
    public static final DeferredHolder<AbstractSkill, TauntSpell> TAUNT_SPELL = registerSpell("taunt", TauntSpell::new);
    //public static final DeferredHolder<AbstractSkill, SkyStrikeSpell> SKY_STRIKE_SPELL = registerSpell("sky_strike", SkyStrikeSpell::new);
    public static final DeferredHolder<AbstractSkill, ShieldBashSpell> SHIELD_BASH_SPELL = registerSpell("shield_bash", ShieldBashSpell::new);
    public static final DeferredHolder<AbstractSkill, PiercingArrowSpell> PIERCING_ARROW_SPELL = registerSpell("piercing_arrow", PiercingArrowSpell::new);
    public static final DeferredHolder<AbstractSkill, RapidFireSpell> RAPID_FIRE_SPELL = registerSpell("rapid_fire", RapidFireSpell::new);
    public static final DeferredHolder<AbstractSkill, BarrageSpell> BARRAGE_SPELL = registerSpell("barrage", BarrageSpell::new);
    //public static final DeferredHolder<AbstractSkill, HuntersMarkSpell> HUNTERS_MARK_SPELL = registerSpell("hunters_mark", HuntersMarkSpell::new);
    public static final DeferredHolder<AbstractSkill, IceArrowSpell> ICE_ARROW_SPELL = registerSpell("ice_arrow", IceArrowSpell::new);
    //public static final DeferredHolder<AbstractSkill, GravitySnareSpell> GRAVITY_SNARE_SPELL = registerSpell("gravity_snare", GravitySnareSpell::new);
    public static final DeferredHolder<AbstractSkill, InfiniteMagicSpell> INFINITE_MAGIC_SPELL = registerSpell("infinite_magic", InfiniteMagicSpell::new);
    public static final DeferredHolder<AbstractSkill, RefillSpell> REFILL_SPELL = registerSpell("refill", RefillSpell::new);
    public static final DeferredHolder<AbstractSkill, ConsumeBleedSpell> CONSUME_BLEED_SPELL = registerSpell("consume_bleed", ConsumeBleedSpell::new);
    public static final DeferredHolder<AbstractSkill, WindBurstSpell> WIND_BURST_SPELL = registerSpell("wind_burst", WindBurstSpell::new);
    public static final DeferredHolder<AbstractSkill, ChainWhipSpell> CHAIN_WHIP_SPELL = registerSpell("chain_whip", ChainWhipSpell::new);
    public static final DeferredHolder<AbstractSkill, WrathEruptionSpell> WRATH_ERUPTION_SPELL = registerSpell("wrath_eruption", WrathEruptionSpell::new);
    public static final DeferredHolder<AbstractSkill, ExecuteSpell> EXECUTE_SPELL = registerSpell("execute", ExecuteSpell::new);
    public static final DeferredHolder<AbstractSkill, PerfectTechniqueSpell> PERFECT_TECHNIQUE_SPELL = registerSpell("perfect_technique", PerfectTechniqueSpell::new);
    public static final DeferredHolder<AbstractSkill, FlankStepSpell> FLANK_STEP_SPELL = registerSpell("flank_step", FlankStepSpell::new);
    //public static final DeferredHolder<AbstractSkill, FocusSpell> FOCUS_SPELL = registerSpell("focus", FocusSpell::new);
    public static final DeferredHolder<AbstractSkill, LightningRodSpell> LIGHTNING_ROD_SPELL = registerSpell("lightning_rod", LightningRodSpell::new);
}
