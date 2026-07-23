package com.example.cqsarmory.api;

import com.example.cqsarmory.CqsArmory;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.minecraft.resources.ResourceLocation;

public class  AbilityAnimations {

        public static ResourceLocation ABILITY_ANIMATION_RESOURCE = ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "animations/ability_animations.json");

        private static AnimationHolder defaultFile(ResourceLocation resourceLocation) {
                return new AnimationHolder(ABILITY_ANIMATION_RESOURCE, resourceLocation);
        }

        private static AnimationHolder defaultFile(ResourceLocation resourceLocation, boolean animatesLegs) {
                return new AnimationHolder(ABILITY_ANIMATION_RESOURCE, resourceLocation, animatesLegs);
        }

        public static final AnimationHolder SPIN_ANIMATION = defaultFile(CqsArmory.id("spin"), true);
        public static final AnimationHolder RUPTURE_ANIMATION = defaultFile(CqsArmory.id("rupture"), true);
        public static final AnimationHolder REAP_ANIMATION = defaultFile(CqsArmory.id("reap"), true);
        public static final AnimationHolder RIPOSTE_ANIMATION = defaultFile(CqsArmory.id("riposte"), true);
        public static final AnimationHolder UPPERCUT_ANIMATION = defaultFile(CqsArmory.id("uppercut"), true);
        public static final AnimationHolder INSTANT_OVERHEAD_SLAM_ANIMATION = defaultFile(CqsArmory.id("instant_overhead_slam"), true);





}
