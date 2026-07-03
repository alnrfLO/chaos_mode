package com.alnrflo.chaos;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import static net.minecraft.world.item.ToolMaterial.NETHERITE;

public class ModItems {
    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {

        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Chaos.MOD_ID, name));

        // Create the item instance.
        T item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }
    public static final Item SWORD_CHAOS = register("sword_chaos", Item::new ,new Item.Properties().sword(NETHERITE, 8.0f, -2.4f));
    public static final Item PICKAXE_CHAOS = register("pickaxe_chaos", Item::new ,new Item.Properties().pickaxe(NETHERITE, 8.0f, -2.4f));
    public static void init() {}
}

