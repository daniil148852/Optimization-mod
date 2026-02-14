package com.ultralite.util;

import net.minecraft.client.MinecraftClient;

public class PerformanceMonitor {

    private long lastTickTime = System.nanoTime();
    private int currentFPS = 0;
    private float averageFPS = 30.0f;
    private int frameCount = 0;
    private long fpsAccumulator = 0;
    private float averageFrameTime = 0;

    // Скользящее среднее
    private static final float SMOOTHING = 0.1f;

    public void tick() {
        long now = System.nanoTime();
        long delta = now - lastTickTime;
        lastTickTime = now;

        averageFrameTime = averageFrameTime * (1 - SMOOTHING) + (delta / 1_000_000f) * SMOOTHING;
    }

    public void updateFPS(MinecraftClient client) {
        currentFPS = client.getCurrentFps();
        averageFPS = averageFPS * (1 - SMOOTHING) + currentFPS * SMOOTHING;
    }

    public int getCurrentFPS() {
        return currentFPS;
    }

    public float getAverageFPS() {
        return averageFPS;
    }

    public float getAverageFrameTime() {
        return averageFrameTime;
    }

    public boolean isLowFPS(int threshold) {
        return averageFPS < threshold;
    }

    public boolean isCriticalFPS() {
        return averageFPS < 15;
    }

    public PerformanceLevel getLevel() {
        if (averageFPS >= 50) return PerformanceLevel.HIGH;
        if (averageFPS >= 30) return PerformanceLevel.MEDIUM;
        if (averageFPS >= 15) return PerformanceLevel.LOW;
        return PerformanceLevel.CRITICAL;
    }

    public enum PerformanceLevel {
        HIGH,
        MEDIUM,
        LOW,
        CRITICAL
    }
}
