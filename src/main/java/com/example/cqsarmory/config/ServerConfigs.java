package com.example.cqsarmory.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfigs {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<Boolean> DISABLE_IFRAMES;
    public static final ModConfigSpec.ConfigValue<Boolean> DISABLE_RAGE;
    public static final ModConfigSpec.ConfigValue<Boolean> DISABLE_MOMENTUM;
    public static final ModConfigSpec.ConfigValue<Boolean> DISABLE_MAGE_AOE;
    public static final ModConfigSpec.ConfigValue<Boolean> DISABLE_SHIELD_CHANGES;

    static {
        BUILDER.push("Combat");

        BUILDER.comment("Disables invulnerability ticks on damage taken. Default: false");
        DISABLE_IFRAMES = BUILDER.worldRestart().define("disableIFrames", false);

        BUILDER.comment("Disables the rage combat system. Default: false");
        DISABLE_RAGE = BUILDER.worldRestart().define("disableRage", false);

        BUILDER.comment("Disables the momentum combat system. Default: false");
        DISABLE_MOMENTUM = BUILDER.worldRestart().define("disableMomentum", false);

        BUILDER.comment("Disables the mage aoe combat system. Default: false");
        DISABLE_MAGE_AOE = BUILDER.worldRestart().define("disableMageAOE", false);

        BUILDER.comment("Disables the shield block strength and shield disabling system. Default: false");
        DISABLE_SHIELD_CHANGES = BUILDER.worldRestart().define("disableShieldChanges", false);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
