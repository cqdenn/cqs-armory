package com.example.cqsarmory.data;

import com.example.cqsarmory.CqsArmory;
import com.example.cqsarmory.items.BowType;
import com.example.cqsarmory.registry.WeaponsetAssetHandler;
import com.google.gson.JsonObject;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BowItem;
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
import java.util.logging.Logger;

public class ItemModelDataGenerator extends ModelProvider<ItemModelBuilder> {

    public static List<Consumer<ItemModelDataGenerator>> toRegister = new ArrayList<>();

    public ItemModelDataGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CqsArmory.MODID, ITEM_FOLDER, AtlasBuilder::new, exFileHelper);
    }

    public AtlasBuilder hiddenModel (String name, ResourceLocation parent) {
        return (AtlasBuilder) new AtlasBuilder(CqsArmory.id(name), this.existingFileHelper).parent(getExistingFile(parent));
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

    public ItemModelBuilder maceItem(DeferredHolder<Item, ? extends Item> item) {
        return ((AtlasBuilder) (withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/handheld_mace")))).handler(CqsArmory.MODID + ":weaponset_handler").loader("atlas_api:dynamic_model");
    }

    public ItemModelBuilder atlas3DItem(DeferredHolder<Item, ? extends Item> item, ResourceLocation model3d) {
        return ((AtlasBuilder) (hiddenModel(item.getId().getPath() + "_handheld",
                model3d))).handler(CqsArmory.MODID + ":weaponset_handler").loader("atlas_api:simple_model").texture("layer0", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getAtlasLocation((TieredItem) item.get(), false)));
    }

    public ItemModelBuilder atlasGuiItem(DeferredHolder<Item, ? extends Item> item) {
        return ((AtlasBuilder) (hiddenModel(item.getId().getPath() + "_gui",
                ResourceLocation.withDefaultNamespace("item/handheld"))))
                .handler(CqsArmory.MODID + ":weaponset_handler")
                .loader("atlas_api:simple_model")
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getAtlasLocation((TieredItem) item.get(), true)));
    }

    public ItemModelBuilder atlasLargeItem(DeferredHolder<Item, ? extends Item> item) {
        return ((AtlasBuilder) (hiddenModel(item.getId().getPath() + "_handheld",
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/template_large_sword"))).handler(CqsArmory.MODID + ":weaponset_handler").loader("atlas_api:simple_model").texture("layer0", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getAtlasLocation((TieredItem) item.get(), false))));
    }

    public ItemModelBuilder bowItemStandalone(DeferredHolder<Item, ? extends Item> item, BowType bowType) {
        String template = bowType.isLarge() ? "large_bow" : "shortbow";
        return ((AtlasBuilder) (withExistingParent(item.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/template_" + template))))
                .handler(CqsArmory.MODID + ":weaponset_handler")
                .loader("atlas_api:simple_model")
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getBowAtlasLocation((BowItem) item.get(), false))
                )
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).model(makeBowPullingStandalone(item, bowType, 0)).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), 0.65f).model(makeBowPullingStandalone(item, bowType, 1)).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), 0.9f).model(makeBowPullingStandalone(item, bowType, 2)).end();

    }

    public ItemModelBuilder spearThrowing(DeferredHolder<Item, ? extends Item> item) {
        return ((AtlasBuilder) (withExistingParent(item.getId().getPath() + "_throwing",
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/template_large_sword_throwing")))).handler(CqsArmory.MODID + ":weaponset_handler").loader("atlas_api:simple_model").texture("layer0", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getAtlasLocation((TieredItem) item.get(), false)));
    }

    public ItemModelBuilder spearAtlasTransform(DeferredHolder<Item, ? extends Item> item, ItemModelBuilder base) {

        ItemModelBuilder model = withExistingParent(item.getId().getPath(), "item/handheld");
        //((AtlasBuilder) base).debugLoader();

        ItemModelBuilder guiModel = atlasGuiItem(item);
        //((AtlasBuilder) guiModel).debugLoader();

        return model.customLoader(SeparateTransformsModelBuilder::begin)
                .base(base)
                .perspective(ItemDisplayContext.GUI, guiModel).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("throwing"), 1).model(spearThrowing(item)).end();
        //return base;
    }

    public ItemModelBuilder makeBowPullingStandalone(DeferredHolder<Item, ? extends Item> item, BowType bowType, int pull) {
        String template = bowType.isLarge() ? "large_bow" : "shortbow";
        return ((AtlasBuilder) (withExistingParent(item.getId().getPath() + "_pulling_" + pull,
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/template_" + template))))
                .handler(CqsArmory.MODID + ":weaponset_handler")
                .loader("atlas_api:simple_model")
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getBowAtlasLocation((BowItem) item.get(), false, pull))
                )
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, String.format("item/%s_arrow_%s", bowType.getArrowString(), pull)));
    }

    public ItemModelBuilder bowItemSeparateTransform(DeferredHolder<Item, ? extends Item> item, BowType bowType) {
        String template = bowType.isLarge() ? "large_bow" : "shortbow";
        var base = hiddenModel("", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/template_" + template))
                .handler(CqsArmory.MODID + ":weaponset_handler")
                .loader("atlas_api:simple_model")
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getBowAtlasLocation((BowItem) item.get(), false))
                );

        var gui = hiddenModel("", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/template_shortbow"))
                .handler(CqsArmory.MODID + ":weaponset_handler")
                .loader("atlas_api:simple_model")
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getBowAtlasLocation((BowItem) item.get(), true))
                );
        return ((AtlasBuilder) (withExistingParent(item.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/template_" + template))))
                .customLoader(SeparateTransformsModelBuilder::begin)
                .base(base)
                .perspective(ItemDisplayContext.GUI, gui)
                .end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).model(makeBowPullingSeparateTransform(item, bowType, 0)).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), 0.65f).model(makeBowPullingSeparateTransform(item, bowType, 1)).end()
                .override().predicate(ResourceLocation.withDefaultNamespace("pulling"), 1).predicate(ResourceLocation.withDefaultNamespace("pull"), 0.9f).model(makeBowPullingSeparateTransform(item, bowType, 2)).end();

    }

    public ItemModelBuilder makeBowPullingSeparateTransform(DeferredHolder<Item, ? extends Item> item, BowType bowType, int pull) {
        String template = bowType.isLarge() ? "large_bow" : "shortbow";
        var base = hiddenModel("", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/template_" + template))
                .handler(CqsArmory.MODID + ":weaponset_handler")
                .loader("atlas_api:simple_model")
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getBowAtlasLocation((BowItem) item.get(), false, pull))
                )
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, String.format("item/%s_arrow_%s", bowType.getArrowString(), pull)));
                ;

        var gui = hiddenModel("", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/template_shortbow"))
                .handler(CqsArmory.MODID + ":weaponset_handler")
                .loader("atlas_api:simple_model")
                .texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, WeaponsetAssetHandler.getBowAtlasLocation((BowItem) item.get(), true, pull))
                )
                .texture("layer1", ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, String.format("item/%s_arrow_%s", BowType.SHORTBOW.getArrowString(), pull)));
                ;
        return ((AtlasBuilder) (withExistingParent(item.getId().getPath() + "_pulling_" + pull,
                ResourceLocation.fromNamespaceAndPath(CqsArmory.MODID, "item/template_" + template))))
                .customLoader(SeparateTransformsModelBuilder::begin)
                .base(base)
                .perspective(ItemDisplayContext.GUI, gui)
                .end();
    }

    public ItemModelBuilder atlasTransform(DeferredHolder<Item, ? extends Item> item, ItemModelBuilder base) {

        ItemModelBuilder model = withExistingParent(item.getId().getPath(), "item/handheld");
        //((AtlasBuilder) base).debugLoader();

        ItemModelBuilder guiModel = atlasGuiItem(item);
        //((AtlasBuilder) guiModel).debugLoader();

        return model.customLoader(SeparateTransformsModelBuilder::begin)
                .base(base)
                .perspective(ItemDisplayContext.GUI, guiModel).end();
        //return base;
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

        public void debugLoader() {
            CqsArmory.LOGGER.debug("{}", this.customLoader);
        }
    }

}


