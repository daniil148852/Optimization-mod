package com.ultralite.mixin;

import com.ultralite.UltraLiteMod;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    /**
     * Отключаем туман для экономии GPU.
     */
    @Inject(method = "renderFog", at = @At("HEAD"), cancellable = true, require = 0)
    private void onRenderFog(CallbackInfo ci) {
        if (UltraLiteMod.isInitialized() && UltraLiteMod.config.disableFog) {
            ci.cancel();
        }
    }
}
