package com.ultralite.optimization;

import com.ultralite.UltraLiteMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;

public class ChunkCuller {

    /**
     * Определяет, нужно ли рендерить чанк на основе направления взгляда.
     */
    public boolean shouldRenderChunk(int chunkX, int chunkZ) {
        if (!UltraLiteMod.config.aggressiveChunkCulling) return true;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.gameRenderer == null) return true;

        Vec3d playerPos = client.player.getPos();
        int playerChunkX = (int) playerPos.x >> 4;
        int playerChunkZ = (int) playerPos.z >> 4;

        // Чанки рядом с игроком всегда рендерим
        int dx = chunkX - playerChunkX;
        int dz = chunkZ - playerChunkZ;
        int distSq = dx * dx + dz * dz;

        if (distSq <= 4) return true; // 2 чанка вокруг — всегда

        // Получаем направление взгляда игрока (yaw)
        float yaw = client.player.getYaw();
        double lookX = -Math.sin(Math.toRadians(yaw));
        double lookZ = Math.cos(Math.toRadians(yaw));

        // Нормализуем вектор к чанку
        double len = Math.sqrt(dx * dx + dz * dz);
        if (len == 0) return true;

        double normX = dx / len;
        double normZ = dz / len;

        // Скалярное произведение — косинус угла
        double dot = lookX * normX + lookZ * normZ;

        // Отсекаем чанки за спиной (угол > 120 градусов)
        if (dot < -0.3 && distSq > 9) {
            return false;
        }

        return true;
    }
}
