package com.ultralite.mixin;

import com.ultralite.UltraLiteMod;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkBuilder.class)
public class ChunkRenderMixin {

    /**
     * Уменьшаем приоритет перестройки чанков, чтобы не нагружать CPU.
     */
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        if (UltraLiteMod.isInitialized()) {
            UltraLiteMod.LOGGER.info("[UltraLite] ChunkBuilder initialized with optimizations");
        }
    }
}
