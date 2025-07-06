package net.shuyanmc.mpem.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.shuyanmc.mpem.FrameRateController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftMixin {
    @Inject(method = "getFramerateLimit", at = @At("HEAD"), cancellable = true)
    private void onGetFramerateLimit(CallbackInfoReturnable<Integer> cir) {
        MinecraftClient instance = MinecraftClient.getInstance();
        if (!instance.isWindowFocused()) {
            cir.setReturnValue(FrameRateController.getInactiveFrameRateLimit());
        }
    }
}