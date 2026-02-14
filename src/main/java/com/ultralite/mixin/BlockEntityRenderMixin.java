package com.ultralite.mixin;

import com.ultralite.UltraLiteMod;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderMixin {

    @Inject(method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V",
            at = @At("HEAD"), cancellable = true)
    private <E extends BlockEntity> void onRender(E blockEntity, float tickDelta,
                                                   MatrixStack matrices,
                                                   VertexConsumerProvider vertexConsumers,
                                                   CallbackInfo ci) {
        if (!UltraLiteMod.isInitialized()) return;

        if (UltraLiteMod.config.disableBlockEntityAnimation) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                double dist = client.player.squaredDistanceTo(
                        blockEntity.getPos().getX(),
                        blockEntity.getPos().getY(),
                        blockEntity.getPos().getZ());

                // Не рендерим далёкие block entity (сундуки, таблички и т.д.)
                if (dist > 32 * 32) {
                    ci.cancel();
                }
            }
        }
    }
}
