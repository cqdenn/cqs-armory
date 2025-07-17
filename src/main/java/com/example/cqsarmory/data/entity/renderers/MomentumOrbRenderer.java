package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.MomentumOrb;
import com.example.cqsarmory.models.MomentumOrbModel;
import com.mojang.blaze3d.vertex.PoseStack;
import io.redspace.ironsspellbooks.entity.mobs.wizards.archevoker.ArchevokerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MomentumOrbRenderer extends GeoEntityRenderer<MomentumOrb> {

    public MomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MomentumOrbModel());
    }
}
