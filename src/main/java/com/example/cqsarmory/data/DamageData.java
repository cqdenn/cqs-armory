package com.example.cqsarmory.data;

import com.example.cqsarmory.registry.EntityDataAttachmentRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;

public class DamageData {
    public DamageData (Vec3 chainWhipLocation) {
        this.chainWhipLocation = chainWhipLocation;
    }

    public DamageData () {

    }

    public record OptionalVec3(boolean present, double x, double y, double z) {
        public static final StreamCodec<FriendlyByteBuf, OptionalVec3> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.BOOL, OptionalVec3::present,
                        ByteBufCodecs.DOUBLE, OptionalVec3::x,
                        ByteBufCodecs.DOUBLE, OptionalVec3::y,
                        ByteBufCodecs.DOUBLE, OptionalVec3::z,
                        OptionalVec3::new
                );

        public static OptionalVec3 of(Vec3 v) {
            return v != null ? new OptionalVec3(true, v.x, v.y, v.z) : new OptionalVec3(false, 0, 0, 0);
        }

        public Vec3 toVec3() {
            return present ? new Vec3(x, y, z) : null;
        }
    }

    public static final StreamCodec<FriendlyByteBuf, DamageData> STREAM_CODEC =
            StreamCodec.composite(
                    OptionalVec3.STREAM_CODEC,
                    damage -> OptionalVec3.of(damage.chainWhipLocation),

                    OptionalVec3.STREAM_CODEC,
                    damage -> OptionalVec3.of(damage.hookedByLocation),

                    (chainLoc, hookedLoc) -> {
                        DamageData data = new DamageData();
                        data.chainWhipLocation = chainLoc.toVec3();
                        data.hookedByLocation = hookedLoc.toVec3();
                        return data;
                    }
            );

    public DamageSource lastSource;
    public float lastDamage;
    public LivingEntity markedBy;
    public long cancelNextFall;
    public Vec3 chainWhipLocation;
    public HashMap<LivingEntity, Integer> bleedStacks = new HashMap<>();
    public boolean preventAOEChaining;
    public int hellfireAOETargetingDelay;
    public Vec3 hookedByLocation;
    public long hookedTimestamp;
    public Entity hookedBy;

    public static DamageData get (Entity entity) {
        return entity.getData(EntityDataAttachmentRegistry.DAMAGE_DATA);
    }

}
