package com.example.cqsarmory.data.effects;

import com.example.cqsarmory.utils.CQtils;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonedEntitiesCastData;
import io.redspace.ironsspellbooks.entity.mobs.SummonedSkeleton;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public class GraveyardMageAOEEffect extends NonCurableEffect {
    public GraveyardMageAOEEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {

        Level level = livingEntity.level();
        if (level instanceof ServerLevel world) {
            Entity caster = livingEntity;
            SummonedEntitiesCastData summonedEntitiesCastData = new SummonedEntitiesCastData();
            int count = 6;
            float radius = 1.5f + 0.185f * count;
            int skillLevel = 3; //pretend raise dead level
            float spellPower = (float) ((10 + 3 * (skillLevel - 1)) * livingEntity.getAttributeValue(AttributeRegistry.BLOOD_SPELL_POWER) * livingEntity.getAttributeValue(AttributeRegistry.EVOCATION_SPELL_POWER));
            Vec3 castOrigin = caster.position().add(Vec3.ZERO);
            float casterYawRad = (float) Math.atan2(livingEntity.getForward().normalize().x, livingEntity.getForward().normalize().z);
            float casterYawDeg = casterYawRad * Mth.RAD_TO_DEG;
            RandomSource random = world.random;

            for (int i = 0; i < count; i++) {
                ItemStack[] equipment = getEquipment(spellPower, random, skillLevel);

                Monster undead = new SummonedSkeleton(world, true);
                undead.finalizeSpawn(world, world.getCurrentDifficultyAt(undead.getOnPos()), MobSpawnType.MOB_SUMMONED, null);
                equip(undead, equipment);

                float angle = 6.281f / count * i + casterYawRad;
                Vec3 spawn = Utils.moveToRelativeGroundLevel(world,
                        castOrigin.add(new Vec3(radius * Mth.cos(angle), 0, radius * Mth.sin(angle))), 10);
                spawn = world.clip(new ClipContext(castOrigin, spawn, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty())).getLocation();
                if (!world.noCollision(undead.getBoundingBox().move(spawn))) {
                    spawn = Utils.moveToRelativeGroundLevel(world,
                            spawn.add(castOrigin.subtract(spawn).normalize().scale(3)), 3);
                }
                undead.setPos(spawn.x, spawn.y, spawn.z);
                undead.setYRot(casterYawDeg);
                undead.setOldPosAndRot();

                Entity creature = undead;
                world.addFreshEntity(creature);
                SummonManager.initSummon(caster, creature, CQtils.getAOETimeSeconds(livingEntity) * 20, summonedEntitiesCastData);
            }

            Vec3 soundPos = castOrigin;
            world.playSound(null, soundPos.x, soundPos.y, soundPos.z, SoundRegistry.RAISE_DEAD_FINISH.get(),
                    SoundSource.HOSTILE, 2.0f, 0.9f + random.nextFloat() * 0.2f);
        }

        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return pDuration == 1;
    }

    private void equip(Mob mob, ItemStack[] equipment) {
        mob.setItemSlot(EquipmentSlot.FEET, equipment[0]);
        mob.setItemSlot(EquipmentSlot.LEGS, equipment[1]);
        mob.setItemSlot(EquipmentSlot.CHEST, equipment[2]);
        mob.setItemSlot(EquipmentSlot.HEAD, equipment[3]);
        mob.setDropChance(EquipmentSlot.FEET, 0.0F);
        mob.setDropChance(EquipmentSlot.LEGS, 0.0F);
        mob.setDropChance(EquipmentSlot.CHEST, 0.0F);
        mob.setDropChance(EquipmentSlot.HEAD, 0.0F);
        mob.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        mob.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
    }

    private ItemStack[] getEquipment(float power, RandomSource random, int skillLevel) {
        Item[] leather = {Items.LEATHER_BOOTS, Items.LEATHER_LEGGINGS, Items.LEATHER_CHESTPLATE, Items.LEATHER_HELMET};
        Item[] chain = {Items.CHAINMAIL_BOOTS, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_HELMET};
        Item[] iron = {Items.IRON_BOOTS, Items.IRON_LEGGINGS, Items.IRON_CHESTPLATE, Items.IRON_HELMET};

        int minQuality = 12;
        int maxQuality = (int) (6 * 3) + 15;

        ItemStack[] result = new ItemStack[4];
        for (int i = 0; i < 4; i++) {
            float quality = Mth.clamp((power + random.nextIntBetweenInclusive(-3, 8) - minQuality) / (maxQuality - minQuality), 0, 0.95f);
            if (random.nextDouble() < quality * quality) {
                if (quality > 0.85) {
                    result[i] = new ItemStack(iron[i]);
                } else if (quality > 0.65) {
                    result[i] = new ItemStack(chain[i]);
                } else if (quality > 0.15) {
                    result[i] = new ItemStack(leather[i]);
                } else {
                    result[i] = ItemStack.EMPTY;
                }
            } else {
                result[i] = ItemStack.EMPTY;
            }
        }
        return result;
    }
}