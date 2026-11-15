package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.data.entity.living.ScorchedBlaze;
import com.example.cqsarmory.models.ScorchedBlazeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class ScorchedBlazeRenderer extends GeoEntityRenderer<ScorchedBlaze> {
    public ScorchedBlazeRenderer(EntityRendererProvider.Context context) {
        super(context, new ScorchedBlazeModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    protected float getDeathMaxRotation(ScorchedBlaze animatable) {
        return 0f;
    }
}

