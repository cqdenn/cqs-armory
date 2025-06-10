package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.spells.IceSmashSpell;
import com.example.cqsarmory.spells.RuptureSpell;
import com.example.cqsarmory.spells.SpinSpell;
import com.example.cqsarmory.spells.StunSpell;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
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


}
