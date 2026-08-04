package com.alnrflo.chaos;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {

    public static <T extends Block> T registerBlock(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties settings) {

        // Create the block key.
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Chaos.MOD_ID, name));

        // Create the block instance.
        T block = blockFactory.apply(settings.setId(blockKey));

        // Register the block.
        Registry.register(BuiltInRegistries.BLOCK, blockKey, block);

        // Create the block item key.
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Chaos.MOD_ID, name));

        // Create and register the BlockItem (so the block can be held/placed from inventory).
        BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);

        return block;
    }

    public static final Block CHAOS_CORE = registerBlock("chaos_core", Block::new, BlockBehaviour.Properties.of());

    public static final Block CHAOS_PORTAL = registerBlock("chaos_portal", ModPortalBlock::new,
            BlockBehaviour.Properties.of().noCollision().lightLevel(state -> 11));

    public static final Block SEMI_CHAOS_ORE = registerBlock("chaos_ore_semi", Block::new,
            BlockBehaviour.Properties.of().strength(3.0f, 3.0f));

    public static final Block CHAOS_ORE = registerBlock("chaos_ore", Block::new,
            BlockBehaviour.Properties.of().strength(5.0f, 6.0f).lightLevel(state -> 4));

    public static final Block CHAOS_PORTAL_FRAME = registerBlock("chaos_portal_frame", ChaosPortalFrameBlock::new,
            BlockBehaviour.Properties.of().strength(50.0f, 1200.0f));

    public static void init() {}
}