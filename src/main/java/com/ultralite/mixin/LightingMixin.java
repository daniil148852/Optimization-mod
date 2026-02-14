package com.ultralite.mixin;

import com.ultralite.UltraLiteMod;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapTextureManager.class)
public class LightingMixin {

    private int ultralite_updateCounter = 0;

    /**
     * Обновляем lightmap не каждый кадр, а реже.
     */
    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void onUpdate(float delta, CallbackInfo ci) {
        if (!UltraLiteMod.isInitialized()) return;

        if (UltraLiteMod.config.simplifyLighting) {
            ultralite_updateCounter++;
            // Обновляем lightmap каждые 4 кадра
            if (ultralite_updateCounter % 4 != 0) {
                ci.cancel();
            }
        }
    }
}
