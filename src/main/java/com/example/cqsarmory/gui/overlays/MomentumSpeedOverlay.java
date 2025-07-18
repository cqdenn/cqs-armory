package com.example.cqsarmory.gui.overlays;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.registry.AttributeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class MomentumSpeedOverlay implements LayeredDraw.Layer {
    private static MomentumSpeedOverlay instance;

    public static MomentumSpeedOverlay getInstance() {
        if (instance == null) {
            instance = new MomentumSpeedOverlay();
        }
        return instance;
    }

    public final static ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/gui/speed_stacks_bar.png");

    public enum Anchor {
        Hunger,
        XP,
        Center,
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight
    }

    public enum Display {
        Never,
        Always,
        Contextual
    }

    static final int DEFAULT_IMAGE_WIDTH = 98;
    static final int XP_IMAGE_WIDTH = 188;
    static final int IMAGE_HEIGHT = 21;
    static final int HOTBAR_HEIGHT = 25;
    static final int ICON_ROW_HEIGHT = 11;
    static final int CHAR_WIDTH = 6;
    static final int HUNGER_BAR_OFFSET = 50;
    static final int SCREEN_BORDER_MARGIN = 20;
    static final int TEXT_COLOR = ChatFormatting.DARK_AQUA.getColor();

    public void render(GuiGraphics guiHelper, DeltaTracker deltaTracker) {
        if (Minecraft.getInstance().options.hideGui || Minecraft.getInstance().player.isSpectator()) {
            return;
        }
        var player = Minecraft.getInstance().player;
        var screenWidth = guiHelper.guiWidth();
        var screenHeight = guiHelper.guiHeight();
        if (!shouldShowMomentumSpeed(player))
            return;

        int maxStacks = 10; // TBD FIXME
        int stacks = (int) AbilityData.get(player).momentumOrbEffects.speedStacks;
        int barX, barY;
        int configOffsetY = com.example.cqsarmory.config.ClientConfigs.MOMENTUM_SPEED_Y_OFFSET.get();
        int configOffsetX = com.example.cqsarmory.config.ClientConfigs.MOMENTUM_SPEED_X_OFFSET.get();
        Anchor anchor = com.example.cqsarmory.config.ClientConfigs.MOMENTUM_SPEED_ANCHOR.get();
        if (anchor == Anchor.XP && player.getJumpRidingScale() > 0) //Hide XP Momentum stacks when actively jumping on a horse
            return;
        barX = getBarX(anchor, screenWidth, player) + configOffsetX;
        barY = getBarY(anchor, screenHeight, Minecraft.getInstance().gui) - configOffsetY;

        int imageWidth = anchor == Anchor.XP ? XP_IMAGE_WIDTH : DEFAULT_IMAGE_WIDTH;
        int spriteX = anchor == Anchor.XP ? 68 : 0;
        int spriteY = anchor == Anchor.XP ? 40 : 0;
        guiHelper.blit(TEXTURE, barX, barY, spriteX, spriteY, imageWidth, IMAGE_HEIGHT, 256, 256);
        guiHelper.blit(TEXTURE, barX, barY, spriteX, spriteY + IMAGE_HEIGHT, (int) (imageWidth * Math.min((stacks / (double) maxStacks), 1)), IMAGE_HEIGHT);

        int textX, textY;
        String stacksFraction = (stacks) + "/" + maxStacks;

        textX = com.example.cqsarmory.config.ClientConfigs.MOMENTUM_SPEED_TEXT_X_OFFSET.get() + barX + imageWidth / 2 - (int) ((("" + stacks).length() + 0.5) * CHAR_WIDTH);
        textY = com.example.cqsarmory.config.ClientConfigs.MOMENTUM_SPEED_TEXT_Y_OFFSET.get() + barY + (anchor == Anchor.XP ? ICON_ROW_HEIGHT / 3 : ICON_ROW_HEIGHT);

        int timerTextX, timerTextY;
        String stacksTimer = ((AbilityData.get(player).momentumOrbEffects.speedEnd - player.tickCount) / 20 ) + "s";

        timerTextX = 30 + barX + imageWidth / 2 - (int) ((("" + stacks).length() + 0.5) * CHAR_WIDTH);
        timerTextY = barY + (anchor == Anchor.XP ? ICON_ROW_HEIGHT / 3 : ICON_ROW_HEIGHT);

        if (com.example.cqsarmory.config.ClientConfigs.MOMENTUM_SPEED_TEXT_VISIBLE.get()) {
            guiHelper.drawString(Minecraft.getInstance().font, stacksFraction, textX, textY, TEXT_COLOR);
            if (((AbilityData.get(player).momentumOrbEffects.speedEnd - player.tickCount) / 20 ) > 0) {
                guiHelper.drawString(Minecraft.getInstance().font, stacksTimer, timerTextX, timerTextY, TEXT_COLOR);
            } else { guiHelper.drawString(Minecraft.getInstance().font, (0) + "s", timerTextX, timerTextY, TEXT_COLOR); }
            //gui.getFont().draw(poseStack, manaFraction, textX, textY, TEXT_COLOR);
        }
    }

    public static boolean shouldShowMomentumSpeed(Player player) {
        //We show momentum speed if their momentum speed is not 0
        var display = com.example.cqsarmory.config.ClientConfigs.MOMENTUM_SPEED_DISPLAY.get();
        return !player.isSpectator() && display != Display.Never &&
                (display == Display.Always || AbilityData.get(player).momentumOrbEffects.speedStacks != 0);

    }

    private static int getBarX(Anchor anchor, int screenWidth, Player player) {
        if (anchor == Anchor.XP)
            return screenWidth / 2 - 91 - 3; //Vanilla's Pos - 3
        if (anchor == Anchor.Hunger)
            return screenWidth / 2 - DEFAULT_IMAGE_WIDTH / 2 + (HUNGER_BAR_OFFSET);
        else if (anchor == Anchor.Center)
            return screenWidth / 2 - DEFAULT_IMAGE_WIDTH / 2;
        else if (anchor == Anchor.TopLeft || anchor == Anchor.BottomLeft)
            return SCREEN_BORDER_MARGIN;
        else return screenWidth - SCREEN_BORDER_MARGIN - DEFAULT_IMAGE_WIDTH;

    }

    private static int getBarY(Anchor anchor, int screenHeight, Gui gui) {
        if (anchor == Anchor.XP)
            return screenHeight - 32 + 3 - 7; //Vanilla's Pos - 7
        if (anchor == Anchor.Hunger)
            return screenHeight - (getAndIncrementRightHeight(gui) - 2) - IMAGE_HEIGHT / 2;
        if (anchor == Anchor.Center)
            return screenHeight - HOTBAR_HEIGHT - (int) (ICON_ROW_HEIGHT * 2.5f) - IMAGE_HEIGHT / 2;
        if (anchor == Anchor.TopLeft || anchor == Anchor.TopRight)
            return SCREEN_BORDER_MARGIN;
        return screenHeight - SCREEN_BORDER_MARGIN - IMAGE_HEIGHT;

    }

    private static int getAndIncrementRightHeight(Gui gui) {
        int x = gui.rightHeight;
        gui.rightHeight += 10;
        return x;
    }
}
