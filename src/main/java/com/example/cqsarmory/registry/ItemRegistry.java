package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.ItemModelDataGenerator;
import com.example.cqsarmory.items.*;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

import static net.neoforged.neoforge.common.util.AttributeUtil.BASE_ATTACK_DAMAGE_ID;
import static net.neoforged.neoforge.common.util.AttributeUtil.BASE_ATTACK_SPEED_ID;

public class ItemRegistry {

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
    public static final DeferredItem<Item> SUMMON_SWORD = ITEMS.register("summon_sword",
            () -> new SummonSwordItem(Tiers.NETHERITE, new Item.Properties().attributes(SwordItem
                    .createAttributes(Tiers.NETHERITE, 5, -2.4F)
            ))
    );

    public static final DeferredItem<Item> GROUND_POUNDER = ITEMS.register("ground_pounder",
            () -> new GroundPounderItem(Tiers.NETHERITE, new Item.Properties().attributes(SwordItem
                    .createAttributes(Tiers.NETHERITE, 10, -3.1F)
            ))
    );

    public static final DeferredItem<Item> MJOLNIR = ITEMS.register("mjolnir",
            () -> new MjolnirItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1).attributes(SwordItem
                    .createAttributes(Tiers.NETHERITE, 12, -3.0F)
            ))
    );

    public static final DeferredItem<Item> COSMIC_ARK = ITEMS.register("cosmic_ark",
            () -> new CosmicArkItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.NETHERITE, 12, -2.5F, new AttributeContainer[]{new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
            ))
    );

    public static final DeferredItem<Item> FANG_SWORD = ITEMS.register("fang_sword",
            () -> new FangSwordItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.NETHERITE, 6, -2.7F, new AttributeContainer[]{new AttributeContainer(Attributes.ARMOR, 2, AttributeModifier.Operation.ADD_VALUE)})
            ))
    );

    public static final DeferredItem<Item> VOLCANO = ITEMS.register("volcano",
            () -> new VolcanoSwordItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.NETHERITE, 11, -2.7F, new AttributeContainer[]{new AttributeContainer(Attributes.BURNING_TIME, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
            ))
    );

    public static final DeferredItem<Item> DESERT_FURY = ITEMS.register("desert_fury",
            () -> new DesertFuryItem(Tiers.NETHERITE, new Item.Properties().stacksTo(1).attributes(ExtendedWeaponItem
                    .createAttributes(ExtendedWeaponTier.NETHERITE, 11, -2.7F, new AttributeContainer[]{new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
            ))
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

    public static final Weaponset DIAMOND_WEAPONSET = createWeaponset(ExtendedWeaponTier.DIAMOND, "diamond", false);
    public static final Weaponset NETHERITE_WEAPONSET = createWeaponset(ExtendedWeaponTier.NETHERITE, "netherite", false);
    public static final Weaponset GOLD_WEAPONSET = createWeaponset(ExtendedWeaponTier.GOLD, "gold", false);
    public static final Weaponset COPPER_WEAPONSET = createWeaponset(ExtendedWeaponTier.COPPER, "copper", false);
    public static final Weaponset WITHERSTEEL_WEAPONSET = createWeaponset(ExtendedWeaponTier.WITHERSTEEL, "withersteel", true);

    public record Weaponset (DeferredItem warhammer,
                             DeferredItem greatsword,
                             DeferredItem halberd,
                             DeferredItem scythe,
                             DeferredItem mace,
                             DeferredItem spear,
                             DeferredItem rapier,
                             Optional<DeferredItem> ingot){

    }

    public static Weaponset createWeaponset(ExtendedWeaponTier tier, String name, boolean create_ingot) {

        var warhammer = ITEMS.register(name + "_warhammer",
                () -> new ExtendedWeaponItem(tier, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(tier, 10, -3.2F, new AttributeContainer[]{new AttributeContainer(Attributes.ARMOR, 6 * tier.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ))
        );

        var greatsword = ITEMS.register(name + "_greatsword",
                () -> new ExtendedWeaponItem(tier, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(tier, 6, -2.8F, new AttributeContainer[]{new AttributeContainer(Attributes.MAX_HEALTH, 4 * tier.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ))
        );

        var halberd = ITEMS.register(name + "_halberd",
                () -> new ExtendedWeaponItem(tier, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(tier, 5, -2.6F, new AttributeContainer[]{})
                ))
        );

        var scythe = ITEMS.register(name + "_scythe",
                () -> new ExtendedWeaponItem(tier, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(tier, 5, -2.6F, new AttributeContainer[]{new AttributeContainer(Attributes.SWEEPING_DAMAGE_RATIO, 0.5 * tier.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ))
        );

        var mace = ITEMS.register(name + "_mace",
                () -> new ExtendedWeaponItem(tier, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(tier, 4, -2.4F, new AttributeContainer[]{new AttributeContainer(Attributes.ATTACK_KNOCKBACK, 1 * tier.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ))
        );

        var spear = ITEMS.register(name + "_spear",
                () -> new ExtendedWeaponItem(tier, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(tier, 4, -2.4F, new AttributeContainer[]{new AttributeContainer(Attributes.ENTITY_INTERACTION_RANGE, 0.5 * tier.getMult(), AttributeModifier.Operation.ADD_VALUE)})
                ))
        );

        var rapier = ITEMS.register(name + "_rapier",
                () -> new ExtendedWeaponItem(tier, new Item.Properties().attributes(ExtendedWeaponItem
                        .createAttributes(tier, 2, -2.0F, new AttributeContainer[]{new AttributeContainer(Attributes.MOVEMENT_SPEED, 0.1 * tier.getMult(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE)})
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

        return new Weaponset(warhammer, greatsword, halberd, scythe, mace, spear, rapier, ingot);
    }





}
