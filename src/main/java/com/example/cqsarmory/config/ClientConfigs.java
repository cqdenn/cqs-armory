package com.example.cqsarmory.config;

import com.example.cqsarmory.gui.overlays.MomentumBarOverlay;
import com.example.cqsarmory.gui.overlays.RageBarOverlay;
import io.redspace.ironsspellbooks.gui.overlays.ManaBarOverlay;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfigs {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.ConfigValue<RageBarOverlay.Anchor> RAGE_BAR_ANCHOR;
    public static final ModConfigSpec.ConfigValue<RageBarOverlay.Display> RAGE_BAR_DISPLAY;
    public static final ModConfigSpec.ConfigValue<Integer> RAGE_BAR_Y_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> RAGE_BAR_X_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> RAGE_TEXT_X_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> RAGE_TEXT_Y_OFFSET;
    public static final ModConfigSpec.ConfigValue<Boolean> RAGE_BAR_TEXT_VISIBLE;

    public static final ModConfigSpec.ConfigValue<MomentumBarOverlay.Anchor> MOMENTUM_BAR_ANCHOR;
    public static final ModConfigSpec.ConfigValue<MomentumBarOverlay.Display> MOMENTUM_BAR_DISPLAY;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_BAR_Y_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_BAR_X_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_TEXT_X_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_TEXT_Y_OFFSET;
    public static final ModConfigSpec.ConfigValue<Boolean> MOMENTUM_BAR_TEXT_VISIBLE;

    public static final ModConfigSpec SPEC;

    static {
        BUILDER.push("UI");
        BUILDER.push("RageBar");
        BUILDER.comment("By default (Contextual), the rage bar only appears when you are above 0 rage.");
        RAGE_BAR_DISPLAY = BUILDER.defineEnum("rageBarDisplay", RageBarOverlay.Display.Contextual);
        BUILDER.comment("Used to adjust rage bar's position (11 is one full hunger bar up).");
        RAGE_BAR_X_OFFSET = BUILDER.define("rageBarXOffset", 0);
        RAGE_BAR_Y_OFFSET = BUILDER.define("rageBarYOffset", 0);
        RAGE_BAR_TEXT_VISIBLE = BUILDER.define("rageBarTextVisible", true);
        RAGE_BAR_ANCHOR = BUILDER.defineEnum("rageBarAnchor", RageBarOverlay.Anchor.Center);
        RAGE_TEXT_X_OFFSET = BUILDER.define("rageTextXOffset", 0);
        RAGE_TEXT_Y_OFFSET = BUILDER.define("rageTextYOffset", 0);
        BUILDER.pop();

        BUILDER.push("MomentumBar");
        BUILDER.comment("By default (Contextual), the momentum bar only appears when you are above 0 momentum.");
        MOMENTUM_BAR_DISPLAY = BUILDER.defineEnum("momentumBarDisplay", MomentumBarOverlay.Display.Contextual);
        BUILDER.comment("Used to adjust momentum bar's position (11 is one full hunger bar up).");
        MOMENTUM_BAR_X_OFFSET = BUILDER.define("momentumBarXOffset", 0);
        MOMENTUM_BAR_Y_OFFSET = BUILDER.define("momentumBarYOffset", 0);
        MOMENTUM_BAR_TEXT_VISIBLE = BUILDER.define("momentumBarTextVisible", true);
        MOMENTUM_BAR_ANCHOR = BUILDER.defineEnum("momentumBarAnchor", MomentumBarOverlay.Anchor.Center);
        MOMENTUM_TEXT_X_OFFSET = BUILDER.define("momentumTextXOffset", 0);
        MOMENTUM_TEXT_Y_OFFSET = BUILDER.define("momentumTextYOffset", 0);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

}
