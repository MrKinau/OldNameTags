package dev.kinau.oldnametags.mixin;

import dev.kinau.oldnametags.OldNameTagsMod;
import net.minecraft.client.renderer.feature.TextFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(TextFeatureRenderer.Content.Text.class)
public abstract class TextFeatureRendererContentTextMixin {

	@ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true, name = "color")
	private static int modifyColor(int value) {
		if (!OldNameTagsMod.config.isEnabled()) return value;
		if (value == 2147483647)
			return 553648127;
		return value;
	}
}