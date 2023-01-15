package org.kalbinvv.carryonanimals;

import java.io.File;



import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.kalbinvv.carryonanimals.commands.CarryOnAnimalsCommand;
import org.kalbinvv.carryonanimals.commands.CarryOnAnimalsTabCompleter;
import org.kalbinvv.carryonanimals.events.CarryEvent;
import org.kalbinvv.carryonanimals.events.QuitEvent;
import org.kalbinvv.carryonanimals.protections.Protection;
import org.kalbinvv.carryonanimals.protections.ProtectionsList;
import org.kalbinvv.carryonanimals.protections.worldguard.WorldGuardFlag;
import org.kalbinvv.carryonanimals.sounds.SoundsUtils;
import org.kalbinvv.carryonanimals.updates.PluginUpdater;
import org.kalbinvv.carryonanimals.utils.ChatUtils;
import org.kalbinvv.carryonanimals.utils.ConfigurationUtils;

public class CarryOnAnimals extends JavaPlugin{

	private static JavaPlugin plugin;
	private static FileConfiguration configuration;
	private static ProtectionsList protectionsList;

	@Override
	public void onEnable() {
		plugin = this;

		File configurationFile = new File(getDataFolder(), "config.yml");

		if(configurationFile.exists()) {
			configuration = new YamlConfiguration();
			try {
				configuration.load(configurationFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}else {
			saveDefaultConfig();
			configuration = getConfig();
		}

		PluginUpdater pluginUpdater = new PluginUpdater();
		pluginUpdater.update(configuration);

		if(pluginUpdater.isPluginWasUpdated()) {
			getLogger().info(String.format(
					"Plugin was updated to %s!",
					pluginUpdater.getCurrentPluginVersion()));
		}else {
			getLogger().info(String.format(
					"Current version of plugin: %s",
					pluginUpdater.getCurrentPluginVersion()));
		}
		

		loadConfiguration(false);

		Utils.enableMetrics();

		final PluginManager pluginManager = getServer().getPluginManager();

		pluginManager.registerEvents(
				new CarryEvent(), 
				getPlugin());

		pluginManager.registerEvents(
				new QuitEvent(), 
				getPlugin());

		getCommand("carryonanimals").setExecutor(new CarryOnAnimalsCommand());
		getCommand("carryonanimals").setTabCompleter(new CarryOnAnimalsTabCompleter());

		getLogger().info("Plugin is enabled!");
	}

	@Override
	public void onLoad() {
		if(getServer().getPluginManager().getPlugin("WorldGuard") != null) {
			WorldGuardFlag.register();

			if(WorldGuardFlag.isRegistered()) {
				getLogger().info(String.format(
						"WorldGuard flag '%s' is registered!", 
						WorldGuardFlag.FLAG_NAME));
			}
		}
	}

	public static void loadConfiguration(boolean shouldBeReloaded) {
		if(shouldBeReloaded) {
			File configurationFile = new File(CarryOnAnimals
					.getPlugin().getDataFolder(), "config.yml");

			configuration = new YamlConfiguration();
			try {
				configuration.load(configurationFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}

		ConfigurationUtils.loadWorldsFromConfiguration(configuration);
		ConfigurationUtils.loadAllowedEntitiesTypes(configuration);
		ConfigurationUtils.loadParticleFromConfiguration(configuration);
		
		SoundsUtils.loadSounds(configuration);

		ChatUtils.setMessagesEnabled(configuration.getBoolean("messages.enabled"));

		Protection.registerProtections();
	}

	@Override
	public void onDisable() {
		getLogger().info("Plugin disabled!");
	}

	public static FileConfiguration getConfiguration() {
		return configuration;
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public static ProtectionsList getProtectionsList() {
		return protectionsList;
	}

	public static void setProtectionsList(ProtectionsList protectionsList) {
		CarryOnAnimals.protectionsList = protectionsList;
	}

}
