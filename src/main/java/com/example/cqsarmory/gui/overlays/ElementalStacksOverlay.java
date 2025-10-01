package com.example.cqsarmory.gui.overlays;

import com.example.cqsarmory.config.ClientConfigs;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ElementalStacksOverlay implements LayeredDraw.Layer {
    private static ElementalStacksOverlay instance;

    public static ElementalStacksOverlay getInstance() {
        if (instance == null) {
            instance = new ElementalStacksOverlay();
        }
        return instance;
    }

    public ResourceLocation getTexture(Player player) {
        if (AbilityData.get(player).elementalistStacks.iceStacks > 0) {
            return ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "textures/gui/spell_icons/cone_of_cold.png");
        } else if (AbilityData.get(player).elementalistStacks.lightningStacks > 0) {
            return ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "textures/gui/spell_icons/lightning_bolt.png");
        } else {
            return ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "textures/gui/spell_icons/fireball.png");
        }
    }

    public int getTextColor(Player player) {
        if (AbilityData.get(player).elementalistStacks.iceStacks > 0) {
            return ChatFormatting.AQUA.getColor();
        } else if (AbilityData.get(player).elementalistStacks.lightningStacks > 0) {
            return ChatFormatting.DARK_BLUE.getColor();
        } else {
            return ChatFormatting.RED.getColor();
        }
    }

    public int getStacks(Player player) {
        if (AbilityData.get(player).elementalistStacks.iceStacks > 0) {
            return AbilityData.get(player).elementalistStacks.iceStacks;
        } else if (AbilityData.get(player).elementalistStacks.lightningStacks > 0) {
            return AbilityData.get(player).elementalistStacks.lightningStacks;
        } else {
            return AbilityData.get(player).elementalistStacks.fireStacks;
        }
    }

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

    static final int DEFAULT_IMAGE_WIDTH = 16;
    static final int XP_IMAGE_WIDTH = 188;
    static final int IMAGE_HEIGHT = 16;
    static final int HOTBAR_HEIGHT = 25;
    static final int ICON_ROW_HEIGHT = 11;
    static final int CHAR_WIDTH = 6;
    static final int HUNGER_BAR_OFFSET = 50;
    static final int SCREEN_BORDER_MARGIN = 20;
    //static final int TEXT_COLOR = ChatFormatting.GREEN.getColor();

    public void render(GuiGraphics guiHelper, DeltaTracker deltaTracker) {
        if (Minecraft.getInstance().options.hideGui || Minecraft.getInstance().player.isSpectator()) {
            return;
        }
        var player = Minecraft.getInstance().player;
        var screenWidth = guiHelper.guiWidth();
        var screenHeight = guiHelper.guiHeight();
        if (!shouldShowElementalStacks(player))
            return;

        //int arrowCount = AbilityData.get(player).quiverArrowCount;
        int barX, barY;
        int configOffsetY = com.example.cqsarmory.config.ClientConfigs.ELEMENTAL_STACKS_Y_OFFSET.get();
        int configOffsetX = com.example.cqsarmory.config.ClientConfigs.ELEMENTAL_STACKS_X_OFFSET.get();
        ElementalStacksOverlay.Anchor anchor = ClientConfigs.ELEMENTAL_STACKS_ANCHOR.get();
        /*if (anchor == Anchor.XP && player.getJumpRidingScale() > 0) //Hide XP Momentum bar when actively jumping on a horse
            return;*/
        barX = getBarX(anchor, screenWidth) + configOffsetX;
        barY = getBarY(anchor, screenHeight, Minecraft.getInstance().gui, player) - configOffsetY;

        int imageWidth = anchor == ElementalStacksOverlay.Anchor.XP ? XP_IMAGE_WIDTH : DEFAULT_IMAGE_WIDTH;
        int spriteX = anchor == ElementalStacksOverlay.Anchor.XP ? 68 : 0;
        int spriteY = anchor == ElementalStacksOverlay.Anchor.XP ? 40 : 0;
        guiHelper.blit(getTexture(player), barX, barY, spriteX, spriteY, imageWidth, IMAGE_HEIGHT, 16, 16);
        //guiHelper.blit(TEXTURE, barX, barY, spriteX, spriteY + IMAGE_HEIGHT, (int) (imageWidth * Math.min((momentum / (double) maxMomentum), 1)), IMAGE_HEIGHT);

        int textX, textY;
        String stacks = getStacks(player) + "";

        textX = com.example.cqsarmory.config.ClientConfigs.ELEMENTAL_STACKS_TEXT_X_OFFSET.get() + 12 + (Minecraft.getInstance().font.width(stacks)) + barX + imageWidth / 2 - (int) (((stacks).length() + 0.5) * CHAR_WIDTH);
        textY = com.example.cqsarmory.config.ClientConfigs.ELEMENTAL_STACKS_TEXT_Y_OFFSET.get() + barY + (anchor == ElementalStacksOverlay.Anchor.XP ? ICON_ROW_HEIGHT / 3 : ICON_ROW_HEIGHT);

        if (com.example.cqsarmory.config.ClientConfigs.ELEMENTAL_STACKS_TEXT_VISIBLE.get()) {
            guiHelper.drawString(Minecraft.getInstance().font, stacks, textX, textY, getTextColor(player));
            //gui.getFont().draw(poseStack, manaFraction, textX, textY, TEXT_COLOR);
        }
    }

    public static boolean shouldShowElementalStacks(Player player) {
        //We show when they have stacks
        var display = com.example.cqsarmory.config.ClientConfigs.ELEMENTAL_STACKS_DISPLAY.get();
        return !player.isSpectator() && display != Display.Never &&
                (display == Display.Always || AbilityData.get(player).elementalistStacks.lightningStacks > 0 || AbilityData.get(player).elementalistStacks.fireStacks > 0 || AbilityData.get(player).elementalistStacks.iceStacks > 0);

    }

    private static int getBarX(ElementalStacksOverlay.Anchor anchor, int screenWidth) {
        if (anchor == ElementalStacksOverlay.Anchor.XP)
            return screenWidth / 2 - 91 - 3; //Vanilla's Pos - 3
        if (anchor == ElementalStacksOverlay.Anchor.Hunger)
            return screenWidth / 2 - DEFAULT_IMAGE_WIDTH / 2 + (HUNGER_BAR_OFFSET);
        else if (anchor == ElementalStacksOverlay.Anchor.Center)
            return screenWidth / 2 - DEFAULT_IMAGE_WIDTH / 2;
        else if (anchor == ElementalStacksOverlay.Anchor.TopLeft || anchor == ElementalStacksOverlay.Anchor.BottomLeft)
            return SCREEN_BORDER_MARGIN;
        else return screenWidth - SCREEN_BORDER_MARGIN - DEFAULT_IMAGE_WIDTH;

    }

    private static int getBarY(Anchor anchor, int screenHeight, Gui gui, Player player) {
        if (anchor == Anchor.XP)
            return screenHeight - 32 + 3 - 7; //Vanilla's Pos - 7
        if (anchor == Anchor.Hunger)
            return screenHeight - (getAndIncrementRightHeight(gui) - 2) - IMAGE_HEIGHT / 2;
        if (anchor == Anchor.Center)
            return screenHeight - HOTBAR_HEIGHT - (int) (ICON_ROW_HEIGHT * 2.5f) - IMAGE_HEIGHT / 2 - (Math.max(gui.rightHeight, gui.leftHeight) - 39);
        if (anchor == Anchor.TopLeft || anchor == Anchor.TopRight)
            return SCREEN_BORDER_MARGIN;
        return screenHeight - SCREEN_BORDER_MARGIN - IMAGE_HEIGHT - (AbilityData.get(player).quiverArrowCount > 0 && !CQtils.getPlayerCurioStack(player, "quiver").isEmpty() ? 20 : 0);

    }

    private static int getAndIncrementRightHeight(Gui gui) {
        int x = gui.rightHeight;
        gui.rightHeight += 10;
        return x;
    }
}
