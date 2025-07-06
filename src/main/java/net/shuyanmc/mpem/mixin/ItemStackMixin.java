package net.shuyanmc.mpem.mixin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.dynamic.Codecs;
import net.shuyanmc.mpem.config.CoolConfig;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.item.ItemStack.ITEM_CODEC;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item getItem();

    @Shadow private int count;

    @Unique
    private static Codec<ItemStack> SELF_CODEC = Codec.lazyInitialized(() -> {
        return RecordCodecBuilder.create((instance) -> {
            return instance.group(ITEM_CODEC.fieldOf("id").forGetter(ItemStack::getRegistryEntry), Codecs.rangedInt(1, Math.max(CoolConfig.MAX_STACK_SIZE.get(),CoolConfig.getmaxe())).fieldOf("count").orElse(1).forGetter(ItemStack::getCount), ComponentChanges.CODEC.optionalFieldOf("components", ComponentChanges.EMPTY).forGetter(ItemStack::getComponentChanges)).apply(instance, ItemStack::new);
        });
    });;

    @Inject(method = "<init>*",at = @At("TAIL"))
    private void init(ItemConvertible item, CallbackInfo ci){
        if(CoolConfig.SPEC.isLoaded()){
            if(CoolConfig.ENABLED.get() && CODEC!=SELF_CODEC){
                CODEC = SELF_CODEC;
            }
        }
    }

    @Inject(method = "getMaxCount", at = @At("HEAD"), cancellable = true)
    private void onGetMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        int configMax = CoolConfig.maxStackSize.get();
        if(CoolConfig.ENABLED.get()){
            cir.setReturnValue(CoolConfig.MAX_STACK_SIZE.get());
            if(CODEC!=SELF_CODEC){
                CODEC = SELF_CODEC;
            }
            return;
        }
        if(configMax<0) return;
        if (configMax > 0) {
            int vanillaMax = this.getItem().getMaxCount();
            cir.setReturnValue(Math.min(configMax, vanillaMax));
        }
    }

    @Inject(method = "isStackable", at = @At("HEAD"), cancellable = true)
    private void onIsStackable(CallbackInfoReturnable<Boolean> cir) {
        if(CoolConfig.ENABLED.get() && CODEC!=SELF_CODEC){
            CODEC = SELF_CODEC;
        }
        int configMax = CoolConfig.maxStackSize.get();
        if (configMax == 0) {
            cir.setReturnValue(this.getItem().getMaxCount() > 1);
        }
    }

    @Mutable
    @Shadow @Final public static Codec<ItemStack> CODEC;
}