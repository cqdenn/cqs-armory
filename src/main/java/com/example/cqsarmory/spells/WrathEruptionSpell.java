package com.example.cqsarmory.spells;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.network.SyncRagePacket;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.registry.CQSchoolRegistry;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.network.particles.FieryExplosionParticlesPacket;
import io.redspace.skillcasting.data.CastContext;
import io.redspace.skillcasting.data.PlayableSound;
import io.redspace.skillcasting.data.cast.CastType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Optional;

public class WrathEruptionSpell extends AbstractSpell {

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CQSchoolRegistry.MELEE_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(20)
            .build();

    public WrathEruptionSpell() {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 0;
    }

    @Override
    public boolean allowCrafting() {
        return false;
    }

    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public Optional<PlayableSound> getOnCastSound(CastContext castContext) {
        return Optional.of(PlayableSound.standard(SoundEvents.DRAGON_FIREBALL_EXPLODE));
    }

    @Override
    public boolean canBeInterrupted(Player player) {
        return false;
    }

    @Override
    public List<MutableComponent> getUniqueInfo(CastContext castContext) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.radius", 4),
                Component.literal("5 Base Damage"),
                Component.literal("3 Damage per Rage"),
                Component.literal("Consumes all Rage")
        );
    }

    @Override
    public void onCast(ServerLevel level, CastContext castContext) {
        Entity entity = castContext.asEntityCaster();
        int radius = 4;
        var entities = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius));
        float damage = 5 + 3 * AbilityData.get(entity).getRage();
        DamageSource damageSource = level.damageSources().explosion(null, entity); //swapped because game bad
        if (entity instanceof ServerPlayer serverPlayer) {
            float newRage = (float) serverPlayer.getAttributeValue(AttributeRegistry.MIN_RAGE);
            AbilityData.get(entity).setRage(newRage);
            PacketDistributor.sendToPlayer(serverPlayer, new SyncRagePacket((int) newRage));
        }


        for (LivingEntity target : entities) {
            if (target != entity && !DamageSources.isFriendlyFireBetween(entity, target) && !target.isSpectator() && Utils.hasLineOfSight(level, entity.position(), target.getBoundingBox().getCenter(), true)) {
                target.hurt(damageSource, damage);
            }
        }
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, new FieryExplosionParticlesPacket(entity.position(), radius));
    }


}


