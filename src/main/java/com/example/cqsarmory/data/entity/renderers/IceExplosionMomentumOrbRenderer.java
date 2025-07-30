package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.IceExplosionMomentumOrb;
import com.example.cqsarmory.models.IceExplosionMomentumOrbModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IceExplosionMomentumOrbRenderer extends GeoEntityRenderer<IceExplosionMomentumOrb> {

    public IceExplosionMomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new IceExplosionMomentumOrbModel());
    }
}
