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

    public static final TagKey<Item> SEMI_CHAOS_MATERIAL_TOOL = TagKey.create(Registries.ITEM, Chaos.id("semi_chaos_material_tool"));

    // pur chaos : au-dessus de la netherite (durabilité 2031, vitesse 9, bonus degats 4, enchantabilite 15) sur tous les points
    // durabilite 2600, vitesse 10, bonus degats 5, enchantabilite 20
    public static final ToolMaterial CHAOS_MATERIAL = new ToolMaterial(INCORRECT_FOR_CHAOS_TOOL, 2600, 10.0F, 5.0F, 20, CHAOS_MATERIAL_TOOL);

    // semi chaos : entre le diamant (durabilite 1561, vitesse 8, bonus degats 3, enchantabilite 10) et la netherite
    // durabilite 1800, vitesse 8.5, bonus degats 3.5, enchantabilite 12
    public static final ToolMaterial SEMI_CHAOS_MATERIAL = new ToolMaterial(INCORRECT_FOR_CHAOS_TOOL, 1800, 8.5F, 3.5F, 12, SEMI_CHAOS_MATERIAL_TOOL);


    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {

        // Create the item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Chaos.MOD_ID, name));

        // Create the item instance.
        T item = itemFactory.apply(settings.setId(itemKey));

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }
    public static final Item SWORD_CHAOS = register("chaos_sword", Item::new,
            new Item.Properties().sword(CHAOS_MATERIAL, 8.0f, -2.4f));

    public static final Item PICKAXE_CHAOS = register("chaos_pickaxe", Item::new,
            new Item.Properties().pickaxe(CHAOS_MATERIAL, 8.0f, -2.4f));

    public static final Item SWORD_SEMI_CHAOS = register("chaos_sword_semi", Item::new,
            new Item.Properties().sword(SEMI_CHAOS_MATERIAL, 6.0f, -2.4f));

    public static final Item PICKAXE_SEMI_CHAOS = register("chaos_pickaxe_semi", Item::new,
            new Item.Properties().pickaxe(SEMI_CHAOS_MATERIAL, 6.0f, -2.4f));

    public static final Item INGOT_CHAOS = register("chaos_ingot", Item::new, new Item.Properties());

    public static final Item SEMI_CHAOS = register("chaos_ingot_semi", Item::new, new Item.Properties());

    public static final Item RAW_CHAOS = register("chaos_raw", Item::new, new Item.Properties());

    public static void init() {}
}