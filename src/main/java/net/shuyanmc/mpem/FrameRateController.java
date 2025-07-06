// FrameRateController.java
package net.shuyanmc.mpem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.shuyanmc.mpem.config.CoolConfig;

public class FrameRateController {
    public static int getActiveFrameRateLimit() {
        GameOptions options = MinecraftClient.getInstance().options;
        // Use Forge's getter method for framerateLimit
        return options.getMaxFps().getValue();
    }

    public static int getInactiveFrameRateLimit() {
        if (CoolConfig.REDUCE_FPS_WHEN_INACTIVE.get()) {
            return CoolConfig.INACTIVE_FPS_LIMIT.get();
        }
        return getActiveFrameRateLimit();
    }
}