package com.ultralite.optimization;

import com.ultralite.UltraLiteMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class EntityCuller {

    private int visibleEntityCount = 0;

    /**
     * Определяет, нужно ли рендерить данную сущность.
     */
    public boolean shouldRenderEntity(Entity entity) {
        if (!UltraLiteMod.config.entityCulling) return true;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return true;

        // Не скрываем самого игрока
        if (entity == client.player) return true;

        // Проверяем расстояние
        double distance = entity.squaredDistanceTo(client.player);
        double maxDist = UltraLiteMod.config.entityRenderDistance;

        if (distance > maxDist * maxDist) {
            return false;
        }

        // Ограничиваем количество видимых сущностей
        if (visibleEntityCount >= UltraLiteMod.config.maxVisibleEntities) {
            return false;
        }

        visibleEntityCount++;
        return true;
    }

    /**
     * Сбрасываем счётчик каждый кадр.
     */
    public void resetFrame() {
        visibleEntityCount = 0;
    }

    public int getVisibleEntityCount() {
        return visibleEntityCount;
    }
}
