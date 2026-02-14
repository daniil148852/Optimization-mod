package com.ultralite.mixin;

import com.ultralite.UltraLiteMod;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameOptions.class)
public class BiomeColorMixin {

    @Shadow
    private SimpleOption<Integer> biomeBlendRadius;

    /**
     * Принудительно устанавливаем минимальный biome blend radius.
     */
    @Inject(method = "getBiomeBlendRadius", at = @At("HEAD"), cancellable = true)
    private void onGetBiomeBlendRadius(CallbackInfoReturnable<SimpleOption<Integer>> cir) {
        if (UltraLiteMod.isInitialized() && UltraLiteMod.config.reduceBiomeBlending) {
            biomeBlendRadius.setValue(UltraLiteMod.config.biomeBlendRadius);
        }
    }
}
