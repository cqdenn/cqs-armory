package com.example.cqsarmory.api;

import com.example.cqsarmory.CqsArmory;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.minecraft.resources.ResourceLocation;

public class AbilityAnimations {
        public static ResourceLocation ANIMATION_RESOURCE = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "animation");

        public static final AnimationHolder SPIN_ANIMATION = new AnimationHolder("spin", false);
}
