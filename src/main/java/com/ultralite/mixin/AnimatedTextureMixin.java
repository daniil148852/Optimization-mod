package com.ultralite.mixin;

import com.ultralite.UltraLiteMod;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpriteAtlasTexture.class)
public class AnimatedTextureMixin {

    /**
     * Отключаем обновление анимированных текстур (вода, лава, портал и т.д.)
     */
    @Inject(method = "tickAnimatedSprites", at = @At("HEAD"), cancellable = true)
    private void onTickAnimatedSprites(CallbackInfo ci) {
        if (UltraLiteMod.isInitialized() && UltraLiteMod.config.disableAnimatedTextures) {
            ci.cancel();
        }
    }
}
