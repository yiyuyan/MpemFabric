package net.shuyanmc.mpem.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.shuyanmc.mpem.config.CoolConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item getItem();

    @Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
    private void onGetMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        int configMax = CoolConfig.maxStackSize.get();
        if(configMax<0) return;
        if (configMax > 0) {
            int vanillaMax = this.getItem().getMaxCount();
            cir.setReturnValue(Math.min(configMax, vanillaMax));
        }
    }

    @Inject(method = "isStackable", at = @At("HEAD"), cancellable = true)
    private void onIsStackable(CallbackInfoReturnable<Boolean> cir) {
        int configMax = CoolConfig.maxStackSize.get();
        if (configMax == 0) {
            cir.setReturnValue(this.getItem().getMaxCount() > 1);
        }
    }
}