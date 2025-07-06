package net.shuyanmc.mpem.mixin;

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.shuyanmc.mpem.config.CoolConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.Settings.class)
public abstract class ItemProMixin {

    @Shadow public abstract <T> Item.Settings component(ComponentType<T> type, T value);

    @Inject(method = "<init>",at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        if(CoolConfig.SPEC.isLoaded() && CoolConfig.ENABLED.get()) this.component(DataComponentTypes.MAX_STACK_SIZE,CoolConfig.MAX_STACK_SIZE.get());
    }

    @Inject(method = "maxCount",at = @At("RETURN"))
    public void stackTo(int p_41488_, CallbackInfoReturnable<Item.Settings> cir) {
        if(CoolConfig.SPEC.isLoaded() && CoolConfig.ENABLED.get()) this.component(DataComponentTypes.MAX_STACK_SIZE,CoolConfig.MAX_STACK_SIZE.get());
    }
}
