package com.example.cqsarmory.models;

import com.example.cqsarmory.data.entity.ability.BatProjectile;
import net.minecraft.client.animation.definitions.BatAnimation;
import net.minecraft.client.model.BatModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BatProjectileModel extends BatModel {
    public BatProjectileModel(ModelPart root) {
        super(root);
    }

    public void setupAnim(BatProjectile entity, float limbSwing, float limbSwingAmount, float ageInTicks, float entityYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.flyAnimationState, BatAnimation.BAT_FLYING, ageInTicks, 1.0F);
        this.animate(entity.restAnimationState, BatAnimation.BAT_RESTING, ageInTicks, 1.0F);
    }
}
