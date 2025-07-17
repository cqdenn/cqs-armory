package com.example.cqsarmory.models;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.data.entity.ability.ExplosiveMomentumOrb;
import com.example.cqsarmory.data.entity.ability.MomentumOrb;
import com.example.cqsarmory.data.entity.renderers.MomentumOrbRenderer;
import com.example.cqsarmory.registry.EntityRegistry;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class MomentumOrbModel extends DefaultedEntityGeoModel<MomentumOrb> {
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "geo/momentum_orb.geo.json");

    public MomentumOrbModel() {
        super(CqsArmory.id("entity"));
    }
    @Override
    public ResourceLocation getModelResource(MomentumOrb object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(MomentumOrb object) {
        return object.getTexture();
    }

}
