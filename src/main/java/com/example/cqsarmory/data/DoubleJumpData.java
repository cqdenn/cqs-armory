package com.example.cqsarmory.data;

import com.example.cqsarmory.registry.EntityDataAttachmentRegistry;
import net.minecraft.world.entity.Entity;

public class DoubleJumpData {
    public int jumps;
    public int dashes;

    public static DoubleJumpData get (Entity entity) {
        return entity.getData(EntityDataAttachmentRegistry.DOUBLE_JUMP_DATA);
    }

}
