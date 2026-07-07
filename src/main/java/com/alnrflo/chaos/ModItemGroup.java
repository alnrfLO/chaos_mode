package com.alnrflo.chaos;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroup {

    public static final CreativeModeTab CHAOS_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            Chaos.id("chaos_tab"),
            FabricItemGroup.builder()
                    .title(Component.literal("Chaos"))
                    .icon(() -> new ItemStack(ModItems.SWORD_CHAOS))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.SWORD_CHAOS);
                        output.accept(ModItems.PICKAXE_CHAOS);
                        output.accept(ModItems.INGOT_CHAOS);
                        output.accept(ModBlocks.CHAOS_CORE);
                    })
                    .build()
    );

    public static void init() {}
}