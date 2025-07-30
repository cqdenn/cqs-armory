package com.example.cqsarmory.gui.overlays;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.config.ClientConfigs;
import io.redspace.ironsspellbooks.item.CastingItem;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class RageBarOverlay implements LayeredDraw.Layer {
    private static RageBarOverlay instance;

    public static RageBarOverlay getInstance() {
        if (instance == null) {
            instance = new RageBarOverlay();
        }
        return instance;
    }

    public final static ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/gui/rage_bar.png");

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
    static final int TEXT_COLOR = ChatFormatting.DARK_RED.getColor();

    public void render(GuiGraphics guiHelper, DeltaTracker deltaTracker) {
        if (Minecraft.getInstance().options.hideGui || Minecraft.getInstance().player.isSpectator()) {
            return;
        }
        var player = Minecraft.getInstance().player;
        var screenWidth = guiHelper.guiWidth();
        var screenHeight = guiHelper.guiHeight();
        if (!shouldShowRageBar(player))
            return;

        int maxRage = (int) player.getAttribute(AttributeRegistry.MAX_RAGE).getValue();
        int rage = (int) AbilityData.get(player).getRage();
        int barX, barY;
        int configOffsetY = com.example.cqsarmory.config.ClientConfigs.RAGE_BAR_Y_OFFSET.get();
        int configOffsetX = com.example.cqsarmory.config.ClientConfigs.RAGE_BAR_X_OFFSET.get();
        Anchor anchor = com.example.cqsarmory.config.ClientConfigs.RAGE_BAR_ANCHOR.get();
        if (anchor == Anchor.XP && player.getJumpRidingScale() > 0) //Hide XP Rage bar when actively jumping on a horse
            return;
        barX = getBarX(anchor, screenWidth, player) + configOffsetX;
        barY = getBarY(anchor, screenHeight, Minecraft.getInstance().gui) - configOffsetY;

        int imageWidth = anchor == Anchor.XP ? XP_IMAGE_WIDTH : DEFAULT_IMAGE_WIDTH;
        int spriteX = anchor == Anchor.XP ? 68 : 0;
        int spriteY = anchor == Anchor.XP ? 40 : 0;
        guiHelper.blit(TEXTURE, barX, barY, spriteX, spriteY, imageWidth, IMAGE_HEIGHT, 256, 256);
        guiHelper.blit(TEXTURE, barX, barY, spriteX, spriteY + IMAGE_HEIGHT, (int) (imageWidth * Math.min((rage / (double) maxRage), 1)), IMAGE_HEIGHT);

        int textX, textY;
        String rageFraction = (rage) + "/" + maxRage;

        textX = com.example.cqsarmory.config.ClientConfigs.RAGE_TEXT_X_OFFSET.get() + barX + imageWidth / 2 - (int) ((("" + rage).length() + 0.5) * CHAR_WIDTH);
        textY = com.example.cqsarmory.config.ClientConfigs.RAGE_TEXT_Y_OFFSET.get() + barY + (anchor == Anchor.XP ? ICON_ROW_HEIGHT / 3 : ICON_ROW_HEIGHT);

        if (com.example.cqsarmory.config.ClientConfigs.RAGE_BAR_TEXT_VISIBLE.get()) {
            guiHelper.drawString(Minecraft.getInstance().font, rageFraction, textX, textY, TEXT_COLOR);
            //gui.getFont().draw(poseStack, manaFraction, textX, textY, TEXT_COLOR);
        }
    }

    public static boolean shouldShowRageBar(Player player) {
        //We show rage if their rage is not full
        var display = com.example.cqsarmory.config.ClientConfigs.RAGE_BAR_DISPLAY.get();
        return !player.isSpectator() && display != Display.Never &&
                (display == Display.Always || AbilityData.get(player).getRage() != 0);

    }

    private static int getBarX(Anchor anchor, int screenWidth, Player player) {
        if (anchor == Anchor.XP)
            return screenWidth / 2 - 91 - 3; //Vanilla's Pos - 3
        if (anchor == Anchor.Hunger)
            return screenWidth / 2 - DEFAULT_IMAGE_WIDTH / 2 + (HUNGER_BAR_OFFSET);
        else if (anchor == Anchor.Center)
            return screenWidth / 2 - DEFAULT_IMAGE_WIDTH / 2 + (AbilityData.get(player).getMomentum() > 0 ? 60 : 0);
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
            return screenHeight - HOTBAR_HEIGHT - (int) (ICON_ROW_HEIGHT * 2.5f) - IMAGE_HEIGHT / 2 - (Math.max(gui.rightHeight, gui.leftHeight) - 49);
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
