package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.ItemModelDataGenerator;
import com.example.cqsarmory.items.*;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.registries.ComponentRegistry;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static net.neoforged.neoforge.common.util.AttributeUtil.BASE_ATTACK_DAMAGE_ID;
import static net.neoforged.neoforge.common.util.AttributeUtil.BASE_ATTACK_SPEED_ID;

public class ItemRegistry {

    public static final List<Weaponset> WEAPONSETS = new ArrayList<>();

    public static ItemAttributeModifiers createCustomAttributes(Tier tier, float attackDamage, float attackSpeed, float maxHealth) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, (double)((float)attackDamage + tier.getAttackDamageBonus()), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, (double)attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CqsArmory.MODID);

    public static final DeferredHolder<Item, Item> REPAIR_KIT = ITEMS.register("repair_kit", () -> new Item(new Item.Properties().stacksTo(4)));

    public static final DeferredItem<Item> SUMMON_SWORD = ITEMS.register("summon_sword",
            () -> new SummonSwordItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_ONE, 8, -2.1f, new AttributeContainer[]{})
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
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_THREE, 14, -2.6F, new AttributeContainer[]{new AttributeContainer(Attributes.BURNING_TIME, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
            ))
    );

    public static final DeferredItem<Item> DESERT_FURY = ITEMS.register("desert_fury",
            () -> new DesertFuryItem(ExtendedWeaponTier.CUSTOM, new Item.Properties().component(ComponentRegistry.CASTING_IMPLEMENT.get(), Unit.INSTANCE).stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.CUSTOM, WeaponPower.POWER_TWO, 12, -2.5F, new AttributeContainer[]{new AttributeContainer(AttributeRegistry.CASTING_MOVESPEED, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
            ), SpellDataRegistryHolder.of(new SpellDataRegistryHolder(CQSpellRegistry.SPIN_SPELL, 1)))
    );

    public static final DeferredItem<Item> LEMON_SHIELD = ITEMS.register("lemon_shield",
            () -> new ShieldItem(new Item.Properties().stacksTo(1))
    );

    public static final DeferredItem<Item> SOUL_SUCKER = ITEMS.register("soul_sucker",
            () -> new SoulSuckerItem(new Item.Properties().stacksTo(1)
            ));



    public static final DeferredItem<Item> IRON_WARHAMMER = ITEMS.register("iron_warhammer",
            () -> new SwordItem(Tiers.IRON, new Item.Properties().attributes(SwordItem
                    .createAttributes(Tiers.IRON, 10, -3.2F)
            ))
    );
    public static final DeferredItem<Item> IRON_GREATSWORD = ITEMS.register("iron_greatsword",
            () -> new SwordItem(Tiers.IRON, new Item.Properties().attributes(SwordItem
                    .createAttributes(Tiers.IRON, 6, -2.8F)
            ))
    );
    public static final DeferredItem<Item> IRON_HALBERD = ITEMS.register("iron_halberd",
            () -> new SwordItem(Tiers.IRON, new Item.Properties().attributes(SwordItem
                    .createAttributes(Tiers.IRON, 5, -2.6F)
            ))
    );
    public static final DeferredItem<Item> IRON_SCYTHE = ITEMS.register("iron_scythe",
            () -> new SwordItem(Tiers.IRON, new Item.Properties().attributes(SwordItem
                    .createAttributes(Tiers.IRON, 5, -2.6F)
            ))
    );
    public static final DeferredItem<Item> IRON_MACE = ITEMS.register("iron_mace",
            () -> new SwordItem(Tiers.IRON, new Item.Properties().attributes(SwordItem
                    .createAttributes(Tiers.IRON, 4, -2.4F)
            ))
    );
    public static final DeferredItem<Item> IRON_SPEAR = ITEMS.register("iron_spear",
            () -> new SwordItem(Tiers.IRON, new Item.Properties().attributes(SwordItem
                    .createAttributes(Tiers.IRON, 4, -2.4F)
            ))
    );
    public static final DeferredItem<Item> IRON_RAPIER = ITEMS.register("iron_rapier",
            () -> new SwordItem(Tiers.IRON, new Item.Properties().attributes(SwordItem
                    .createAttributes(Tiers.IRON, 2, -2.0F)
            ))
    );

    public static final Weaponset DIAMOND_WEAPONSET = createWeaponset(ExtendedWeaponTier.DIAMOND, WeaponPower.POWER_ONE, "diamond", false);
    public static final Weaponset NETHERITE_WEAPONSET = createWeaponset(ExtendedWeaponTier.NETHERITE, WeaponPower.POWER_ONE, "netherite", false);
    public static final Weaponset GOLD_WEAPONSET = createWeaponset(ExtendedWeaponTier.GOLD, WeaponPower.POWER_ONE, "gold", false);
    public static final Weaponset COPPER_WEAPONSET = createWeaponset(ExtendedWeaponTier.COPPER, WeaponPower.POWER_ONE, "copper", false);
    public static final Weaponset WITHERSTEEL_WEAPONSET = createWeaponset(ExtendedWeaponTier.WITHERSTEEL, WeaponPower.POWER_THREE, "withersteel", true);

    public record Weaponset (DeferredItem warhammer,
                             DeferredItem greatsword,
                             DeferredItem halberd,
                             DeferredItem scythe,
                             DeferredItem mace,
                             DeferredItem spear,
                             DeferredItem rapier,
                             Optional<DeferredItem> ingot) implements Iterable<DeferredItem>{

        @Override
        public @NotNull Iterator<DeferredItem> iterator() {
            return List.of(warhammer, greatsword, halberd, scythe, mace, spear, rapier).iterator();
        }
    }

    public static Weaponset createWeaponset(ExtendedWeaponTier material, WeaponPower power, String name, boolean create_ingot) {

        var warhammer = ITEMS.register(name + "_warhammer",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.WARHAMMER.attackDamage(), WeaponType.WARHAMMER.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.ARMOR, 6 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ))
        );

        var greatsword = ITEMS.register(name + "_greatsword",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.GREATSWORD.attackDamage(), WeaponType.GREATSWORD.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.MAX_HEALTH, 4 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ))
        );

        var halberd = ITEMS.register(name + "_halberd",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.HALBERD.attackDamage(), WeaponType.HALBERD.attackSpeed(), new AttributeContainer[]{})
                ))
        );

        var scythe = ITEMS.register(name + "_scythe",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.SCYTHE.attackDamage(), WeaponType.SCYTHE.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.SWEEPING_DAMAGE_RATIO, 0.5 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ))
        );

        var mace = ITEMS.register(name + "_mace",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.MACE.attackDamage(), WeaponType.MACE.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.ATTACK_KNOCKBACK, 1 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ))
        );

        var spear = ITEMS.register(name + "_spear",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power, WeaponType.SPEAR.attackDamage(), WeaponType.SPEAR.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.ENTITY_INTERACTION_RANGE, 0.5 * material.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ))
        );

        var rapier = ITEMS.register(name + "_rapier",
                () -> new ExtendedWeaponItem(material, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(material, power,WeaponType.RAPIER.attackDamage(), WeaponType.RAPIER.attackSpeed(), new AttributeContainer[]{new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.1 * material.getMult(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
                ))
        );

        Optional<DeferredItem> ingot = Optional.empty();

        if (create_ingot) {
            var val = ITEMS.register(name + "_ingot",
                    () -> new Item(new Item.Properties())
            );
            ingot = Optional.of(val);
            ItemModelDataGenerator.toRegister.add(generator -> generator.atlasItem(val));
        }

        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasItem(warhammer));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasItem(greatsword));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasItem(halberd));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasItem(scythe));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasItem(mace));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasItem(spear));
        ItemModelDataGenerator.toRegister.add(generator -> generator.atlasItem(rapier));

        var weaponset = new Weaponset(warhammer, greatsword, halberd, scythe, mace, spear, rapier, ingot);
        WEAPONSETS.add(weaponset);

        return weaponset;
    }





}
