package com.example.cqsarmory.config;

import com.example.cqsarmory.gui.overlays.*;
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

    public static final ModConfigSpec.ConfigValue<QuiverArrowOverlay.Anchor> QUIVER_ARROWS_ANCHOR;
    public static final ModConfigSpec.ConfigValue<QuiverArrowOverlay.Display> QUIVER_ARROWS_DISPLAY;
    public static final ModConfigSpec.ConfigValue<Integer> QUIVER_ARROWS_X_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> QUIVER_ARROWS_Y_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> QUIVER_ARROWS_TEXT_X_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> QUIVER_ARROWS_TEXT_Y_OFFSET;
    public static final ModConfigSpec.ConfigValue<Boolean> QUIVER_ARROWS_TEXT_VISIBLE;

    public static final ModConfigSpec.ConfigValue<MomentumSpeedOverlay.Anchor> MOMENTUM_SPEED_ANCHOR;
    public static final ModConfigSpec.ConfigValue<MomentumSpeedOverlay.Display> MOMENTUM_SPEED_DISPLAY;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_SPEED_Y_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_SPEED_X_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_SPEED_TEXT_X_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_SPEED_TEXT_Y_OFFSET;
    public static final ModConfigSpec.ConfigValue<Boolean> MOMENTUM_SPEED_TEXT_VISIBLE;

    public static final ModConfigSpec.ConfigValue<MomentumDamageOverlay.Anchor> MOMENTUM_DAMAGE_ANCHOR;
    public static final ModConfigSpec.ConfigValue<MomentumDamageOverlay.Display> MOMENTUM_DAMAGE_DISPLAY;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_DAMAGE_Y_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_DAMAGE_X_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_DAMAGE_TEXT_X_OFFSET;
    public static final ModConfigSpec.ConfigValue<Integer> MOMENTUM_DAMAGE_TEXT_Y_OFFSET;
    public static final ModConfigSpec.ConfigValue<Boolean> MOMENTUM_DAMAGE_TEXT_VISIBLE;

    public static final ModConfigSpec SPEC;

    static {
        BUILDER.comment("It is recommended to keep the default UI settings.");
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

        BUILDER.push("QuiverArrows");
        BUILDER.comment("By default (Contextual), the arrow count only appears when you have a quiver equipped.");
        QUIVER_ARROWS_DISPLAY = BUILDER.defineEnum("quiverArrowDisplay", QuiverArrowOverlay.Display.Contextual);
        BUILDER.comment("Used to adjust arrows position (11 is one full hunger bar up).");
        QUIVER_ARROWS_X_OFFSET = BUILDER.define("quiverArrowXOffset", 0);
        QUIVER_ARROWS_Y_OFFSET = BUILDER.define("quiverArrowYOffset", 0);
        QUIVER_ARROWS_TEXT_VISIBLE = BUILDER.define("quiverArrowTextVisible", true);
        QUIVER_ARROWS_ANCHOR = BUILDER.defineEnum("quiverArrowAnchor", QuiverArrowOverlay.Anchor.BottomLeft);
        QUIVER_ARROWS_TEXT_X_OFFSET = BUILDER.define("quiverArrowTextXOffset", 0);
        QUIVER_ARROWS_TEXT_Y_OFFSET = BUILDER.define("quiverArrowTextYOffset", 0);
        BUILDER.pop();

        BUILDER.push("MomentumSpeed");
        BUILDER.comment("By default (Contextual), the momentum speed only appears when you are above 0 speed.");
        MOMENTUM_SPEED_DISPLAY = BUILDER.defineEnum("momentumSpeedDisplay", MomentumSpeedOverlay.Display.Contextual);
        BUILDER.comment("Used to adjust momentum speed position (11 is one full hunger bar up).");
        MOMENTUM_SPEED_X_OFFSET = BUILDER.define("momentumSpeedXOffset", 0);
        MOMENTUM_SPEED_Y_OFFSET = BUILDER.define("momentumSpeedYOffset", 0);
        MOMENTUM_SPEED_TEXT_VISIBLE = BUILDER.define("momentumSpeedTextVisible", true);
        MOMENTUM_SPEED_ANCHOR = BUILDER.defineEnum("momentumSpeedAnchor", MomentumSpeedOverlay.Anchor.BottomRight);
        MOMENTUM_SPEED_TEXT_X_OFFSET = BUILDER.define("momentumSpeedTextXOffset", 0);
        MOMENTUM_SPEED_TEXT_Y_OFFSET = BUILDER.define("momentumSpeedTextYOffset", 0);
        BUILDER.pop();

        BUILDER.push("MomentumDamage");
        BUILDER.comment("By default (Contextual), the momentum damage only appears when you are above 0 damage.");
        MOMENTUM_DAMAGE_DISPLAY = BUILDER.defineEnum("momentumDamageDisplay", MomentumDamageOverlay.Display.Contextual);
        BUILDER.comment("Used to adjust momentum damage position (11 is one full hunger bar up).");
        MOMENTUM_DAMAGE_X_OFFSET = BUILDER.define("momentumDamageXOffset", 0);
        MOMENTUM_DAMAGE_Y_OFFSET = BUILDER.define("momentumDamageYOffset", 0);
        MOMENTUM_DAMAGE_TEXT_VISIBLE = BUILDER.define("momentumDamageTextVisible", true);
        MOMENTUM_DAMAGE_ANCHOR = BUILDER.defineEnum("momentumDamageAnchor", MomentumDamageOverlay.Anchor.BottomRight);
        MOMENTUM_DAMAGE_TEXT_X_OFFSET = BUILDER.define("momentumDamageTextXOffset", 0);
        MOMENTUM_DAMAGE_TEXT_Y_OFFSET = BUILDER.define("momentumDamageTextYOffset", 0);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

}
