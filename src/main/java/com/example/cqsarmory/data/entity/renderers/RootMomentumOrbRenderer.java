package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.BlackHoleMomentumOrb;
import com.example.cqsarmory.data.entity.ability.RootMomentumOrb;
import com.example.cqsarmory.models.BlackHoleMomentumOrbModel;
import com.example.cqsarmory.models.RootMomentumOrbModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RootMomentumOrbRenderer extends GeoEntityRenderer<RootMomentumOrb> {

    public RootMomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RootMomentumOrbModel());
    }
}
