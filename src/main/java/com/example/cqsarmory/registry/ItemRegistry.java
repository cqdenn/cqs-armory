package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.ItemModelDataGenerator;
import com.example.cqsarmory.items.*;
import com.example.cqsarmory.items.armor.*;
import com.example.cqsarmory.items.weapons.*;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
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

public class ItemRegistry {
    public static final List<Weaponset> WEAPONSETS = new ArrayList<>();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CqsArmory.MODID);

    public static final DeferredHolder<Item, Item> HUNTER_HELMET = ITEMS.register("hunter_helmet", () -> new HunterArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(22))));
    public static final DeferredHolder<Item, Item> HUNTER_CHESTPLATE = ITEMS.register("hunter_chestplate", () -> new HunterArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(22))));
    public static final DeferredHolder<Item, Item> HUNTER_LEGGINGS = ITEMS.register("hunter_leggings", () -> new HunterArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(22))));
    public static final DeferredHolder<Item, Item> HUNTER_BOOTS = ITEMS.register("hunter_boots", () -> new HunterArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(22)), new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));

    public static final DeferredHolder<Item, Item> TRACKER_HELMET = ITEMS.register("tracker_helmet", () -> new TrackerArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(26))));
    public static final DeferredHolder<Item, Item> TRACKER_CHESTPLATE = ITEMS.register("tracker_chestplate", () -> new TrackerArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(26))));
    public static final DeferredHolder<Item, Item> TRACKER_LEGGINGS = ITEMS.register("tracker_leggings", () -> new TrackerArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(26))));
    public static final DeferredHolder<Item, Item> TRACKER_BOOTS = ITEMS.register("tracker_boots", () -> new TrackerArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(26)), new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));

    public static final DeferredHolder<Item, Item> MARKSMAN_HELMET = ITEMS.register("marksman_helmet", () -> new MarksmanArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(37))));
    public static final DeferredHolder<Item, Item> MARKSMAN_CHESTPLATE = ITEMS.register("marksman_chestplate", () -> new MarksmanArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(37))));
    public static final DeferredHolder<Item, Item> MARKSMAN_LEGGINGS = ITEMS.register("marksman_leggings", () -> new MarksmanArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(37))));
    public static final DeferredHolder<Item, Item> MARKSMAN_BOOTS = ITEMS.register("marksman_boots", () -> new MarksmanArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(37)), new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)));

    public static final DeferredHolder<Item, Item> WARRIOR_HELMET = ITEMS.register("warrior_helmet", () -> new WarriorArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(22)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_DAMAGE, 0.01, AttributeModifier.Operation.ADD_VALUE)));
    public static final DeferredHolder<Item, Item> WARRIOR_CHESTPLATE = ITEMS.register("warrior_chestplate", () -> new WarriorArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(22)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_DAMAGE, 0.01, AttributeModifier.Operation.ADD_VALUE)));
    public static final DeferredHolder<Item, Item> WARRIOR_LEGGINGS = ITEMS.register("warrior_leggings", () -> new WarriorArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(22)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_DAMAGE, 0.01, AttributeModifier.Operation.ADD_VALUE)));
    public static final DeferredHolder<Item, Item> WARRIOR_BOOTS = ITEMS.register("warrior_boots", () -> new WarriorArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(22)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_SPEED, 0.01, AttributeModifier.Operation.ADD_VALUE)));

    public static final DeferredHolder<Item, Item> SOLDIER_HELMET = ITEMS.register("soldier_helmet", () -> new SoldierArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(26)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_DAMAGE, 0.01, AttributeModifier.Operation.ADD_VALUE)));
    public static final DeferredHolder<Item, Item> SOLDIER_CHESTPLATE = ITEMS.register("soldier_chestplate", () -> new SoldierArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(26)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_DAMAGE, 0.01, AttributeModifier.Operation.ADD_VALUE)));
    public static final DeferredHolder<Item, Item> SOLDIER_LEGGINGS = ITEMS.register("soldier_leggings", () -> new SoldierArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(26)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_DAMAGE, 0.01, AttributeModifier.Operation.ADD_VALUE)));
    public static final DeferredHolder<Item, Item> SOLDIER_BOOTS = ITEMS.register("soldier_boots", () -> new SoldierArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(26)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_SPEED, 0.01, AttributeModifier.Operation.ADD_VALUE)));

    public static final DeferredHolder<Item, Item> CHAMPION_HELMET = ITEMS.register("champion_helmet", () -> new ChampionArmorItem(ArmorItem.Type.HELMET, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.HELMET.getDurability(37)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_DAMAGE, 0.02, AttributeModifier.Operation.ADD_VALUE)));
    public static final DeferredHolder<Item, Item> CHAMPION_CHESTPLATE = ITEMS.register("champion_chestplate", () -> new ChampionArmorItem(ArmorItem.Type.CHESTPLATE, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.CHESTPLATE.getDurability(37)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_DAMAGE, 0.02, AttributeModifier.Operation.ADD_VALUE)));
    public static final DeferredHolder<Item, Item> CHAMPION_LEGGINGS = ITEMS.register("champion_leggings", () -> new ChampionArmorItem(ArmorItem.Type.LEGGINGS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.LEGGINGS.getDurability(37)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_DAMAGE, 0.02, AttributeModifier.Operation.ADD_VALUE)));
    public static final DeferredHolder<Item, Item> CHAMPION_BOOTS = ITEMS.register("champion_boots", () -> new ChampionArmorItem(ArmorItem.Type.BOOTS, ItemPropertiesHelper.equipment(1).durability(ArmorItem.Type.BOOTS.getDurability(37)), new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.RAGE_SPEED, 0.02, AttributeModifier.Operation.ADD_VALUE)));


    public static final DeferredHolder<Item, Item> REPAIR_KIT = ITEMS.register("repair_kit", () -> new Item(new Item.Properties().stacksTo(4)));

    public static final DeferredItem<Item> SUMMON_SWORD = ITEMS.register("summon_sword",
            () -> new SummonSwordItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_ONE, 8, -2.4f, new AttributeContainer[]{})
            ))
    );

    public static final DeferredItem<Item> GROUND_POUNDER = ITEMS.register("ground_pounder",
            () -> new GroundPounderItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_ONE, 13, -2.8f, new AttributeContainer[]{})
            ))
    );

    public static final DeferredItem<Item> MJOLNIR = ITEMS.register("mjolnir",
            () -> new MjolnirItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_TWO, 15, -2.7f, new AttributeContainer[]{})
            ))
    );

    public static final DeferredItem<Item> COSMIC_ARK = ITEMS.register("cosmic_ark",
            () -> new CosmicArkItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_TWO, 9, -2.3F, new AttributeContainer[]{new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
            ))
    );

    public static final DeferredItem<Item> FANG_SWORD = ITEMS.register("fang_sword",
            () -> new FangSwordItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_ONE, 9, -2.4F, new AttributeContainer[]{new AttributeContainer(Attributes.ARMOR, 2, AttributeModifier.Operation.ADD_VALUE)})
            ))
    );

    public static final DeferredItem<Item> VOLCANO = ITEMS.register("volcano",
            () -> new VolcanoSwordItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_THREE, 14, -2.7F, new AttributeContainer[]{new AttributeContainer(Attributes.BURNING_TIME, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
            ))
    );

    public static final DeferredItem<Item> DESERT_FURY = ITEMS.register("desert_fury",
            () -> new DesertFuryItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_TWO, 12, -2.5F, new AttributeContainer[]{new AttributeContainer(AttributeRegistry.CASTING_MOVESPEED, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
            ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.SPIN_SPELL, 1)))
    );

    public static final DeferredItem<Item> VILETHORN = ITEMS.register("vilethorn",
            () -> new VilethornItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_TWO, 18, -3.2F, new AttributeContainer[]{new AttributeContainer(Attributes.MAX_HEALTH, 4, AttributeModifier.Operation.ADD_VALUE)})
            ))
    );

    public static final DeferredItem<Item> WINGLASH = ITEMS.register("winglash",
            () -> new WinglashItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_TWO, 9, -2.6F, new AttributeContainer[]{new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.DODGE_CHANCE, 0.1, AttributeModifier.Operation.ADD_VALUE)})
            ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.SKY_STRIKE_SPELL, 1)))
    );

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

    public static final DeferredItem<Item> LONGBOW = ITEMS.register("longbow",
            () -> new ExtendedBowItem(new Item.Properties().stacksTo(1)
                    .attributes(ExtendedBowItem.createAttributes(12, 0.8f, new AttributeContainer[]{new AttributeContainer(com.example.cqsarmory.registry.AttributeRegistry.MOMENTUM_ON_HIT, 1, AttributeModifier.Operation.ADD_VALUE)})),
                    SpellDataRegistryHolder.of(/*add spell FIXME*/))
    );

    public static final DeferredItem<Item> SOUL_SUCKER = ITEMS.register("soul_sucker",
            () -> new SoulSuckerItem(new Item.Properties().stacksTo(1)
            ));


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
                             Optional<DeferredItem> ingot) implements Iterable<DeferredItem>{

        @Override
        public @NotNull Iterator<DeferredItem> iterator() {
            return List.of(warhammer, greatsword, halberd, scythe, mace, spear, rapier, greataxe).iterator();
        }
    }

    public static Weaponset createWeaponset(ExtendedWeaponTier material, WeaponPower power, String name, boolean create_ingot) {

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
                        .createAttributes(material, power, WeaponType.SCYTHE.attackDamage(), WeaponType.SCYTHE.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.SWEEPING_DAMAGE_RATIO, 0.5 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.REAP_SPELL, power.power())))
        );

        var mace = ITEMS.register(name + "_mace",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.MACE.attackDamage(), WeaponType.MACE.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.ATTACK_KNOCKBACK, 1 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.WRECKING_BALL_SPELL, power.power())))
        );

        var spear = ITEMS.register(name + "_spear",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
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
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(mace, generator.atlasLargeItem(mace)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(spear, generator.atlasLargeItem(spear)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(rapier, generator.atlasItem(rapier)));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasTransform(greataxe, generator.atlasLargeItem(greataxe)));

        var weaponset = new Weaponset(warhammer, greatsword, halberd, scythe, mace, spear, rapier, greataxe, ingot);
        WEAPONSETS.add(weaponset);

        return weaponset;
    }





}
