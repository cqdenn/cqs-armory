package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.ArrowDamageMomentumOrb;
import com.example.cqsarmory.data.entity.ability.DodgeMomentumOrb;
import com.example.cqsarmory.models.ArrowDamageMomentumOrbModel;
import com.example.cqsarmory.models.DodgeMomentumOrbModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DodgeMomentumOrbRenderer extends GeoEntityRenderer<DodgeMomentumOrb> {

    public DodgeMomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DodgeMomentumOrbModel());
    }
}
