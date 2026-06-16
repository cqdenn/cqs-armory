package com.example.cqsarmory.items.curios.quivers;

import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.entity.ability.AbilityArrow;
import com.example.cqsarmory.data.entity.ability.HitscanArcaneBeam;
import com.example.cqsarmory.items.curios.SimpleDescriptiveQuiver;
import com.example.cqsarmory.registry.AttributeRegistry;
import com.example.cqsarmory.utils.CQRaycaster;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.util.RaycastBuilder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

public class ArcaneQuiver extends SimpleDescriptiveQuiver {
    public ArcaneQuiver(Properties properties, String slotIdentifier, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, slotIdentifier, spellDataRegistryHolders);
    }

    @Override
    public AbilityArrow getCustomProjectile(Projectile arrow, Player shooter, float arrowDmg) {
        float originalDistance = 64 * AbilityData.get(shooter).bowVelocity;
        var hitResult = CQRaycaster.begin(arrow.level(), shooter)
                .range(originalDistance)
                .checkForBlocks(true)
                .bbInflation(.15f)
                .buildList();
        int pierce = 0;
        if (arrow instanceof AbstractArrow abs) pierce += abs.getPierceLevel();
        pierce += (int) shooter.getAttributeValue(AttributeRegistry.ARROW_PIERCING);
        Vec3 end = hitResult.get(pierce).getLocation();
        HitscanArcaneBeam beam = new HitscanArcaneBeam(arrow.level(), shooter.getEyePosition().subtract(0, 0.75f, 0), end, originalDistance, shooter, hitResult, 1);
        Vec3 vec3 = shooter.getForward();
        double d0 = vec3.horizontalDistance();
        beam.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
        beam.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / (float)Math.PI));
        beam.yRotO = beam.getYRot();
        beam.xRotO = beam.getXRot();
        beam.copyStats(arrow, shooter, arrowDmg);
        return beam;
    }
}
