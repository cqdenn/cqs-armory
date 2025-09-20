package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = CqsArmory.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(),
                new ItemModelDataGenerator(packOutput, existingFileHelper));

        generator.addProvider(event.includeServer(),
                new CQItemTagsProvider(packOutput, event.getLookupProvider(), existingFileHelper));

        generator.addProvider(event.includeServer(),
                new CQDamageTypeTagsProvider(packOutput, event.getLookupProvider(), existingFileHelper));

        generator.addProvider(event.includeServer(),
                new CQEntityTypeTagsProvider(packOutput, event.getLookupProvider(), existingFileHelper));

        generator.addProvider(event.includeServer(),
                new RecipeDataGenerator(packOutput, event.getLookupProvider()));

    }
}
