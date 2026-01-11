package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.api.CQSpellDataRegistryHolder;
import com.example.cqsarmory.data.ItemModelDataGenerator;
import com.example.cqsarmory.items.*;
import com.example.cqsarmory.items.armor.*;
import com.example.cqsarmory.items.curios.*;
import com.example.cqsarmory.items.curios.brands.ArcaneBrand;
import com.example.cqsarmory.items.curios.brands.ElementalBrand;
import com.example.cqsarmory.items.curios.brands.SummonersBrand;
import com.example.cqsarmory.items.curios.coatings.*;
import com.example.cqsarmory.items.curios.quivers.*;
import com.example.cqsarmory.items.weapons.*;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ItemRegistry {
    public static final List<Weaponset> WEAPONSETS = new ArrayList<>();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CqsArmory.MODID);

    //archer dmg
    public static final DeferredHolder<Item, Item> HUNTER_HELMET = ITEMS.register("hunter_helmet", () -> new HunterArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(22))));
    public static final DeferredHolder<Item, Item> HUNTER_CHESTPLATE = ITEMS.register("hunter_chestplate", () -> new HunterArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(22))));
    public static final DeferredHolder<Item, Item> HUNTER_LEGGINGS = ITEMS.register("hunter_leggings", () -> new HunterArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(22))));
    public static final DeferredHolder<Item, Item> HUNTER_BOOTS = ITEMS.register("hunter_boots", () -> new HunterArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(22))));

    public static final DeferredHolder<Item, Item> TRACKER_HELMET = ITEMS.register("tracker_helmet", () -> new TrackerArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(26))));
    public static final DeferredHolder<Item, Item> TRACKER_CHESTPLATE = ITEMS.register("tracker_chestplate", () -> new TrackerArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(26))));
    public static final DeferredHolder<Item, Item> TRACKER_LEGGINGS = ITEMS.register("tracker_leggings", () -> new TrackerArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(26))));
    public static final DeferredHolder<Item, Item> TRACKER_BOOTS = ITEMS.register("tracker_boots", () -> new TrackerArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(26))));

    public static final DeferredHolder<Item, Item> MARKSMAN_HELMET = ITEMS.register("marksman_helmet", () -> new MarksmanArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(37))));
    public static final DeferredHolder<Item, Item> MARKSMAN_CHESTPLATE = ITEMS.register("marksman_chestplate", () -> new MarksmanArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(37))));
    public static final DeferredHolder<Item, Item> MARKSMAN_LEGGINGS = ITEMS.register("marksman_leggings", () -> new MarksmanArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(37))));
    public static final DeferredHolder<Item, Item> MARKSMAN_BOOTS = ITEMS.register("marksman_boots", () -> new MarksmanArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(37))));

    //archer speed
    public static final DeferredHolder<Item, Item> SCOUT_HELMET = ITEMS.register("scout_helmet", () -> new ScoutArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(22))));
    public static final DeferredHolder<Item, Item> SCOUT_CHESTPLATE = ITEMS.register("scout_chestplate", () -> new ScoutArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(22))));
    public static final DeferredHolder<Item, Item> SCOUT_LEGGINGS = ITEMS.register("scout_leggings", () -> new ScoutArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(22))));
    public static final DeferredHolder<Item, Item> SCOUT_BOOTS = ITEMS.register("scout_boots", () -> new ScoutArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(22))));

    public static final DeferredHolder<Item, Item> RANGER_HELMET = ITEMS.register("ranger_helmet", () -> new RangerArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(26))));
    public static final DeferredHolder<Item, Item> RANGER_CHESTPLATE = ITEMS.register("ranger_chestplate", () -> new RangerArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(26))));
    public static final DeferredHolder<Item, Item> RANGER_LEGGINGS = ITEMS.register("ranger_leggings", () -> new RangerArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(26))));
    public static final DeferredHolder<Item, Item> RANGER_BOOTS = ITEMS.register("ranger_boots", () -> new RangerArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(26))));

    public static final DeferredHolder<Item, Item> SKIRMISHER_HELMET = ITEMS.register("skirmisher_helmet", () -> new SkirmisherArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(37))));
    public static final DeferredHolder<Item, Item> SKIRMISHER_CHESTPLATE = ITEMS.register("skirmisher_chestplate", () -> new SkirmisherArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(37))));
    public static final DeferredHolder<Item, Item> SKIRMISHER_LEGGINGS = ITEMS.register("skirmisher_leggings", () -> new SkirmisherArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(37))));
    public static final DeferredHolder<Item, Item> SKIRMISHER_BOOTS = ITEMS.register("skirmisher_boots", () -> new SkirmisherArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(37))));

    //melee dmg
    public static final DeferredHolder<Item, Item> WARRIOR_HELMET = ITEMS.register("warrior_helmet", () -> new WarriorArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(22))));
    public static final DeferredHolder<Item, Item> WARRIOR_CHESTPLATE = ITEMS.register("warrior_chestplate", () -> new WarriorArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(22))));
    public static final DeferredHolder<Item, Item> WARRIOR_LEGGINGS = ITEMS.register("warrior_leggings", () -> new WarriorArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(22))));
    public static final DeferredHolder<Item, Item> WARRIOR_BOOTS = ITEMS.register("warrior_boots", () -> new WarriorArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(22))));

    public static final DeferredHolder<Item, Item> SOLDIER_HELMET = ITEMS.register("soldier_helmet", () -> new SoldierArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(26))));
    public static final DeferredHolder<Item, Item> SOLDIER_CHESTPLATE = ITEMS.register("soldier_chestplate", () -> new SoldierArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(26))));
    public static final DeferredHolder<Item, Item> SOLDIER_LEGGINGS = ITEMS.register("soldier_leggings", () -> new SoldierArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(26))));
    public static final DeferredHolder<Item, Item> SOLDIER_BOOTS = ITEMS.register("soldier_boots", () -> new SoldierArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(26))));

    public static final DeferredHolder<Item, Item> CHAMPION_HELMET = ITEMS.register("champion_helmet", () -> new ChampionArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(37))));
    public static final DeferredHolder<Item, Item> CHAMPION_CHESTPLATE = ITEMS.register("champion_chestplate", () -> new ChampionArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(37))));
    public static final DeferredHolder<Item, Item> CHAMPION_LEGGINGS = ITEMS.register("champion_leggings", () -> new ChampionArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(37))));
    public static final DeferredHolder<Item, Item> CHAMPION_BOOTS = ITEMS.register("champion_boots", () -> new ChampionArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(37))));

    //melee tank
    public static final DeferredHolder<Item, Item> RAMPART_HELMET = ITEMS.register("rampart_helmet", () -> new RampartArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(22))));
    public static final DeferredHolder<Item, Item> RAMPART_CHESTPLATE = ITEMS.register("rampart_chestplate", () -> new RampartArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(22))));
    public static final DeferredHolder<Item, Item> RAMPART_LEGGINGS = ITEMS.register("rampart_leggings", () -> new RampartArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(22))));
    public static final DeferredHolder<Item, Item> RAMPART_BOOTS = ITEMS.register("rampart_boots", () -> new RampartArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(22))));

    public static final DeferredHolder<Item, Item> BASTION_HELMET = ITEMS.register("bastion_helmet", () -> new BastionArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(26))));
    public static final DeferredHolder<Item, Item> BASTION_CHESTPLATE = ITEMS.register("bastion_chestplate", () -> new BastionArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(26))));
    public static final DeferredHolder<Item, Item> BASTION_LEGGINGS = ITEMS.register("bastion_leggings", () -> new BastionArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(26))));
    public static final DeferredHolder<Item, Item> BASTION_BOOTS = ITEMS.register("bastion_boots", () -> new BastionArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(26))));

    public static final DeferredHolder<Item, Item> JUGGERNAUT_HELMET = ITEMS.register("juggernaut_helmet", () -> new JuggernautArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(37))));
    public static final DeferredHolder<Item, Item> JUGGERNAUT_CHESTPLATE = ITEMS.register("juggernaut_chestplate", () -> new JuggernautArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(37))));
    public static final DeferredHolder<Item, Item> JUGGERNAUT_LEGGINGS = ITEMS.register("juggernaut_leggings", () -> new JuggernautArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(37))));
    public static final DeferredHolder<Item, Item> JUGGERNAUT_BOOTS = ITEMS.register("juggernaut_boots", () -> new JuggernautArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(37))));

    //mage dmg
    public static final DeferredHolder<Item, Item> APPRENTICE_HELMET = ITEMS.register("apprentice_helmet", () -> new ApprenticeArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(22))));
    public static final DeferredHolder<Item, Item> APPRENTICE_CHESTPLATE = ITEMS.register("apprentice_chestplate", () -> new ApprenticeArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(22))));
    public static final DeferredHolder<Item, Item> APPRENTICE_LEGGINGS = ITEMS.register("apprentice_leggings", () -> new ApprenticeArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(22))));
    public static final DeferredHolder<Item, Item> APPRENTICE_BOOTS = ITEMS.register("apprentice_boots", () -> new ApprenticeArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(22))));

    public static final DeferredHolder<Item, Item> MAGUS_HELMET = ITEMS.register("magus_helmet", () -> new MagusArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(26))));
    public static final DeferredHolder<Item, Item> MAGUS_CHESTPLATE = ITEMS.register("magus_chestplate", () -> new MagusArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(26))));
    public static final DeferredHolder<Item, Item> MAGUS_LEGGINGS = ITEMS.register("magus_leggings", () -> new MagusArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(26))));
    public static final DeferredHolder<Item, Item> MAGUS_BOOTS = ITEMS.register("magus_boots", () -> new MagusArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(26))));

    public static final DeferredHolder<Item, Item> ARCHMAGE_HELMET = ITEMS.register("archmage_helmet", () -> new ArchmageArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(37))));
    public static final DeferredHolder<Item, Item> ARCHMAGE_CHESTPLATE = ITEMS.register("archmage_chestplate", () -> new ArchmageArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(37))));
    public static final DeferredHolder<Item, Item> ARCHMAGE_LEGGINGS = ITEMS.register("archmage_leggings", () -> new ArchmageArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(37))));
    public static final DeferredHolder<Item, Item> ARCHMAGE_BOOTS = ITEMS.register("archmage_boots", () -> new ArchmageArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(37))));

    //mage mana
    public static final DeferredHolder<Item, Item> SEER_HELMET = ITEMS.register("seer_helmet", () -> new SeerArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(22))));
    public static final DeferredHolder<Item, Item> SEER_CHESTPLATE = ITEMS.register("seer_chestplate", () -> new SeerArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(22))));
    public static final DeferredHolder<Item, Item> SEER_LEGGINGS = ITEMS.register("seer_leggings", () -> new SeerArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(22))));
    public static final DeferredHolder<Item, Item> SEER_BOOTS = ITEMS.register("seer_boots", () -> new SeerArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(22))));

    public static final DeferredHolder<Item, Item> MYSTIC_HELMET = ITEMS.register("mystic_helmet", () -> new MysticArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(26))));
    public static final DeferredHolder<Item, Item> MYSTIC_CHESTPLATE = ITEMS.register("mystic_chestplate", () -> new MysticArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(26))));
    public static final DeferredHolder<Item, Item> MYSTIC_LEGGINGS = ITEMS.register("mystic_leggings", () -> new MysticArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(26))));
    public static final DeferredHolder<Item, Item> MYSTIC_BOOTS = ITEMS.register("mystic_boots", () -> new MysticArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(26))));

    public static final DeferredHolder<Item, Item> SAGE_HELMET = ITEMS.register("sage_helmet", () -> new SageArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(37))));
    public static final DeferredHolder<Item, Item> SAGE_CHESTPLATE = ITEMS.register("sage_chestplate", () -> new SageArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(37))));
    public static final DeferredHolder<Item, Item> SAGE_LEGGINGS = ITEMS.register("sage_leggings", () -> new SageArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(37))));
    public static final DeferredHolder<Item, Item> SAGE_BOOTS = ITEMS.register("sage_boots", () -> new SageArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(37))));

    public static final Supplier<CurioBaseItem> BERSERKERS_RUSH = ITEMS.register("berserkers_rush", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_SPEED, 0.01, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> BERSERKERS_FURY = ITEMS.register("berserkers_fury", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_DAMAGE, 0.01, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> WARDSTONE = ITEMS.register("wardstone", () -> new SimpleDescriptiveBooster(ItemPropertiesHelper.equipment(1), "booster"));
    public static final Supplier<CurioBaseItem> HELLFIRE_SIGIL = ITEMS.register("hellfire_sigil", () -> new SimpleDescriptiveBooster(ItemPropertiesHelper.equipment(1), "booster"));
    public static final Supplier<CurioBaseItem> CHRONOWARP_RUNE = ITEMS.register("chronowarp_rune", () -> new SimpleDescriptiveBooster(ItemPropertiesHelper.equipment(1), "booster"));
    public static final Supplier<CurioBaseItem> MANASAVER = ITEMS.register("manasaver", () -> new SimpleDescriptiveBooster(ItemPropertiesHelper.equipment(1), "booster"));
    public static final Supplier<CurioBaseItem> HUNTERS_ECHO = ITEMS.register("hunters_echo", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.MOMENTUM_ORBS_SPAWNED, 1, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> BLASTER = ITEMS.register("blaster", () -> new SimpleDescriptiveBooster(ItemPropertiesHelper.equipment(1), "booster"));
    public static final Supplier<CurioBaseItem> SHARPHOOTER = ITEMS.register("sharpshooter", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.ARROW_PIERCING, 2, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> BIGGER_BOOMER = ITEMS.register("bigger_boomer", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.EXPLOSIVE_DAMAGE, 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> HUNTER_TALISMAN = ITEMS.register("hunter_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.DODGE_CHANCE, 0.02, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> TRACKER_TALISMAN = ITEMS.register("tracker_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.DODGE_CHANCE, 0.04, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> MARKSMAN_TALISMAN = ITEMS.register("marksman_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.DODGE_CHANCE, 0.06, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> SCOUT_TALISMAN = ITEMS.register("scout_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.MIN_MOMENTUM, 10, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> RANGER_TALISMAN = ITEMS.register("ranger_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.MIN_MOMENTUM, 20, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> SKIRMISHER_TALISMAN = ITEMS.register("skirmisher_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.MIN_MOMENTUM, 30, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> WARRIOR_TALISMAN = ITEMS.register("warrior_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.MIN_RAGE, 5, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> SOLDIER_TALISMAN = ITEMS.register("soldier_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.MIN_RAGE, 10, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> CHAMPION_TALISMAN = ITEMS.register("champion_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.MIN_RAGE, 15, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> RAMPART_TALISMAN = ITEMS.register("rampart_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.BLOCK_STRENGTH, 15, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> BASTION_TALISMAN = ITEMS.register("bastion_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.BLOCK_STRENGTH, 25, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> JUGGERNAUT_TALISMAN = ITEMS.register("juggernaut_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.BLOCK_STRENGTH, 35, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> SEER_TALISMAN = ITEMS.register("seer_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(AttributeRegistry.MANA_REGEN, 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> MYSTIC_TALISMAN = ITEMS.register("mystic_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(AttributeRegistry.MANA_REGEN, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> SAGE_TALISMAN = ITEMS.register("sage_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(AttributeRegistry.MANA_REGEN, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> APPRENTICE_TALISMAN = ITEMS.register("apprentice_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(AttributeRegistry.SPELL_POWER, 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> MAGUS_TALISMAN = ITEMS.register("magus_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(AttributeRegistry.SPELL_POWER, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> ARCHMAGE_TALISMAN = ITEMS.register("archmage_talisman", () -> new BoosterBaseItem(ItemPropertiesHelper.equipment(1)).withAttributes("booster", new AttributeContainer(AttributeRegistry.SPELL_POWER, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));

    public static final Supplier<CurioBaseItem> BASIC_QUIVER = ITEMS.register("basic_quiver", () -> new QuiverItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of()).withQuiverAttributes(100));
    public static final Supplier<CurioBaseItem> POISON_QUIVER = ITEMS.register("poison_quiver", () -> new PoisonQuiver(ItemPropertiesHelper.equipment(1), "quiver", SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.POISON_ARROW_SPELL, 5))).withQuiverAttributes(250));
    public static final Supplier<CurioBaseItem> FIREWORK_QUIVER = ITEMS.register("firework_quiver", () -> new FireworkQuiver(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of()).withQuiverAttributes(250));
    public static final Supplier<CurioBaseItem> HUNTERS_QUIVER = ITEMS.register("hunters_quiver", () -> new QuiverItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.HUNTERS_MARK_SPELL, 1))).withQuiverAttributes(250));
    public static final Supplier<CurioBaseItem> FIRE_QUIVER = ITEMS.register("fire_quiver", () -> new FireQuiver(ItemPropertiesHelper.equipment(1), "quiver", SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.FIRE_ARROW_SPELL, 5))).withQuiverAttributes(250));
    public static final Supplier<CurioBaseItem> ICE_QUIVER = ITEMS.register("ice_quiver", () -> new IceQuiver(ItemPropertiesHelper.equipment(1), "quiver", SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.ICE_ARROW_SPELL, 5))).withQuiverAttributes(250));
    public static final Supplier<CurioBaseItem> GRAVITY_QUIVER = ITEMS.register("gravity_quiver", () -> new GravityQuiver(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.GRAVITY_SNARE_SPELL, 1))).withQuiverAttributes(250));
    public static final Supplier<CurioBaseItem> PLENTY_QUIVER = ITEMS.register("plenty_quiver", () -> new QuiverItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.REFILL_SPELL, 1))).withQuiverAttributes(250));
    public static final Supplier<CurioBaseItem> BAT_QUIVER = ITEMS.register("bat_quiver", () -> new BatQuiver(ItemPropertiesHelper.equipment(1), "quiver", SpellDataRegistryHolder.of()).withQuiverAttributes(250));
    public static final Supplier<CurioBaseItem> SWORD_QUIVER = ITEMS.register("sword_quiver", () -> new SwordQuiver(ItemPropertiesHelper.equipment(1), "quiver", SpellDataRegistryHolder.of()).withQuiverAttributes(250));
    public static final Supplier<CurioBaseItem> ANVIL_QUIVER = ITEMS.register("anvil_quiver", () -> new AnvilQuiver(ItemPropertiesHelper.equipment(1), "quiver", SpellDataRegistryHolder.of()).withQuiverAttributes(250));

    public static final Supplier<CurioBaseItem> MAGMA_BRAND = ITEMS.register("magma_brand", () -> new BrandBaseItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.MAGMA_BOMB_SPELL, 8))).withAttributes("brand", new AttributeContainer(AttributeRegistry.FIRE_SPELL_POWER, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> COLD_BRAND = ITEMS.register("cold_brand", () -> new BrandBaseItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.CONE_OF_COLD_SPELL, 10))).withAttributes("brand", new AttributeContainer(AttributeRegistry.ICE_SPELL_POWER, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> VOLT_BRAND = ITEMS.register("volt_brand", () -> new BrandBaseItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.VOLT_STRIKE_SPELL, 10))).withAttributes("brand", new AttributeContainer(AttributeRegistry.LIGHTNING_SPELL_POWER, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> BLOOD_BRAND = ITEMS.register("blood_brand", () -> new BrandBaseItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.BLOOD_NEEDLES_SPELL, 10))).withAttributes("brand", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.NECROMANCY_SKILL_POWER, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> EVASIVE_BRAND = ITEMS.register("evasive_brand", () -> new BrandBaseItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.EVASION_SPELL, 5))).withAttributes("brand", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.ARCANE_SKILL_POWER, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> ANGEL_BRAND = ITEMS.register("angel_brand", () -> new BrandBaseItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(SpellRegistry.ANGEL_WINGS_SPELL, 5))).withAttributes("brand", new AttributeContainer(AttributeRegistry.HOLY_SPELL_POWER, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> VEIL_BRAND = ITEMS.register("veil_brand", () -> new SimpleDescriptiveBrand(ItemPropertiesHelper.equipment(1), "brand").withAttributes("brand", new AttributeContainer(AttributeRegistry.SPELL_POWER, 1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));
    public static final Supplier<CurioBaseItem> ARCANE_BRAND = ITEMS.register("arcane_brand", () -> new ArcaneBrand().withAttributes("brand", new AttributeContainer(AttributeRegistry.MAX_MANA, 100, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> INFINITY_BRAND = ITEMS.register("infinity_brand", () -> new BrandBaseItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.INFINITE_MAGIC_SPELL, 1))));
    public static final Supplier<CurioBaseItem> ELEMENTAL_BRAND = ITEMS.register("elemental_brand", () -> new ElementalBrand(ItemPropertiesHelper.equipment(1), "brand"));
    public static final Supplier<CurioBaseItem> SUMMONERS_BRAND = ITEMS.register("summoners_brand", () -> new SummonersBrand(ItemPropertiesHelper.equipment(1), "brand"));

    public static final Supplier<CurioBaseItem> POISON_COATING = ITEMS.register("poison_coating", () -> new PoisonCoating(ItemPropertiesHelper.equipment(1), "coating"));
    public static final Supplier<CurioBaseItem> VOLCANO_COATING = ITEMS.register("volcano_coating", () -> new SimpleDescriptiveCoating(ItemPropertiesHelper.equipment(1), "coating"));
    public static final Supplier<CurioBaseItem> LIGHTNING_COATING = ITEMS.register("lightning_coating", () -> new LightningCoating(ItemPropertiesHelper.equipment(1), "coating"));
    public static final Supplier<CurioBaseItem> FANG_COATING = ITEMS.register("fang_coating", () -> new FangCoating(ItemPropertiesHelper.equipment(1), "coating"));
    public static final Supplier<CurioBaseItem> COSMIC_COATING = ITEMS.register("cosmic_coating", () -> new CosmicCoating(ItemPropertiesHelper.equipment(1), "coating"));
    public static final Supplier<CurioBaseItem> THORN_COATING = ITEMS.register("thorn_coating", () -> new ThornCoating(ItemPropertiesHelper.equipment(1), "coating"));
    //public static final Supplier<CurioBaseItem> EXPLOSIVE_COATING = ITEMS.register("explosive_coating", () -> new ExplosiveCoating(ItemPropertiesHelper.equipment(1), "coating"));
    public static final Supplier<CurioBaseItem> TAUNT_COATING = ITEMS.register("taunt_coating", () -> new CoatingBaseItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.TAUNT_SPELL, 3))).withAttributes("coating", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.BLOCK_STRENGTH, 50, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> BASH_COATING = ITEMS.register("bash_coating", () -> new CoatingBaseItem(ItemPropertiesHelper.equipment(1), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.SHIELD_BASH_SPELL, 3))).withAttributes("coating", new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.MIN_RAGE, 10, AttributeModifier.Operation.ADD_VALUE)));
    public static final Supplier<CurioBaseItem> BLEED_COATING = ITEMS.register("bleed_coating", () -> new BleedCoating(ItemPropertiesHelper.equipment(1), "coating", SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.CONSUME_BLEED_SPELL, 1))));
    public static final Supplier<CurioBaseItem> FIRE_COATING = ITEMS.register("fire_coating", () -> new FireCoating(ItemPropertiesHelper.equipment(1), "coating"));
    public static final Supplier<CurioBaseItem> ICE_COATING = ITEMS.register("ice_coating", () -> new IceCoating(ItemPropertiesHelper.equipment(1), "coating"));

    //public static final DeferredHolder<Item, Item> REPAIR_KIT = ITEMS.register("repair_kit", () -> new Item(new Item.Properties().stacksTo(4)));

    /*public static final DeferredItem<Item> SUMMON_SWORD = ITEMS.register("summon_sword",
            () -> new SummonSwordItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_ONE, 8, -2.4f, new AttributeContainer[]{})
            ))
    );*/

    public static final DeferredItem<Item> GROUND_POUNDER = ITEMS.register("ground_pounder",
            () -> new GroundPounderItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_ONE, 13, -2.8f, new AttributeContainer[]{})
            ))
    );

    /*public static final DeferredItem<Item> MJOLNIR = ITEMS.register("mjolnir",
            () -> new MjolnirItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_TWO, 15, -2.7f, new AttributeContainer[]{})
            ))
    );*/

    /*public static final DeferredItem<Item> COSMIC_ARK = ITEMS.register("cosmic_ark",
            () -> new CosmicArkItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_TWO, 9, -2.3F, new AttributeContainer[]{new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
            ))
    );*/

    /*public static final DeferredItem<Item> FANG_SWORD = ITEMS.register("fang_sword",
            () -> new FangSwordItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_ONE, 9, -2.4F, new AttributeContainer[]{new AttributeContainer(Attributes.ARMOR, 2, AttributeModifier.Operation.ADD_VALUE)})
            ))
    );*/

    /*public static final DeferredItem<Item> VOLCANO = ITEMS.register("volcano",
            () -> new VolcanoSwordItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_THREE, 14, -2.7F, new AttributeContainer[]{new AttributeContainer(Attributes.BURNING_TIME, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
            ))
    );*/

    /*public static final DeferredItem<Item> DESERT_FURY = ITEMS.register("desert_fury",
            () -> new DesertFuryItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_TWO, 12, -2.5F, new AttributeContainer[]{new AttributeContainer(AttributeRegistry.CASTING_MOVESPEED, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
            ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.SPIN_SPELL, 1)))
    );*/

    /*public static final DeferredItem<Item> VILETHORN = ITEMS.register("vilethorn",
            () -> new VilethornItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_TWO, 18, -3.2F, new AttributeContainer[]{new AttributeContainer(Attributes.MAX_HEALTH, 4, AttributeModifier.Operation.ADD_VALUE)})
            ))
    );*/

    /*public static final DeferredItem<Item> WINGLASH = ITEMS.register("winglash",
            () -> new WinglashItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_TWO, 9, -2.6F, new AttributeContainer[]{new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.DODGE_CHANCE, 0.1, AttributeModifier.Operation.ADD_VALUE)})
            ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.SKY_STRIKE_SPELL, 1)))
    );*/

    public static final DeferredItem<Item> IRONWALL = ITEMS.register("ironwall",
            () -> new ExtendedShieldItem(new Item.Properties().stacksTo(1)
                    .attributes(ExtendedShieldItem.createAttributes(100, new AttributeContainer[]{new AttributeContainer(Attributes.ARMOR, 10, AttributeModifier.Operation.ADD_VALUE)})),
                    SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.TAUNT_SPELL, 1)))
    );

    public static final DeferredItem<Item> THORNBARK = ITEMS.register("thornbark",
            () -> new ExtendedShieldItem(new Item.Properties().stacksTo(1)
                    .attributes(ExtendedShieldItem.createAttributes(15, new AttributeContainer[]{new AttributeContainer(Attributes.ATTACK_DAMAGE, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)})),
                    SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.SHIELD_BASH_SPELL, 2)))
    );

    public static final DeferredItem<Item> FLASHGUARD = ITEMS.register("flashguard",
            () -> new ExtendedShieldItem(new Item.Properties().stacksTo(1)
                    .attributes(ExtendedShieldItem.createAttributes(5, new AttributeContainer[]{new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.DODGE_CHANCE, 0.1, AttributeModifier.Operation.ADD_VALUE)})),
                    SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.SHIELD_BASH_SPELL, 2)))
    );

    /*public static final DeferredItem<Item> SOUL_SUCKER = ITEMS.register("soul_sucker",
            () -> new SoulSuckerItem(new Item.Properties().stacksTo(1)
            ));*/


    public static final Weaponset DIAMOND_WEAPONSET = createWeaponset(ExtendedWeaponTier.DIAMOND, WeaponPower.POWER_ONE, "diamond", false);
    public static final Weaponset NETHERITE_WEAPONSET = createWeaponset(ExtendedWeaponTier.NETHERITE, WeaponPower.POWER_THREE, "netherite", false);
    public static final Weaponset IRON_WEAPONSET = createWeaponset(ExtendedWeaponTier.IRON, WeaponPower.POWER_ONE, "iron", false);
    public static final Weaponset GOLD_WEAPONSET = createWeaponset(ExtendedWeaponTier.GOLD, WeaponPower.POWER_ONE, "gold", false);
    public static final Weaponset COPPER_WEAPONSET = createWeaponset(ExtendedWeaponTier.COPPER, WeaponPower.POWER_ONE, "copper", false);
    public static final Weaponset WITHERSTEEL_WEAPONSET = createWeaponset(ExtendedWeaponTier.WITHERSTEEL, WeaponPower.POWER_THREE, "withersteel", true);
    public static final Weaponset SCULK_WEAPONSET = createWeaponset(ExtendedWeaponTier.SCULK, WeaponPower.POWER_TWO, "sculk", true);
    public static final Weaponset OBSIDIAN_WEAPONSET = createWeaponset(ExtendedWeaponTier.OBSIDIAN, WeaponPower.POWER_ONE, "obsidian", false);
    public static final Weaponset AMETHYST_WEAPONSET = createWeaponset(ExtendedWeaponTier.AMETHYST, WeaponPower.POWER_ONE, "amethyst", false);
    public static final Weaponset BLAZING_WEAPONSET = createWeaponset(ExtendedWeaponTier.BLAZING, WeaponPower.POWER_THREE, "blazing", true);
    public static final Weaponset LIVING_WEAPONSET = createWeaponset(ExtendedWeaponTier.LIVING, WeaponPower.POWER_TWO, "living", true);
    public static final Weaponset UMBRITE_WEAPONSET = createWeaponset(ExtendedWeaponTier.UMBRITE, WeaponPower.POWER_TWO, "umbrite", true);
    public static final Weaponset SILVERSTEEL_WEAPONSET = createWeaponset(ExtendedWeaponTier.SILVERSTEEL, WeaponPower.POWER_TWO, "silversteel", true);
    public static final Weaponset DWARVEN_STEEL_WEAPONSET = createWeaponset(ExtendedWeaponTier.DWARVEN_STEEL, WeaponPower.POWER_TWO, "dwarvensteel", true);

    public record Weaponset (DeferredItem warhammer,
                             DeferredItem greatsword,
                             DeferredItem halberd,
                             DeferredItem scythe,
                             DeferredItem mace,
                             DeferredItem spear,
                             DeferredItem rapier,
                             DeferredItem greataxe,
                             DeferredItem shortbow,
                             DeferredItem recurve,
                             DeferredItem longbow,
                             DeferredItem ice,
                             DeferredItem fire,
                             DeferredItem lightning,
                             DeferredItem necromancy,
                             DeferredItem arcane,
                             DeferredItem holy,
                             Optional<DeferredItem> ingot) implements Iterable<DeferredItem>{

        @Override
        public @NotNull Iterator<DeferredItem> iterator() {
            return List.of(warhammer, greatsword, halberd, scythe, mace, spear, rapier, greataxe, shortbow, recurve, longbow, ice, fire, lightning, necromancy, arcane, holy).iterator();
        }
    }

    public static Weaponset createWeaponset(ExtendedWeaponTier material, WeaponPower power, String name, boolean create_ingot) {

        final List<CQSpellDataRegistryHolder> iceSpells = List.of(
                new CQSpellDataRegistryHolder(SpellRegistry.ICICLE_SPELL, 6),
                new CQSpellDataRegistryHolder(SpellRegistry.SNOWBALL_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.RAY_OF_FROST_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.ICE_TOMB_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.ICE_SPIKES_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.FROSTWAVE_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.ICE_BLOCK_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.FROST_STEP_SPELL, 1)
        );

        final List<CQSpellDataRegistryHolder> fireSpells = List.of(
                new CQSpellDataRegistryHolder(SpellRegistry.FIREBOLT_SPELL, 6),
                new CQSpellDataRegistryHolder(SpellRegistry.BURNING_DASH_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.HEAT_SURGE_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.SCORCH_SPELL, -1),
                new CQSpellDataRegistryHolder(SpellRegistry.FIREBALL_SPELL, -1),
                new CQSpellDataRegistryHolder(SpellRegistry.WALL_OF_FIRE_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.FLAMING_BARRAGE_SPELL, 0),
                new CQSpellDataRegistryHolder(SpellRegistry.RAISE_HELL_SPELL, 1)
        );

        final List<CQSpellDataRegistryHolder> lightningSpells = List.of(
                new CQSpellDataRegistryHolder(SpellRegistry.BALL_LIGHTNING_SPELL, 6),
                new CQSpellDataRegistryHolder(SpellRegistry.SHOCKWAVE_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.ELECTROCUTE_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.LIGHTNING_BOLT_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.CHAIN_LIGHTNING_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.CHARGE_SPELL, -3),
                new CQSpellDataRegistryHolder(SpellRegistry.THUNDERSTORM_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.LIGHTNING_LANCE_SPELL, 2)
        );

        final List<CQSpellDataRegistryHolder> necromancySpells = List.of(
                new CQSpellDataRegistryHolder(SpellRegistry.FANG_STRIKE_SPELL, 6),
                new CQSpellDataRegistryHolder(SpellRegistry.RAISE_DEAD_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.DEVOUR_SPELL, 3),
                new CQSpellDataRegistryHolder(SpellRegistry.SACRIFICE_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.SUMMON_VEX_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.BLIGHT_SPELL, -1),
                new CQSpellDataRegistryHolder(SpellRegistry.BLOOD_SLASH_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.HEARTSTOP_SPELL, 1)
        );

        final List<CQSpellDataRegistryHolder> arcaneSpells = List.of(
                new CQSpellDataRegistryHolder(SpellRegistry.MAGIC_MISSILE_SPELL, 6),
                new CQSpellDataRegistryHolder(SpellRegistry.GUST_SPELL, 3),
                new CQSpellDataRegistryHolder(SpellRegistry.STARFALL_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.INVISIBILITY_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.ELDRITCH_BLAST_SPELL, -4),
                new CQSpellDataRegistryHolder(SpellRegistry.SLOW_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.TELEKINESIS_SPELL, 1),
                new CQSpellDataRegistryHolder(SpellRegistry.TELEPORT_SPELL, 2)
        );

        final List<CQSpellDataRegistryHolder> holySpells = List.of(
                new CQSpellDataRegistryHolder(SpellRegistry.GUIDING_BOLT_SPELL, 6),
                new CQSpellDataRegistryHolder(SpellRegistry.BLESSING_OF_LIFE_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.SHIELD_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.FORTIFY_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.SUNBEAM_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.CLEANSE_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.HEAL_SPELL, 2),
                new CQSpellDataRegistryHolder(SpellRegistry.HASTE_SPELL, 2)
        );

        int spellCount = power.power() * 2;
        CQSpellDataRegistryHolder[] iceSpellsForPower = new CQSpellDataRegistryHolder[spellCount];
        CQSpellDataRegistryHolder[] holySpellsForPower = new CQSpellDataRegistryHolder[spellCount];
        CQSpellDataRegistryHolder[] arcaneSpellsForPower = new CQSpellDataRegistryHolder[spellCount];
        CQSpellDataRegistryHolder[] necromancySpellsForPower = new CQSpellDataRegistryHolder[spellCount];
        CQSpellDataRegistryHolder[] lightningSpellsForPower = new CQSpellDataRegistryHolder[spellCount];
        CQSpellDataRegistryHolder[] fireSpellsForPower = new CQSpellDataRegistryHolder[spellCount];
        for (int i = 0; i < spellCount; i++) {
            iceSpellsForPower[i] = new CQSpellDataRegistryHolder(iceSpells.get(i).getSpellSupplier(), iceSpells.get(i).getLevel() + (power.power() * 2));
            fireSpellsForPower[i] = new CQSpellDataRegistryHolder(fireSpells.get(i).getSpellSupplier(), fireSpells.get(i).getLevel() + (power.power() * 2));
            lightningSpellsForPower[i] = new CQSpellDataRegistryHolder(lightningSpells.get(i).getSpellSupplier(), lightningSpells.get(i).getLevel() + (power.power() * 2));
            necromancySpellsForPower[i] = new CQSpellDataRegistryHolder(necromancySpells.get(i).getSpellSupplier(), necromancySpells.get(i).getLevel() + (power.power() * 2));
            arcaneSpellsForPower[i] = new CQSpellDataRegistryHolder(arcaneSpells.get(i).getSpellSupplier(), arcaneSpells.get(i).getLevel() + (power.power() * 2));
            holySpellsForPower[i] = new CQSpellDataRegistryHolder(holySpells.get(i).getSpellSupplier(), holySpells.get(i).getLevel() + (power.power() * 2));
        }

        var warhammer = ITEMS.register(name + "_warhammer",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.WARHAMMER.attackDamage(), WeaponType.WARHAMMER.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.ARMOR, 6 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.STUN_SPELL, power.power())))
        );

        var greatsword = ITEMS.register(name + "_greatsword",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.GREATSWORD.attackDamage(), WeaponType.GREATSWORD.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.MAX_HEALTH, 4 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.RUPTURE_SPELL, power.power())))
        );

        var halberd = ITEMS.register(name + "_halberd",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.HALBERD.attackDamage(), WeaponType.HALBERD.attackSpeed(), new AttributeContainer[]{new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.DODGE_CHANCE, 0.02 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.UPPERCUT_SPELL, power.power())))
        );

        var scythe = ITEMS.register(name + "_scythe",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.SCYTHE.attackDamage(), WeaponType.SCYTHE.attackSpeed(), new AttributeContainer[]{new AttributeContainer(AttributeRegistry.SUMMON_DAMAGE, 0.4 * material.getMult(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.REAP_SPELL, power.power())))
        );

        var mace = ITEMS.register(name + "_mace",
                () -> new ExtendedMaceItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.MACE.attackDamage(), WeaponType.MACE.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.ATTACK_KNOCKBACK, 1 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.WRECKING_BALL_SPELL, power.power())))
        );

        var spear = ITEMS.register(name + "_spear",
                () -> new SpearItem(material, power, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.SPEAR.attackDamage(), WeaponType.SPEAR.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.ENTITY_INTERACTION_RANGE, 0.5 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.SKEWER_SPELL, power.power())))
        );

        var rapier = ITEMS.register(name + "_rapier",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power,WeaponType.RAPIER.attackDamage(), WeaponType.RAPIER.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.1 * material.getMult(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.RIPOSTE_SPELL, power.power())))
        );

        var greataxe = ITEMS.register(name + "_greataxe",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power,WeaponType.GREATAXE.attackDamage(), WeaponType.GREATAXE.attackSpeed(), new AttributeContainer[]{new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_ON_HIT, 1 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.BERSERK_SPELL, power.power())))
        );

        var shortbow = ITEMS.register(name + "_shortbow",
                () -> new ExtendedBowItem(new Item.Properties().durability(material.uses).attributes(ExtendedBowItem
                        .createAttributes(material, power,WeaponType.SHORTBOW.attackDamage(), WeaponType.SHORTBOW.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.1 * material.getMult(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.RAPID_FIRE_SPELL, power.power())))
        );

        var recurve = ITEMS.register(name + "_recurve_bow",
                () -> new ExtendedBowItem(new Item.Properties().durability(material.uses).attributes(ExtendedBowItem
                        .createAttributes(material, power,WeaponType.RECURVE.attackDamage(), WeaponType.RECURVE.attackSpeed(), new AttributeContainer[]{new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.DODGE_CHANCE, 0.02 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.BARRAGE_SPELL, power.power())))
        );

        var longbow = ITEMS.register(name + "_longbow",
                () -> new ExtendedBowItem(new Item.Properties().durability(material.uses).attributes(ExtendedBowItem
                        .createAttributes(material, power,WeaponType.LONGBOW.attackDamage(), WeaponType.LONGBOW.attackSpeed(), new AttributeContainer[]{new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.MOMENTUM_ON_HIT, 1 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.PIERCING_ARROW_SPELL, power.power())))
        );

        var ice = ITEMS.register(name + "_ice_staff",
                () -> new MagicStaffItem(material, new Item.Properties().attributes(MagicStaffItem
                        .createAttributes(material, power,WeaponType.STAFF.attackDamage(), WeaponType.STAFF.attackSpeed(), new AttributeContainer[]{}) // attributes? FIXME
                ), iceSpellsForPower)
        );

        var fire = ITEMS.register(name + "_fire_staff",
                () -> new MagicStaffItem(material, new Item.Properties().attributes(MagicStaffItem
                        .createAttributes(material, power,WeaponType.STAFF.attackDamage(), WeaponType.STAFF.attackSpeed(), new AttributeContainer[]{}) // attributes? FIXME
                ), fireSpellsForPower)
        );

        var lightning = ITEMS.register(name + "_lightning_staff",
                () -> new MagicStaffItem(material, new Item.Properties().attributes(MagicStaffItem
                        .createAttributes(material, power,WeaponType.STAFF.attackDamage(), WeaponType.STAFF.attackSpeed(), new AttributeContainer[]{}) // attributes? FIXME
                ), lightningSpellsForPower)
        );

        var necromancy = ITEMS.register(name + "_necromancy_staff",
                () -> new MagicStaffItem(material, new Item.Properties().attributes(MagicStaffItem
                        .createAttributes(material, power,WeaponType.STAFF.attackDamage(), WeaponType.STAFF.attackSpeed(), new AttributeContainer[]{new AttributeContainer(AttributeRegistry.SUMMON_DAMAGE, 1 + (0.2 * material.getMult()), AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
                ), necromancySpellsForPower)
        );

        var arcane = ITEMS.register(name + "_arcane_staff",
                () -> new MagicStaffItem(material, new Item.Properties().attributes(MagicStaffItem
                        .createAttributes(material, power,WeaponType.STAFF.attackDamage(), WeaponType.STAFF.attackSpeed(), new AttributeContainer[]{}) // attributes? FIXME
                ), arcaneSpellsForPower)
        );

        var holy = ITEMS.register(name + "_holy_staff",
                () -> new MagicStaffItem(material, new Item.Properties().attributes(MagicStaffItem
                        .createAttributes(material, power,WeaponType.STAFF.attackDamage(), WeaponType.STAFF.attackSpeed(), new AttributeContainer[]{}) // attributes? FIXME
                ), holySpellsForPower)
        );

        Optional<DeferredItem> ingot = Optional.empty();

        if (create_ingot) {
            var val = ITEMS.register(name + "_ingot",
                    () -> new Item(new Item.Properties())
            );
            ingot = Optional.of(val);
            ItemModelDataGenerator.toRegister.add(generator -> generator.atlasItem(val));
        }

        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(warhammer, generator.atlas3DItem(warhammer, ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/base_warhammer_handheld"))));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(greatsword, generator.atlasLargeItem(greatsword)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(halberd, generator.atlasLargeItem(halberd)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(scythe, generator.atlasLargeItem(scythe)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.maceItem(mace));
        ItemModelDataGenerator.toRegister.add(generator -> generator.spearAtlasTransform(spear, generator.atlasLargeItem(spear)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasItem(rapier));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(greataxe, generator.atlasLargeItem(greataxe)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.bowItemSeparateTransform(longbow, BowType.LONGBOW));
        ItemModelDataGenerator.toRegister.add(generator -> generator.bowItemSeparateTransform(recurve, BowType.RECURVE_BOW));
        ItemModelDataGenerator.toRegister.add(generator -> generator.bowItemStandalone(shortbow, BowType.SHORTBOW));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(ice, generator.atlasLargeItem(ice)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(fire, generator.atlasLargeItem(fire)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(lightning, generator.atlasLargeItem(lightning)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(necromancy, generator.atlasLargeItem(necromancy)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(arcane, generator.atlasLargeItem(arcane)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(holy, generator.atlasLargeItem(holy)));

        var weaponset = new Weaponset(warhammer, greatsword, halberd, scythe, mace, spear, rapier, greataxe, shortbow, recurve, longbow, ice, fire, lightning, necromancy, arcane, holy, ingot);
        WEAPONSETS.add(weaponset);

        return weaponset;
    }





}
