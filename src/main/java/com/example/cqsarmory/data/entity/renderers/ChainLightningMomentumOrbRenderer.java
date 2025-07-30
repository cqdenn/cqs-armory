package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.BlackHoleMomentumOrb;
import com.example.cqsarmory.data.entity.ability.ChainLightningMomentumOrb;
import com.example.cqsarmory.models.BlackHoleMomentumOrbModel;
import com.example.cqsarmory.models.ChainLightningMomentumOrbModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ChainLightningMomentumOrbRenderer extends GeoEntityRenderer<ChainLightningMomentumOrb> {

    public ChainLightningMomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ChainLightningMomentumOrbModel());
    }
}
