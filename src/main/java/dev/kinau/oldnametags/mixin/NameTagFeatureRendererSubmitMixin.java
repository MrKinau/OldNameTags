package dev.kinau.oldnametags.mixin;

import dev.kinau.oldnametags.OldNameTagsMod;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(NameTagFeatureRenderer.Submit.class)
public abstract class NameTagFeatureRendererSubmitMixin {

	@ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 1, argsOnly = true)
	private static int modifyColor(int value) {
		if (!OldNameTagsMod.config.isEnabled()) return value;
		if (value == -2130706433)
			return 553648127;
		return value;
	}
}