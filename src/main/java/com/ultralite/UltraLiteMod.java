package com.ultralite;

import com.ultralite.config.UltraLiteConfig;
import com.ultralite.optimization.*;
import com.ultralite.util.PerformanceMonitor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UltraLiteMod implements ClientModInitializer {
    public static final String MOD_ID = "ultralite";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static UltraLiteConfig config;
    public static PerformanceMonitor perfMonitor;
    public static MemoryManager memoryManager;
    public static RenderDistanceScaler renderScaler;
    public static EntityCuller entityCuller;
    public static ChunkCuller chunkCuller;
    public static TickOptimizer tickOptimizer;

    private static boolean initialized = false;
    private int tickCounter = 0;

    @Override
    public void onInitializeClient() {
        LOGGER.info("[UltraLite] Initializing optimization mod for low-end devices...");

        config = new UltraLiteConfig();
        config.load();

        perfMonitor = new PerformanceMonitor();
        memoryManager = new MemoryManager();
        renderScaler = new RenderDistanceScaler();
        entityCuller = new EntityCuller();
        chunkCuller = new ChunkCuller();
        tickOptimizer = new TickOptimizer();

        // Применяем агрессивные настройки при старте
        applyStartupOptimizations();

        // Регистрируем тик-обработчик
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);

        initialized = true;
        LOGGER.info("[UltraLite] Optimization mod initialized successfully!");
    }

    private void applyStartupOptimizations() {
        // Устанавливаем JVM параметры где возможно
        System.setProperty("java.awt.headless", "true");

        LOGGER.info("[UltraLite] Startup optimizations applied");
        LOGGER.info("[UltraLite] Available processors: {}", Runtime.getRuntime().availableProcessors());
        LOGGER.info("[UltraLite] Max memory: {} MB", Runtime.getRuntime().maxMemory() / 1024 / 1024);
        LOGGER.info("[UltraLite] Free memory: {} MB", Runtime.getRuntime().freeMemory() / 1024 / 1024);
    }

    private void onClientTick(MinecraftClient client) {
        if (client.world == null || client.player == null) return;

        tickCounter++;
        perfMonitor.tick();

        // Каждые 20 тиков (1 секунда) — обновляем мониторинг
        if (tickCounter % 20 == 0) {
            perfMonitor.updateFPS(client);
            memoryManager.checkMemory();
            renderScaler.adjustRenderDistance(client, perfMonitor);
        }

        // Каждые 100 тиков — агрессивная очистка если нужно
        if (tickCounter % 100 == 0) {
            memoryManager.periodicCleanup();
            tickCounter = 0;
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
