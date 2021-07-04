package com.red.masaadditions;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MasaAdditions implements ModInitializer {
    public static final Logger logger = LogManager.getLogger("masaadditions");

    @Override
    public void onInitialize() {
        logger.info("MasaAdditions Loaded.");
    }
}
