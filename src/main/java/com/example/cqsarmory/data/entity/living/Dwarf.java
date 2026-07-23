package com.example.cqsarmory.data.entity.living;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.items.MagicStaffItem;
import com.example.cqsarmory.registry.CQSpellRegistry;
import com.example.cqsarmory.registry.EntityRegistry;
import com.example.cqsarmory.registry.SoundRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.NeutralWizard;
import io.redspace.ironsspellbooks.entity.mobs.goals.PatrolNearLocationGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.WarlockAttackGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.WizardAttackGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.WizardRecoverGoal;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Dwarf extends NeutralWizard {

    public static final ResourceLocation modelResource = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "geo/dwarf.geo.json");
    private final WizardAttackGoal bowGoal = new WizardAttackGoal(this, 1.1f, 50, 100)
            .setSpells(
                    List.of(CQSpellRegistry.RAPID_FIRE_SPELL.get(), CQSpellRegistry.PIERCING_ARROW_SPELL.get(), CQSpellRegistry.BARRAGE_SPELL.get()),
                    List.of(),
                    List.of(),
                    List.of()
            )
            .setSpellQuality(0.5f, 0.5f)
            .setDrinksPotions();
    private final WizardAttackGoal meleeGoal = new WarlockAttackGoal(this, 1.1f, 50, 100)
            .setSpells(
                    List.of(CQSpellRegistry.CHAIN_WHIP_SPELL.get(), CQSpellRegistry.FLANK_STEP_SPELL.get(), CQSpellRegistry.SKEWER_SPELL.get()),
                    List.of(CQSpellRegistry.SPIN_SPELL.get(), CQSpellRegistry.RIPOSTE_SPELL.get(), CQSpellRegistry.STUN_SPELL.get()),
                    List.of(),
                    List.of()
            )
            .setSpellQuality(0.5f, 0.5f)
            .setDrinksPotions();
    private final WizardAttackGoal magicGoal = new WizardAttackGoal(this, 1.1f, 50, 100)
            .setSpells(
                    List.of(SpellRegistry.FIREBOLT_SPELL.get(), SpellRegistry.BALL_LIGHTNING_SPELL.get(), SpellRegistry.ICICLE_SPELL.get()),
                    List.of(SpellRegistry.GUST_SPELL.get(), SpellRegistry.INVISIBILITY_SPELL.get()),
                    List.of(),
                    List.of(SpellRegistry.HEAL_SPELL.get())
            )
            .setSpellQuality(0.8f, 1)
            .setDrinksPotions();

    private final Item meleeHelmet = Items.DIAMOND_HELMET;
    private final Item archerHelmet = Items.CHAINMAIL_HELMET;
    private final Item mageHelmet = io.redspace.ironsspellbooks.registries.ItemRegistry.NETHERITE_MAGE_HELMET.get();

    private final Item meleeHandItem = Items.IRON_PICKAXE;
    private final Item archerHandItem = Items.BOW;
    private final Item mageHandItem = io.redspace.ironsspellbooks.registries.ItemRegistry.GRAYBEARD_STAFF.get();

    @Nullable
    private DwarfType type;

    public enum DwarfType {
        MELEE,
        ARCHER,
        MAGE
    }

    public Dwarf(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.reassessWeaponGoal();
        this.xpReward = 15;
    }

    public Dwarf(Level level, DwarfType type) {
        this(EntityRegistry.DWARF.get(), level);
        this.type = type;
        this.reassessWeaponGoal();
    }

    @Override
    public Optional<SoundEvent> getAngerSound() {
        return Optional.of(SoundRegistry.DWARF_ANGER_SOUND.get());
    }

    @Override
    protected @org.jetbrains.annotations.Nullable SoundEvent getAmbientSound() {
        List<SoundEvent> sounds = new ArrayList<>();
        sounds.add(SoundRegistry.DWARF_DIGGY_HOLE.get());
        return sounds.get(Utils.random.nextIntBetweenInclusive(0, sounds.size() - 1));
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    @Override
    protected @org.jetbrains.annotations.Nullable SoundEvent getDeathSound() {
        return SoundRegistry.DWARF_AW_MAN.get();
    }

    @Override
    protected @org.jetbrains.annotations.Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundRegistry.DWARF_HURT.get();
    }

    @Override
    protected SoundEvent getDrinkingSound(ItemStack stack) {
        return SoundRegistry.DWARF_DRINK.get();
    }

    public SoundEvent getDrinkingSoundPublic() {
        return SoundRegistry.DWARF_DRINK.get();
    }

    @Override
    public SoundEvent getEatingSound(ItemStack stack) {
        return SoundRegistry.DWARF_EAT.get();
    }

    public void reassessWeaponGoal() {
        if (this.level() != null && !this.level().isClientSide) {
            this.goalSelector.removeGoal(this.meleeGoal);
            this.goalSelector.removeGoal(this.bowGoal);
            ItemStack itemstack = this.getItemInHand(this.getMainHandItem().isEmpty() ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
            if (itemstack.getItem() instanceof BowItem) {
                this.goalSelector.addGoal(4, this.bowGoal);
            } else if (itemstack.getItem() instanceof SwordItem || itemstack.getItem() instanceof PickaxeItem) {
                this.goalSelector.addGoal(4, this.meleeGoal);
            } else if (itemstack.getItem() instanceof StaffItem || itemstack.getItem() instanceof MagicStaffItem) {
                this.goalSelector.addGoal(4, this.magicGoal);
            }
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new PatrolNearLocationGoal(this, 30, .75f));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new WizardRecoverGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isHostileTowards));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));

    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 14.0)
                .add(Attributes.ATTACK_KNOCKBACK, 0.0)
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .add(Attributes.MOVEMENT_SPEED, .4)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 3);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
        RandomSource randomsource = Utils.random;
        this.populateDefaultEquipmentSlots(randomsource, pDifficulty);
        this.reassessWeaponGoal();
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        if (this.type != null) {
            switch (this.type) {
                case MELEE -> {
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(meleeHandItem));
                    this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(meleeHelmet));
                }
                case ARCHER -> {
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(archerHandItem));
                    this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(archerHelmet));
                }
                case MAGE -> {
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(mageHandItem));
                    this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(mageHelmet));
                }
            }
        } else {
            switch (pRandom.nextIntBetweenInclusive(0, 2)) {
                case 0 -> {
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(meleeHandItem));
                    this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(meleeHelmet));
                }
                case 1 -> {
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(archerHandItem));
                    this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(archerHelmet));
                }
                default -> {
                    this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(mageHandItem));
                    this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(mageHelmet));
                }
            }
        }
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.reassessWeaponGoal();
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        super.setItemSlot(slot, stack);
        if (!this.level().isClientSide) {
            this.reassessWeaponGoal();
        }
    }
}
