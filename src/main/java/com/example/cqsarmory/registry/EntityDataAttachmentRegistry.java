package com.example.cqsarmory.registry;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import com.example.cqsarmory.data.DamageData;
import com.example.cqsarmory.data.DodgeData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class EntityDataAttachmentRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, CqsArmory.MODID);

    public static final Supplier<AttachmentType<AbilityData>> ABILITY_DATA = ATTACHMENT_TYPES.register(
            "ability_data", () -> AttachmentType.builder(() -> new AbilityData()).build()
    );
    public static final Supplier<AttachmentType<DamageData>> DAMAGE_DATA = ATTACHMENT_TYPES.register(
            "damage_data", () -> AttachmentType.builder(() -> new DamageData()).sync(DamageData.STREAM_CODEC).build()
    );
    public static final Supplier<AttachmentType<DodgeData>> DODGE_DATA = ATTACHMENT_TYPES.register(
            "dodge_data", () -> AttachmentType.builder(() -> new DodgeData()).build()
    );

}
