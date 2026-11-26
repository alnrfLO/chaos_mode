package com.alnrflo.chaos;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Chaos implements ModInitializer {
	public static final String MOD_ID = "chaos";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");
		ModItemGroup.init();
		ModItems.init();
		ModBlocks.init();
		ModPortalActivation.register();
		ModWorldGen.register();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}

