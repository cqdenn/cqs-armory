package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.ability.ArrowDamageMomentumOrb;
import com.example.cqsarmory.data.entity.ability.BlackHoleMomentumOrb;
import com.example.cqsarmory.models.ArrowDamageMomentumOrbModel;
import com.example.cqsarmory.models.BlackHoleMomentumOrbModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BlackHoleMomentumOrbRenderer extends GeoEntityRenderer<BlackHoleMomentumOrb> {

    public BlackHoleMomentumOrbRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BlackHoleMomentumOrbModel());
    }
}
