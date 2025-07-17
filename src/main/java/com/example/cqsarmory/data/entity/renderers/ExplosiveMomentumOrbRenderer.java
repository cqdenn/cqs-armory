package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.ExplosiveMomentumOrb;
import com.example.cqsarmory.data.entity.ability.MomentumOrb;
import com.example.cqsarmory.models.ExplosiveMomentumOrbModel;
import com.example.cqsarmory.models.MomentumOrbModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ExplosiveMomentumOrbRenderer extends GeoEntityRenderer<ExplosiveMomentumOrb> {

    public ExplosiveMomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ExplosiveMomentumOrbModel());
    }
}
