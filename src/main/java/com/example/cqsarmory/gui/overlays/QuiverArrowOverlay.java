package com.example.cqsarmory.gui.overlays;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.config.ClientConfigs;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.items.curios.QuiverItem;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Predicate;

public class QuiverArrowOverlay implements LayeredDraw.Layer {
    private static QuiverArrowOverlay instance;

    public static QuiverArrowOverlay getInstance() {
        if (instance == null) {
            instance = new QuiverArrowOverlay();
        }
        return instance;
    }

    public final static ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/gui/quiver_arrow_overlay.png");

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
    static final int TEXT_COLOR = ChatFormatting.GRAY.getColor();

    public void render(GuiGraphics guiHelper, DeltaTracker deltaTracker) {
        if (Minecraft.getInstance().options.hideGui || Minecraft.getInstance().player.isSpectator()) {
            return;
        }
        var player = Minecraft.getInstance().player;
        var screenWidth = guiHelper.guiWidth();
        var screenHeight = guiHelper.guiHeight();
        if (!shouldShowQuiverArrows(player))
            return;

        //int arrowCount = AbilityData.get(player).quiverArrowCount;
        int barX, barY;
        int configOffsetY = com.example.cqsarmory.config.ClientConfigs.QUIVER_ARROWS_Y_OFFSET.get();
        int configOffsetX = com.example.cqsarmory.config.ClientConfigs.QUIVER_ARROWS_X_OFFSET.get();
        Anchor anchor = ClientConfigs.QUIVER_ARROWS_ANCHOR.get();
        /*if (anchor == Anchor.XP && player.getJumpRidingScale() > 0) //Hide XP Momentum bar when actively jumping on a horse
            return;*/
        barX = getBarX(anchor, screenWidth, player) + configOffsetX;
        barY = getBarY(anchor, screenHeight, Minecraft.getInstance().gui) - configOffsetY;

        int imageWidth = anchor == Anchor.XP ? XP_IMAGE_WIDTH : DEFAULT_IMAGE_WIDTH;
        int spriteX = anchor == Anchor.XP ? 68 : 0;
        int spriteY = anchor == Anchor.XP ? 40 : 0;
        guiHelper.blit(TEXTURE, barX, barY, spriteX, spriteY, imageWidth, IMAGE_HEIGHT, 16, 16);
        //guiHelper.blit(TEXTURE, barX, barY, spriteX, spriteY + IMAGE_HEIGHT, (int) (imageWidth * Math.min((momentum / (double) maxMomentum), 1)), IMAGE_HEIGHT);

        int textX, textY;
        String arrowCount = AbilityData.get(player).quiverArrowCount + "";

        textX = com.example.cqsarmory.config.ClientConfigs.QUIVER_ARROWS_TEXT_X_OFFSET.get() + 8 + barX + imageWidth / 2 - (int) (((arrowCount).length() + 0.5) * CHAR_WIDTH);
        textY = com.example.cqsarmory.config.ClientConfigs.QUIVER_ARROWS_TEXT_Y_OFFSET.get() + barY + (anchor == Anchor.XP ? ICON_ROW_HEIGHT / 3 : ICON_ROW_HEIGHT);

        if (com.example.cqsarmory.config.ClientConfigs.QUIVER_ARROWS_TEXT_VISIBLE.get()) {
            guiHelper.drawString(Minecraft.getInstance().font, arrowCount, textX, textY, TEXT_COLOR);
            //gui.getFont().draw(poseStack, manaFraction, textX, textY, TEXT_COLOR);
        }
    }

    public static boolean shouldShowQuiverArrows(Player player) {
        //We show if holding bow/crossbow and a quiver is equipped
        var display = com.example.cqsarmory.config.ClientConfigs.QUIVER_ARROWS_DISPLAY.get();
        return !player.isSpectator() && display != Display.Never &&
                (display == Display.Always || ((player.getMainHandItem().getItem() instanceof BowItem || player.getOffhandItem().getItem() instanceof BowItem || player.getMainHandItem().getItem() instanceof CrossbowItem || player.getOffhandItem().getItem() instanceof CrossbowItem) && ItemRegistry.BASIC_QUIVER.get().isEquippedBy(player)));

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
            return screenHeight - HOTBAR_HEIGHT - (int) (ICON_ROW_HEIGHT * 2.5f) - IMAGE_HEIGHT / 2 - (Math.max(gui.rightHeight, gui.leftHeight) - 39);
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
