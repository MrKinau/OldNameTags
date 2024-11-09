package dev.kinau.oldnametags;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.blaze3d.platform.InputConstants;
import dev.kinau.oldnametags.config.OldNameTagsConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class OldNameTagsMod implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("oldnametags");
	public static OldNameTagsConfig config;
	private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

	@Override
	public void onInitialize() {
        try {
			File configFile = getConfigFile();
			if (configFile.exists())
            	config = GSON.fromJson(new FileReader(configFile), OldNameTagsConfig.class);
			if (config == null) {
				config = new OldNameTagsConfig();
				saveConfig();
			}
        } catch (FileNotFoundException ex) {
            LOGGER.error("Failed to load oldnametags config", ex);
        }

        KeyMapping toggleKeyMapping = new KeyMapping("key.oldnametags.toggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KeyMapping.CATEGORY_MISC);
		KeyBindingHelper.registerKeyBinding(toggleKeyMapping);

		ClientTickEvents.END_CLIENT_TICK.register(e -> {
			while (toggleKeyMapping.consumeClick()) {
				config.setEnabled(!config.isEnabled());
				saveConfig();
				Minecraft.getInstance().gui.setOverlayMessage(config.isEnabled() ?
								Component.translatable("text.oldnametags.toggled.on").withColor(CommonColors.GREEN) :
								Component.translatable("text.oldnametags.toggled.off").withColor(CommonColors.SOFT_RED),
						false);
			}
		});
	}

	private static File getConfigFile() {
		File configDir = FabricLoader.getInstance().getConfigDir().toFile();
		if (!configDir.exists())
			configDir.mkdirs();
		return new File(configDir, "oldnametags.json");
	}

	public static void saveConfig() {
		String configJson = GSON.toJson(config);
		try {
			Files.writeString(getConfigFile().toPath(), configJson);
		} catch (IOException ex) {
			LOGGER.error("Failed to save oldnametags config", ex);
		}
	}
}