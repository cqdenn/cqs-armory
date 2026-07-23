package com.example.cqsarmory.data.entity.renderers;

import com.example.cqsarmory.models.DwarfModel;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DwarfRenderer extends AbstractSpellCastingMobRenderer {
    public DwarfRenderer(EntityRendererProvider.Context context) {
        super(context, new DwarfModel());
    }
}
