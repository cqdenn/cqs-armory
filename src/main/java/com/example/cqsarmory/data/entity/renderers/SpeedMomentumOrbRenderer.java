package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.ArrowDamageMomentumOrb;
import com.example.cqsarmory.data.entity.ability.SpeedMomentumOrb;
import com.example.cqsarmory.models.ArrowDamageMomentumOrbModel;
import com.example.cqsarmory.models.SpeedMomentumOrbModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SpeedMomentumOrbRenderer extends GeoEntityRenderer<SpeedMomentumOrb> {

    public SpeedMomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SpeedMomentumOrbModel());
    }
}
