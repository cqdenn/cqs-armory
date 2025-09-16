package com.example.cqsarmory.data;

import com.example.cqsarmory.registry.EntityDataAttachmentRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DamageData {
    public DamageSource lastSource;
    public float lastDamage;
    public LivingEntity markedBy;

    public static DamageData get (Entity entity) {
        return entity.getData(EntityDataAttachmentRegistry.DAMAGE_DATA);
    }

}
