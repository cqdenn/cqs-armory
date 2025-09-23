package com.example.cqsarmory.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfigs {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.ConfigValue<Boolean> DISABLE_IFRAMES;

    static {
        BUILDER.push("Combat");
        BUILDER.comment("Disables invulnerability ticks on damage taken. Default: false");
        DISABLE_IFRAMES = BUILDER.worldRestart().define("disableIFrames", false);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
