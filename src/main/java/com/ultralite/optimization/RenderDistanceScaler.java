package com.ultralite.optimization;

import com.ultralite.UltraLiteMod;
import com.ultralite.util.PerformanceMonitor;
import net.minecraft.client.MinecraftClient;

public class RenderDistanceScaler {

    private int currentRenderDistance = 4;
    private int stabilityCounter = 0;
    private static final int STABILITY_THRESHOLD = 5; // секунд стабильности перед увеличением

    public void adjustRenderDistance(MinecraftClient client, PerformanceMonitor monitor) {
        if (!UltraLiteMod.config.dynamicRenderDistance) return;

        int targetFPS = UltraLiteMod.config.targetFPS;
        float avgFPS = monitor.getAverageFPS();
        int minDist = UltraLiteMod.config.minRenderDistance;
        int maxDist = UltraLiteMod.config.maxRenderDistance;

        int viewDist = client.options.getViewDistance().getValue();

        if (avgFPS < targetFPS * 0.7f) {
            // FPS сильно ниже целевого — уменьшаем
            stabilityCounter = 0;
            if (viewDist > minDist) {
                int newDist = Math.max(minDist, viewDist - 1);
                client.options.getViewDistance().setValue(newDist);
                currentRenderDistance = newDist;
                UltraLiteMod.LOGGER.info("[UltraLite] Render distance decreased to {} (FPS: {})",
                        newDist, (int) avgFPS);
            }
        } else if (avgFPS < targetFPS * 0.9f) {
            // FPS немного ниже — держим
            stabilityCounter = 0;
        } else if (avgFPS > targetFPS * 1.3f) {
            // FPS значительно выше целевого — можно увеличить
            stabilityCounter++;
            if (stabilityCounter >= STABILITY_THRESHOLD && viewDist < maxDist) {
                int newDist = Math.min(maxDist, viewDist + 1);
                client.options.getViewDistance().setValue(newDist);
                currentRenderDistance = newDist;
                stabilityCounter = 0;
                UltraLiteMod.LOGGER.info("[UltraLite] Render distance increased to {} (FPS: {})",
                        newDist, (int) avgFPS);
            }
        } else {
            // Стабильно — накапливаем
            stabilityCounter++;
        }
    }

    public int getCurrentRenderDistance() {
        return currentRenderDistance;
    }
}
