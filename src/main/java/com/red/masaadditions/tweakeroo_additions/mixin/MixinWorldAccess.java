package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import net.minecraft.world.LunarWorldView;
import net.minecraft.world.RegistryWorldView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WorldAccess.class, priority = 1001)
public interface MixinWorldAccess extends RegistryWorldView, LunarWorldView {
    @Shadow
    WorldProperties getLevelProperties();

    /**
     * @author Red.#9015
     * @reason Isn't possible to inject into interfaces. Overwrite shouldn't affect most other mods though.
     */
    @Overwrite()
    default long getLunarTime() {
        if (FeatureToggleExtended.TWEAK_OVERRIDE_SKY_TIME.getBooleanValue())
            return ConfigsExtended.Generic.SKY_TIME_OVERRIDE.getIntegerValue();
        return this.getLevelProperties().getTimeOfDay();
    }
}
