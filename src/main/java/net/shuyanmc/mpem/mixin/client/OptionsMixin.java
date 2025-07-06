package net.shuyanmc.mpem.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.shuyanmc.mpem.RenderDistanceController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameOptions.class)
public class OptionsMixin {
    @Inject(method = "getClampedViewDistance", at = @At("HEAD"), cancellable = true)
    private void onGetEffectiveRenderDistance(CallbackInfoReturnable<Integer> cir) {
        MinecraftClient instance = MinecraftClient.getInstance();
        if (!instance.isWindowFocused()) {
            cir.setReturnValue(RenderDistanceController.getInactiveRenderDistance());
        }
    }
}