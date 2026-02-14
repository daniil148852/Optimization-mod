package com.ultralite.optimization;

import com.ultralite.UltraLiteMod;

public class TickOptimizer {

    private int tickCount = 0;

    /**
     * Определяет, стоит ли обрабатывать тик блока в зависимости от расстояния.
     */
    public boolean shouldTickBlock(double distanceSq) {
        if (!UltraLiteMod.config.skipDistantBlockTicks) return true;

        tickCount++;

        // Далёкие блоки тикают реже
        if (distanceSq > 64 * 64) {
            return tickCount % 8 == 0;
        } else if (distanceSq > 32 * 32) {
            return tickCount % 4 == 0;
        } else if (distanceSq > 16 * 16) {
            return tickCount % 2 == 0;
        }

        return true;
    }

    public void resetTick() {
        if (tickCount > 10000) tickCount = 0;
    }
}
