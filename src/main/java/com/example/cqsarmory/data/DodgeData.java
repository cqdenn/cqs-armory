package com.example.cqsarmory.data;

import com.example.cqsarmory.registry.EntityDataAttachmentRegistry;
import net.minecraft.world.entity.Entity;

public class DodgeData {
    public int cooldown;
    public int charges;
    public long invulnerableTimeEnd;

    public static DodgeData get (Entity entity) {
        return entity.getData(EntityDataAttachmentRegistry.DODGE_DATA);
    }
}
