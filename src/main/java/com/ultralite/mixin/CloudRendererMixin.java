package com.ultralite.mixin;

import com.ultralite.UltraLiteMod;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class CloudRendererMixin {

    @Inject(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lorg/joml/Matrix4f;FDDD)V",
            at = @At("HEAD"), cancellable = true)
    private void onRenderClouds(CallbackInfo ci) {
        if (UltraLiteMod.isInitialized() && UltraLiteMod.config.disableClouds) {
            ci.cancel();
        }
    }
}
