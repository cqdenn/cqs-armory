package com.example.cqsarmory.network;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.AbilityData;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.network.particles.FieryExplosionParticlesPacket;
import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SmashParticlePacket implements CustomPacketPayload {

    private final BlockPos pos;
    public static final CustomPacketPayload.Type<SmashParticlePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "smash_particles"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SmashParticlePacket> STREAM_CODEC = CustomPacketPayload.codec(SmashParticlePacket::write, SmashParticlePacket::new);

    public SmashParticlePacket(BlockPos pos) {
        this.pos = pos;
    }

    public SmashParticlePacket(FriendlyByteBuf buf) {
        pos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(SmashParticlePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level == null) return;
            BlockPos blockpos = packet.pos;
            Vec3 vec3 = blockpos.getCenter().add(0.0, 0.5, 0.0);
            BlockParticleOption blockparticleoption = new BlockParticleOption(ParticleTypes.DUST_PILLAR, level.getBlockState(blockpos));

            for (int i = 0; (float) i < (float) 750 / 3.0F; i++) {
                double d0 = vec3.x + level.getRandom().nextGaussian() / 2.0;
                double d1 = vec3.y;
                double d2 = vec3.z + level.getRandom().nextGaussian() / 2.0;
                double d3 = level.getRandom().nextGaussian() * 0.2F;
                double d4 = level.getRandom().nextGaussian() * 0.2F;
                double d5 = level.getRandom().nextGaussian() * 0.2F;
                level.addParticle(blockparticleoption, d0, d1, d2, d3, d4, d5);
            }
            for (int j = 0; (float) j < (float) 750 / 1.5F; j+=2) {
                double d6 = vec3.x + 2.5 * Math.cos((double) j) + level.getRandom().nextGaussian() / 2.0;
                double d7 = vec3.y;
                double d8 = vec3.z + 2.5 * Math.sin((double) j) + level.getRandom().nextGaussian() / 2.0;
                double d9 = level.getRandom().nextGaussian() * 0.05F;
                double d10 = level.getRandom().nextGaussian() * 0.05F;
                double d11 = level.getRandom().nextGaussian() * 0.05F;
                level.addParticle(blockparticleoption, d6, d7, d8, d9, d10, d11);
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


}
