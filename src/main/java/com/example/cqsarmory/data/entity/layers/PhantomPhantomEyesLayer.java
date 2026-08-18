package com.example.cqsarmory.data.entity.layers;

import com.example.cqsarmory.CqsArmory;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Phantom;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhantomPhantomEyesLayer<T extends Phantom> extends EyesLayer<T, PhantomModel<T>> {
    private static final RenderType PHANTOM_EYES = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "textures/entity/phantom_phantom_eyes.png"));

    public PhantomPhantomEyesLayer(RenderLayerParent<T, PhantomModel<T>> p_117342_) {
        super(p_117342_);
    }

    @Override
    public RenderType renderType() {
        return PHANTOM_EYES;
    }
}
