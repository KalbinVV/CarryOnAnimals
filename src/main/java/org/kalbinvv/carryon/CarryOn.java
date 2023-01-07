package org.kalbinvv.carryon;

import java.io.File;


import java.io.FileNotFoundException;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kalbinvv.carryon.commands.CarryOnAnimalsCommand;
import org.kalbinvv.carryon.commands.CarryOnAnimalsTabCompleter;
import org.kalbinvv.carryon.events.CarryEvent;
import org.kalbinvv.carryon.events.QuitEvent;
import org.kalbinvv.carryon.protections.Protection;
import org.kalbinvv.carryon.protections.ProtectionsList;
import org.kalbinvv.carryon.protections.worldguard.WorldGuardFlag;
import org.kalbinvv.carryon.sounds.SoundsUtils;
import org.kalbinvv.carryon.updates.PluginUpdater;
import org.kalbinvv.carryon.utils.ChatUtils;
import org.kalbinvv.carryon.utils.ConfigurationUtils;

public class CarryOn extends JavaPlugin{

	private static Plugin plugin;
	private static FileConfiguration configuration;
	private static ProtectionsList protectionsList;

	@Override
	public void onEnable() {
		plugin = this;

		saveDefaultConfig();
		configuration = getConfig();

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

		enableMetrics();

		getServer().getPluginManager().registerEvents(
				new CarryEvent(), 
				getPlugin());

		getServer().getPluginManager().registerEvents(
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
						WorldGuardFlag.FlagName));
			}
		}
	}

	public static void loadConfiguration(boolean shouldBeReloaded) {
		if(shouldBeReloaded) {
			File configurationFile = new File(CarryOn
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

		ConfigurationUtils.initAllowedEntitiesTypes(configuration);
		ConfigurationUtils.loadParticleFromConfiguration(configuration);

		SoundsUtils.loadSounds(configuration);

		ChatUtils.setMessagesEnabled(configuration.getBoolean("messagesEnabled"));

		Protection.registerProtections();
	}

	private void enableMetrics() {
		try {
			int pluginId = 17282;
			Metrics metrics = new Metrics(this, pluginId);

			metrics.addCustomChart(new Metrics.SimplePie(
					"used_protections", 
					() -> {
						return protectionsList.getStringOfEnabledProtections();
					}));

			metrics.addCustomChart(new Metrics.SimplePie(
					"particles", 
					() -> {
						return ConfigurationUtils.isParticleRegistered() 
								? "Enabled" : "Disabled";
					}));

			metrics.addCustomChart(new Metrics.SimplePie(
					"the_sound_of_picking", 
					() -> {
						return SoundsUtils.getPickUpSound().isRegistered() 
								? "Enabled" : "Disabled";
					}));

			metrics.addCustomChart(new Metrics.SimplePie(
					"the_sound_of_placing", 
					() -> {
						return SoundsUtils.getPlaceSound().isRegistered() 
								? "Enabled" : "Disabled";
					}));

			getLogger().info("Metrics enabled!");
		} catch(Exception e) {
			getLogger().warning("Can't enable metrics!");
		}
	}

	@Override
	public void onDisable() {
		getLogger().info("Plugin disabled!");
	}

	public static FileConfiguration getConfiguration() {
		return configuration;
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static ProtectionsList getProtectionsList() {
		return protectionsList;
	}

	public static void setProtectionsList(ProtectionsList protectionsList) {
		CarryOn.protectionsList = protectionsList;
	}

}
