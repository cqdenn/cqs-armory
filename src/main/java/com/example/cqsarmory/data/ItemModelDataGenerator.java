package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.registry.WeaponsetAssetHandler;
import com.google.gson.JsonObject;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.TieredItem;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemModelDataGenerator extends ModelProvider<ItemModelBuilder> {

    public static List<Consumer<ItemModelDataGenerator>> toRegister = new ArrayList<>();

    public ItemModelDataGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CqsArmory.MODID, ITEM_FOLDER, AtlasBuilder::new, exFileHelper);
    }

    @Override
    protected void registerModels() {
        toRegister.forEach(c -> c.accept(this));
    }

    public ItemModelBuilder simpleItem(DeferredHolder<Item, ? extends Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/" + item.getId().getPath()));
    }

    public ItemModelBuilder atlasItem(DeferredHolder<Item, ? extends Item> item) {
        return ((AtlasBuilder) (withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/handheld")))).handler(CqsArmory.MODID + ":weaponset_handler").loader("atlas_api:dynamic_model");
    }

    public ItemModelBuilder atlas3DItem(DeferredHolder<Item, ? extends Item> item, ResourceLocation model3d) {
        return ((AtlasBuilder) (withExistingParent(item.getId().getPath()/* + "_handheld"*/,
                model3d))).handler(CqsArmory.MODID + ":weaponset_handler").loader("atlas_api:simple_model").texture("layer0", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getAtlasLocation((TieredItem) item.get(), false)));
    }

    public ItemModelBuilder atlasGuiItem(DeferredHolder<Item, ? extends Item> item) {
        return ((AtlasBuilder) (withExistingParent(item.getId().getPath() + "_gui",
                ResourceLocation.withDefaultNamespace("item/handheld")))).handler(CqsArmory.MODID + ":weaponset_handler").loader("atlas_api:simple_model").texture("layer0", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getAtlasLocation((TieredItem) item.get(), true)));
    }

    public ItemModelBuilder atlasLargeItem(DeferredHolder<Item, ? extends Item> item) {
        return ((AtlasBuilder) (withExistingParent(item.getId().getPath()/* + "_handheld"*/,
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/template_large_sword")))).handler(CqsArmory.MODID + ":weaponset_handler").loader("atlas_api:simple_model").texture("layer0", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getAtlasLocation((TieredItem) item.get(), false)));
    }

    public ItemModelBuilder atlasTransform(DeferredHolder<Item, ? extends Item> item, ItemModelBuilder base) {

        /*ItemModelBuilder model = withExistingParent(item.getId().getPath(), "item/handheld");

        ItemModelBuilder base = getBuilder(item.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile("minecraft:item/handheld"))
                .texture("layer0", WeaponsetAssetHandler.getAtlasLocation((TieredItem) item.get(), false));

        ItemModelBuilder guiModel = atlasGuiItem(item);getBuilder(item.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile("minecraft:item/generated"))
                .texture("layer0", WeaponsetAssetHandler.getAtlasLocation((TieredItem) item.get(), true));

        return model.customLoader(SeparateTransformsModelBuilder::begin)
                .base(base)
                .perspective(ItemDisplayContext.GUI, guiModel).end();*/
        return base;
    }

    @Override
    public String getName() {
        return "";
    }

    static class AtlasBuilder extends ItemModelBuilder {
        //  "loader": "atlas_api:dynamic_model",
        //  "handler": "irons_jewelry:jewelry"
        protected AtlasBuilder(ResourceLocation outputLocation, ExistingFileHelper existingFileHelper) {
            super(outputLocation, existingFileHelper);
        }

        @Override
        public ItemModelBuilder texture(String key, ResourceLocation texture) {
            this.textures.put(key, texture.toString());
            return this;
        }

        String loader = null;
        String handler = null;

        public AtlasBuilder loader(String loader) {
            this.loader = loader;
            return this;
        }

        public AtlasBuilder handler(String handler) {
            this.handler = handler;
            return this;
        }

        @Override
        public JsonObject toJson() {
            var json = super.toJson();
            if (loader != null) {
                json.addProperty("loader", loader);
            }
            if (handler != null) {
                json.addProperty("handler", handler);
            }
            return json;
        }
    }

}


