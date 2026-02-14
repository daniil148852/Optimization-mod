package com.ultralite.config;

import com.ultralite.UltraLiteMod;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class UltraLiteConfig {

    private static final Path CONFIG_PATH = Path.of("config", "ultralite.properties");

    // Рендеринг
    public boolean disableParticles = true;
    public boolean reduceParticles = true;
    public int maxParticles = 50;
    public boolean disableClouds = true;
    public boolean disableAnimatedTextures = true;
    public boolean disableBlockEntityAnimation = true;
    public boolean simplifyItemRendering = true;

    // Дальность
    public boolean dynamicRenderDistance = true;
    public int minRenderDistance = 2;
    public int maxRenderDistance = 6;
    public int targetFPS = 30;

    // Сущности
    public boolean entityCulling = true;
    public int maxVisibleEntities = 32;
    public double entityRenderDistance = 24.0;

    // Освещение
    public boolean simplifyLighting = true;
    public boolean cacheLightValues = true;

    // Память
    public boolean aggressiveGC = true;
    public int gcIntervalSeconds = 30;
    public double memoryThreshold = 0.85;

    // Тики
    public boolean optimizeTicking = true;
    public boolean skipDistantBlockTicks = true;

    // Чанки
    public boolean aggressiveChunkCulling = true;
    public boolean skipHiddenChunkFaces = true;

    // Дополнительное
    public boolean disableFog = true;
    public boolean reduceBiomeBlending = true;
    public int biomeBlendRadius = 0;
    public boolean disableRainSnow = true;

    public void load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                Properties props = new Properties();
                try (InputStream in = Files.newInputStream(CONFIG_PATH)) {
                    props.load(in);
                }

                disableParticles = getBool(props, "disableParticles", disableParticles);
                reduceParticles = getBool(props, "reduceParticles", reduceParticles);
                maxParticles = getInt(props, "maxParticles", maxParticles);
                disableClouds = getBool(props, "disableClouds", disableClouds);
                disableAnimatedTextures = getBool(props, "disableAnimatedTextures", disableAnimatedTextures);
                disableBlockEntityAnimation = getBool(props, "disableBlockEntityAnimation", disableBlockEntityAnimation);
                simplifyItemRendering = getBool(props, "simplifyItemRendering", simplifyItemRendering);
                dynamicRenderDistance = getBool(props, "dynamicRenderDistance", dynamicRenderDistance);
                minRenderDistance = getInt(props, "minRenderDistance", minRenderDistance);
                maxRenderDistance = getInt(props, "maxRenderDistance", maxRenderDistance);
                targetFPS = getInt(props, "targetFPS", targetFPS);
                entityCulling = getBool(props, "entityCulling", entityCulling);
                maxVisibleEntities = getInt(props, "maxVisibleEntities", maxVisibleEntities);
                entityRenderDistance = getDouble(props, "entityRenderDistance", entityRenderDistance);
                simplifyLighting = getBool(props, "simplifyLighting", simplifyLighting);
                cacheLightValues = getBool(props, "cacheLightValues", cacheLightValues);
                aggressiveGC = getBool(props, "aggressiveGC", aggressiveGC);
                gcIntervalSeconds = getInt(props, "gcIntervalSeconds", gcIntervalSeconds);
                memoryThreshold = getDouble(props, "memoryThreshold", memoryThreshold);
                optimizeTicking = getBool(props, "optimizeTicking", optimizeTicking);
                skipDistantBlockTicks = getBool(props, "skipDistantBlockTicks", skipDistantBlockTicks);
                aggressiveChunkCulling = getBool(props, "aggressiveChunkCulling", aggressiveChunkCulling);
                skipHiddenChunkFaces = getBool(props, "skipHiddenChunkFaces", skipHiddenChunkFaces);
                disableFog = getBool(props, "disableFog", disableFog);
                reduceBiomeBlending = getBool(props, "reduceBiomeBlending", reduceBiomeBlending);
                biomeBlendRadius = getInt(props, "biomeBlendRadius", biomeBlendRadius);
                disableRainSnow = getBool(props, "disableRainSnow", disableRainSnow);

                UltraLiteMod.LOGGER.info("[UltraLite] Config loaded from file");
            } else {
                save();
                UltraLiteMod.LOGGER.info("[UltraLite] Default config created");
            }
        } catch (Exception e) {
            UltraLiteMod.LOGGER.error("[UltraLite] Error loading config", e);
        }
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Properties props = new Properties();

            props.setProperty("disableParticles", String.valueOf(disableParticles));
            props.setProperty("reduceParticles", String.valueOf(reduceParticles));
            props.setProperty("maxParticles", String.valueOf(maxParticles));
            props.setProperty("disableClouds", String.valueOf(disableClouds));
            props.setProperty("disableAnimatedTextures", String.valueOf(disableAnimatedTextures));
            props.setProperty("disableBlockEntityAnimation", String.valueOf(disableBlockEntityAnimation));
            props.setProperty("simplifyItemRendering", String.valueOf(simplifyItemRendering));
            props.setProperty("dynamicRenderDistance", String.valueOf(dynamicRenderDistance));
            props.setProperty("minRenderDistance", String.valueOf(minRenderDistance));
            props.setProperty("maxRenderDistance", String.valueOf(maxRenderDistance));
            props.setProperty("targetFPS", String.valueOf(targetFPS));
            props.setProperty("entityCulling", String.valueOf(entityCulling));
            props.setProperty("maxVisibleEntities", String.valueOf(maxVisibleEntities));
            props.setProperty("entityRenderDistance", String.valueOf(entityRenderDistance));
            props.setProperty("simplifyLighting", String.valueOf(simplifyLighting));
            props.setProperty("cacheLightValues", String.valueOf(cacheLightValues));
            props.setProperty("aggressiveGC", String.valueOf(aggressiveGC));
            props.setProperty("gcIntervalSeconds", String.valueOf(gcIntervalSeconds));
            props.setProperty("memoryThreshold", String.valueOf(memoryThreshold));
            props.setProperty("optimizeTicking", String.valueOf(optimizeTicking));
            props.setProperty("skipDistantBlockTicks", String.valueOf(skipDistantBlockTicks));
            props.setProperty("aggressiveChunkCulling", String.valueOf(aggressiveChunkCulling));
            props.setProperty("skipHiddenChunkFaces", String.valueOf(skipHiddenChunkFaces));
            props.setProperty("disableFog", String.valueOf(disableFog));
            props.setProperty("reduceBiomeBlending", String.valueOf(reduceBiomeBlending));
            props.setProperty("biomeBlendRadius", String.valueOf(biomeBlendRadius));
            props.setProperty("disableRainSnow", String.valueOf(disableRainSnow));

            try (OutputStream out = Files.newOutputStream(CONFIG_PATH)) {
                props.store(out, "UltraLite Optimization Mod Config - for Unisoc T606 and similar low-end devices");
            }
        } catch (Exception e) {
            UltraLiteMod.LOGGER.error("[UltraLite] Error saving config", e);
        }
    }

    private boolean getBool(Properties p, String key, boolean def) {
        return Boolean.parseBoolean(p.getProperty(key, String.valueOf(def)));
    }

    private int getInt(Properties p, String key, int def) {
        try {
            return Integer.parseInt(p.getProperty(key, String.valueOf(def)));
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private double getDouble(Properties p, String key, double def) {
        try {
            return Double.parseDouble(p.getProperty(key, String.valueOf(def)));
        } catch (NumberFormatException e) {
            return def;
        }
    }
}
