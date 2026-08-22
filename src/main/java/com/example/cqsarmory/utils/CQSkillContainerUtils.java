package com.example.cqsarmory.utils;

import com.example.cqsarmory.registry.CQComponentRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.util.TooltipsUtils;
import io.redspace.skillcasting.api.event.GatherSkillSelectionEvent;
import io.redspace.skillcasting.api.event.SkillSelectionPriority;
import io.redspace.skillcasting.data.SkillcastingData;
import io.redspace.skillcasting.data.cast.CastSource;
import io.redspace.skillcasting.data.skill.ISkillContainer;
import io.redspace.skillcasting.data.skill.SkillData;
import io.redspace.skillcasting.data.skill.SkillSlot;
import io.redspace.skillcasting.network.SkillcastingNetwork;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber
public class CQSkillContainerUtils {

    public static boolean has(ItemStack stack) {
        return stack.has(CQComponentRegistry.WEAPON_SKILL_CONTAINER);
    }

    public static ISkillContainer get(ItemStack stack) {
        return stack.get(CQComponentRegistry.WEAPON_SKILL_CONTAINER);
    }

    public static void set(ItemStack stack, ISkillContainer container) {
        stack.set(CQComponentRegistry.WEAPON_SKILL_CONTAINER, container);
    }

    public static ISkillContainer create(int maxSize, boolean mustEquip) {
        return ISkillContainer.create(mustEquip, maxSize);
    }

    public static void applyImbue(ItemStack stack, AbstractSpell spell, int level) {
        set(stack, ISkillContainer.create(false, new SkillData(spell, level)));
    }

    @SubscribeEvent
    public static void skillTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (has(stack) && !get(stack).isEmpty() && event.getEntity() != null) {
            var player = (LocalPlayer) event.getEntity();
            var lines = event.getToolTip();
            boolean advanced = event.getFlags().isAdvanced();
            CastSource castSource = CastSource.EMPTY;
            if (stack == player.getMainHandItem()) {
                castSource = CastSource.of(EquipmentSlot.MAINHAND);
            } else if (stack == player.getOffhandItem()) {
                castSource = CastSource.of(EquipmentSlot.OFFHAND);
            }
            int tooltipInjectIndex = advanced ? TooltipsUtils.indexOfAdvancedText(lines, stack) : lines.size();
            var spellContainer = get(stack);
            List<SkillSlot> spellSlots = spellContainer.getActiveSkills().stream().toList();
            int spellCount = spellSlots.size();
            var additionalLines = new ArrayList<Component>();
            var header = Component.literal(spellCount > 1 ? "Weapon Skills:" : "Weapon Skill:").withStyle(ChatFormatting.GRAY);
            for (var spellSlot : spellSlots) {
                var spellTooltip = TooltipsUtils.formatActiveSpellTooltip(stack, spellSlot.skillData(), castSource, player);
                //Indent the title because we'll have an additional header
                spellTooltip.set(1, Component.literal(" ").append(spellTooltip.get(1)));
                additionalLines.addAll(spellTooltip);
            }
            additionalLines.add(1, header);
            lines.addAll(tooltipInjectIndex < 0 ? lines.size() : tooltipInjectIndex, additionalLines);
        }
    }

    @SubscribeEvent
    public static void buildSkillOptions(GatherSkillSelectionEvent event) {
        var player = event.getEntity();
        CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
            /*ItemStack spellbook = Utils.getPlayerSpellbookStack(player);
            if (spellbook != null && SpellbookContainer.has(spellbook)) {
                event.addSource(SpellbookContainer.get(spellbook), Curios.SPELLBOOK_SLOT, SkillSelectionPriority.PRIMARY_SKILL_SOURCE);
            }*/
            inv.findCurios(CQSkillContainerUtils::has).forEach(
                    slotResult -> event.addSource(CQSkillContainerUtils.get(slotResult.stack()), String.format("%s_%s", slotResult.slotContext().identifier(), slotResult.slotContext().index()), SkillSelectionPriority.CURIO));
        });
    }

    @SubscribeEvent
    public static void onCurioChangeEvent(CurioChangeEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (has(event.getFrom()) || has(event.getTo())) {
            SkillcastingData.get(player).selectionManager().refresh(player);
            SkillcastingNetwork.syncSelection(player, SkillcastingData.get(player));
        }
    }

}
