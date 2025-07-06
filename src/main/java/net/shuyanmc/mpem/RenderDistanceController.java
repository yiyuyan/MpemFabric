package net.shuyanmc.mpem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.shuyanmc.mpem.config.CoolConfig;

public class RenderDistanceController {
    public static int getActiveRenderDistance() {
        GameOptions options = MinecraftClient.getInstance().options;
        return options.getViewDistance().getValue();
    }

    public static int getInactiveRenderDistance() {
        if (CoolConfig.REDUCE_RENDER_DISTANCE_WHEN_INACTIVE.get()) {
            return CoolConfig.INACTIVE_RENDER_DISTANCE.get();
        }
        return getActiveRenderDistance();
    }
}