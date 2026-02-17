package com.example.cqsarmory.data;

import com.example.cqsarmory.registry.EntityDataAttachmentRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class DamageData {
    public DamageData (Vec3 chainWhipLocation) {
        this.chainWhipLocation = chainWhipLocation;
    }

    public DamageData () {

    }

    public static final StreamCodec<FriendlyByteBuf, DamageData> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    damage -> damage.chainWhipLocation != null,

                    ByteBufCodecs.DOUBLE,
                    damage -> damage.chainWhipLocation != null ? damage.chainWhipLocation.x : 0,

                    ByteBufCodecs.DOUBLE,
                    damage -> damage.chainWhipLocation != null ? damage.chainWhipLocation.y : 0,

                    ByteBufCodecs.DOUBLE,
                    damage -> damage.chainWhipLocation != null ? damage.chainWhipLocation.z : 0,

                    (hasVec, x, y, z) -> {
                        DamageData data = new DamageData();
                        if (hasVec) {
                            data.chainWhipLocation = new Vec3(x, y, z);
                        }
                        return data;
                    }
            );

    /*public static final StreamCodec<FriendlyByteBuf, DamageData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, damage -> damage.chainWhipLocation == null ? 0 : damage.chainWhipLocation.x,
            ByteBufCodecs.DOUBLE, damage -> damage.chainWhipLocation == null ? 0 : damage.chainWhipLocation.y,
            ByteBufCodecs.DOUBLE, damage -> damage.chainWhipLocation == null ? 0 : damage.chainWhipLocation.z,
            (x, y, z) -> new DamageData(new Vec3(x, y, z))
    );*/

    public DamageSource lastSource;
    public float lastDamage;
    public LivingEntity markedBy;
    public long cancelNextFall;
    public Vec3 chainWhipLocation;

    public static DamageData get (Entity entity) {
        return entity.getData(EntityDataAttachmentRegistry.DAMAGE_DATA);
    }

}
