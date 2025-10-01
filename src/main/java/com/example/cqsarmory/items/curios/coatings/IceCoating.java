package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.OnSwingCoating;
import com.example.cqsarmory.registry.DamageTypes;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.entity.spells.ray_of_frost.RayOfFrostVisualEntity;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class IceCoating extends OnSwingCoating {
    public IceCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnSwingEffect(Player attacker, float hitDamage) {
        Level level = attacker.level();
        double damage = (hitDamage * 0.25) * attacker.getAttributeValue(AttributeRegistry.ICE_SPELL_POWER);
        DamageSource damageSource = new DamageSource(level.damageSources().damageTypes.getHolder(ISSDamageTypes.ICE_MAGIC).get(), null, attacker);
        var hitResult = Utils.raycastForEntity(level, attacker, 32, true, .15f);
        level.addFreshEntity(new RayOfFrostVisualEntity(level, attacker.getEyePosition(), hitResult.getLocation(), attacker));
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            Entity target = ((EntityHitResult) hitResult).getEntity();
            DamageSources.applyDamage(target, (float) damage, damageSource);
            target.setTicksFrozen(200);
            MagicManager.spawnParticles(level, ParticleHelper.ICY_FOG, hitResult.getLocation().x, target.getY(), hitResult.getLocation().z, 4, 0, 0, 0, .3, true);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            MagicManager.spawnParticles(level, ParticleHelper.ICY_FOG, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, 4, 0, 0, 0, .3, true);
        }
        MagicManager.spawnParticles(level, ParticleHelper.SNOWFLAKE, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, 50, 0, 0, 0, .3, false);
    }
}
