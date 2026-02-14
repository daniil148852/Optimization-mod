package com.ultralite.mixin;

import com.ultralite.UltraLiteMod;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow
    private ClientWorld world;

    /**
     * Отключаем рендер погоды (дождь/снег)
     */
    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void onRenderWeather(CallbackInfo ci) {
        if (UltraLiteMod.isInitialized() && UltraLiteMod.config.disableRainSnow) {
            ci.cancel();
        }
    }

    /**
     * Сбрасываем счётчик entity culler каждый кадр
     */
    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderStart(CallbackInfo ci) {
        if (UltraLiteMod.isInitialized()) {
            UltraLiteMod.entityCuller.resetFrame();
        }
    }
}
