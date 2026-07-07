package com.alnrflo.chaos;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;

public class ModItems {

    //liste vide, donc ton outil n'est jamais "incorrect"
    public static final TagKey<Block> INCORRECT_FOR_CHAOS_TOOL =
            TagKey.create(Registries.BLOCK, Chaos.id("incorrect_for_chaos_tool"));

    public static final TagKey<Item> CHAOS_MATERIAL_TOOL = TagKey.create(Registries.ITEM, Chaos.id("chaos_material_tool"));

    // durabilité 2000, vitesse 10, bonus dégâts 5, enchantabilité 20
    public static final ToolMaterial CHAOS_MATERIAL = new ToolMaterial(INCORRECT_FOR_CHAOS_TOOL, 2000, 10.0F, 5.0F, 20, CHAOS_MATERIAL_TOOL);


    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {

        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Chaos.MOD_ID, name));

        // Create the item instance.
        T item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }
    public static final Item SWORD_CHAOS = register("sword_chaos", Item::new,
            new Item.Properties().sword(CHAOS_MATERIAL, 8.0f, -2.4f));

    public static final Item PICKAXE_CHAOS = register("pickaxe_chaos", Item::new,
            new Item.Properties().pickaxe(CHAOS_MATERIAL, 8.0f, -2.4f));

    public static final Item INGOT_CHAOS = register("ingot_chaos", Item::new, new Item.Properties());
    

    public static void init() {}
}