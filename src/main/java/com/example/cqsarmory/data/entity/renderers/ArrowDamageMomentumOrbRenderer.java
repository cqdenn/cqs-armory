package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.ArrowDamageMomentumOrb;
import com.example.cqsarmory.data.entity.ability.ExplosiveMomentumOrb;
import com.example.cqsarmory.models.ArrowDamageMomentumOrbModel;
import com.example.cqsarmory.models.ExplosiveMomentumOrbModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArrowDamageMomentumOrbRenderer extends GeoEntityRenderer<ArrowDamageMomentumOrb> {

    public ArrowDamageMomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ArrowDamageMomentumOrbModel());
    }
}
