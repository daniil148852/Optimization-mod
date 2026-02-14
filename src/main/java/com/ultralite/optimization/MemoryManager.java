package com.ultralite.optimization;

import com.ultralite.UltraLiteMod;

public class MemoryManager {

    private long lastGCTime = System.currentTimeMillis();
    private int gcCount = 0;

    public void checkMemory() {
        Runtime runtime = Runtime.getRuntime();
        long maxMem = runtime.maxMemory();
        long usedMem = runtime.totalMemory() - runtime.freeMemory();
        double usage = (double) usedMem / maxMem;

        if (usage > UltraLiteMod.config.memoryThreshold) {
            forceCleanup();
            UltraLiteMod.LOGGER.info("[UltraLite] Memory cleanup triggered. Usage: {}%",
                    String.format("%.1f", usage * 100));
        }
    }

    public void periodicCleanup() {
        if (!UltraLiteMod.config.aggressiveGC) return;

        long now = System.currentTimeMillis();
        int intervalMs = UltraLiteMod.config.gcIntervalSeconds * 1000;

        if (now - lastGCTime > intervalMs) {
            softCleanup();
            lastGCTime = now;
        }
    }

    private void softCleanup() {
        // Намекаем GC провести уборку
        System.gc();
        gcCount++;

        if (gcCount % 10 == 0) {
            UltraLiteMod.LOGGER.debug("[UltraLite] Soft GC #{} performed", gcCount);
        }
    }

    private void forceCleanup() {
        System.gc();
        System.runFinalization();
        System.gc();

        Runtime runtime = Runtime.getRuntime();
        long usedMem = runtime.totalMemory() - runtime.freeMemory();
        UltraLiteMod.LOGGER.info("[UltraLite] Force GC. Memory after: {} MB",
                usedMem / 1024 / 1024);
    }

    public long getUsedMemoryMB() {
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
    }

    public double getMemoryUsagePercent() {
        Runtime runtime = Runtime.getRuntime();
        long used = runtime.totalMemory() - runtime.freeMemory();
        return (double) used / runtime.maxMemory() * 100;
    }
}
