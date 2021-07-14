package com.red.masaadditions.litematica_additions.litematica_mixin;

import com.red.masaadditions.litematica_additions.util.MiscUtils;
import fi.dy.masa.litematica.gui.GuiMainMenu;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiMainMenu.class, remap = false)
public abstract class MixinGuiMainMenu extends GuiBase {
    @Shadow
    protected abstract int getButtonWidth();

    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Lfi/dy/masa/litematica/gui/GuiMainMenu;createChangeMenuButton(IIILfi/dy/masa/litematica/gui/GuiMainMenu$ButtonListenerChangeMenu$ButtonType;)V", ordinal = 6), index = 1)
    private int createSchematicManagerButton(int y) {
        return y - 22;
    }

    @Inject(method = "initGui", at = @At("RETURN"))
    private void initGui(CallbackInfo ci) {
        int width = this.getButtonWidth();
        ButtonGeneric button = new ButtonGeneric(width + 32, 74, width, 20, "Open Schematics Folder");
        this.addButton(button, new MiscUtils.ButtonListenerOpenFolder());
    }
}