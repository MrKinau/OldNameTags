package dev.kinau.oldnametags.mixin;

import dev.kinau.oldnametags.OldNameTagsMod;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Font.class)
public abstract class FontMixin {

    @Inject(method = "drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)V", at = @At("HEAD"))
    private void test(Component component, float f, float g, int i, boolean bl, Matrix4f matrix4f, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int j, int k, CallbackInfo ci) {

    }

	@ModifyArg(method = "drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;prepareText(Lnet/minecraft/util/FormattedCharSequence;FFIZZI)Lnet/minecraft/client/gui/Font$PreparedText;"), index = 3)
	private int init(int i) {
		if (!OldNameTagsMod.config.isEnabled()) return i;
		if (i == -2130706433)
			return 553648127;
		return i;
	}
}