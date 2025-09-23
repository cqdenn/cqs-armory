package com.example.cqsarmory.items.curios.coatings;

import com.example.cqsarmory.items.curios.OnHitCoating;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.ExtendedEvokerFang;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class FangCoating extends OnHitCoating {
    public FangCoating(Properties properties, String slotIdentifier) {
        super(properties, slotIdentifier);
    }

    @Override
    public void doOnHitEffect(Player attacker, LivingEntity target, float hitDamage) {
        int rings = Math.min(1 + ((int) hitDamage / 10), 7);
        int count = 5;
        float damage = (float) ((hitDamage / 10) * attacker.getAttributeValue(AttributeRegistry.SUMMON_DAMAGE));
        Vec3 center = target.getEyePosition();
        Level level = attacker.level();

        for (int r = 0; r < rings; r++) {
            float fangs = count + r * r;
            for (int i = 0; i < fangs; i++) {
                Vec3 spawn = center.add(new Vec3(0, 0, 1.5 * (r + 1)).yRot(attacker.getYRot() * Mth.DEG_TO_RAD + ((6.281f / fangs) * i)));
                spawn = Utils.moveToRelativeGroundLevel(level, spawn, 5);
                if (!level.getBlockState(BlockPos.containing(spawn).below()).isAir()) {
                    ExtendedEvokerFang fang = new ExtendedEvokerFang(level, spawn.x, spawn.y, spawn.z, get2DAngle(center, spawn), r, attacker, damage);
                    level.addFreshEntity(fang);
                }
            }
        }
    }

    private float get2DAngle(Vec3 a, Vec3 b) {
        return Utils.getAngle(new Vec2((float) a.x, (float) a.z), new Vec2((float) b.x, (float) b.z));
    }

}
