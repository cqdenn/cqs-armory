package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.ArrowDamageMomentumOrb;
import com.example.cqsarmory.data.entity.ability.InstaDrawMomentumOrb;
import com.example.cqsarmory.models.ArrowDamageMomentumOrbModel;
import com.example.cqsarmory.models.InstaDrawMomentumOrbModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class InstaDrawMomentumOrbRenderer extends GeoEntityRenderer<InstaDrawMomentumOrb> {

    public InstaDrawMomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new InstaDrawMomentumOrbModel());
    }
}
