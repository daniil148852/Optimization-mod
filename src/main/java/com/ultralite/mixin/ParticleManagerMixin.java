package com.ultralite.mixin;

import com.ultralite.UltraLiteMod;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    private static int ultralite_particleCount = 0;

    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;",
            at = @At("HEAD"), cancellable = true)
    private void onAddParticle(ParticleEffect parameters, double x, double y, double z,
                               double velocityX, double velocityY, double velocityZ,
                               CallbackInfoReturnable<Particle> cir) {
        if (!UltraLiteMod.isInitialized()) return;

        // Полное отключение частиц
        if (UltraLiteMod.config.disableParticles) {
            cir.setReturnValue(null);
            return;
        }

        // Ограничение количества
        if (UltraLiteMod.config.reduceParticles) {
            ultralite_particleCount++;
            if (ultralite_particleCount > UltraLiteMod.config.maxParticles) {
                cir.setReturnValue(null);
                return;
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ultralite_particleCount = 0;
    }
}
