package org.kalbinvv.carryon;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.kalbinvv.carryon.commands.CarryOnAnimalsCommand;
import org.kalbinvv.carryon.commands.CarryOnAnimalsTabCompleter;
import org.kalbinvv.carryon.events.CarryEvent;
import org.kalbinvv.carryon.events.QuitEvent;
import org.kalbinvv.carryon.protections.ProtectionsList;
import org.kalbinvv.carryon.protections.TownyProtection;
import org.kalbinvv.carryon.protections.worldguard.WorldGuardFlag;
import org.kalbinvv.carryon.protections.worldguard.WorldGuardProtection;
import org.kalbinvv.carryon.sounds.SoundsUtils;
import org.kalbinvv.carryon.updates.PluginUpdater;
import org.kalbinvv.carryon.utils.ChatUtils;
import org.kalbinvv.carryon.utils.ConfigurationUtils;

import com.palmergames.bukkit.towny.TownyAPI;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class CarryOn extends JavaPlugin{

	private static Logger logger;
	private static Plugin plugin;
	private static FileConfiguration configuration;
	private static ProtectionsList protectionsList;

	@Override
	public void onEnable() {
		plugin = this;
		logger = getLogger();

		saveDefaultConfig();
		configuration = getConfig();
		
		PluginUpdater pluginUpdater = new PluginUpdater();
		pluginUpdater.update(configuration);

		loadConfiguration(false);

		enableMetrics();

		getServer().getPluginManager().registerEvents(new CarryEvent(), getPlugin());
		getServer().getPluginManager().registerEvents(new QuitEvent(), getPlugin());

		getCommand("carryonanimals").setExecutor(new CarryOnAnimalsCommand());
		getCommand("carryonanimals").setTabCompleter(new CarryOnAnimalsTabCompleter());
		
		getPluginLogger().info("Plugin is enabled!");
	}

	@Override
	public void onLoad() {
		if(getServer().getPluginManager().getPlugin("WorldGuard") != null) {
			WorldGuardFlag.register();

			if(WorldGuardFlag.isRegistered()) {
				getLogger().info(String.format("WorldGuard "
						+ "flag '%s' is registered!", WorldGuardFlag.FlagName));
			}
		}
	}

	private static void registerProtections() {
		protectionsList = new ProtectionsList();

		Server server = CarryOn.getPlugin().getServer();

		if(getConfiguration().getBoolean("protections.enabled")) {

			boolean townyEnabled = getConfiguration().getBoolean("protections.towny.enabled");

			if(server.getPluginManager().getPlugin("Towny") != null) {
				if(townyEnabled) {
					registerTownyProtection();
					getPluginLogger().info("Towny protection is enabled!");
				}else {
					getPluginLogger().warning("Towny protections is disabled");
				}
			}else {
				if(townyEnabled) {
					getPluginLogger().warning("Towny is not installed, "
							+ "protection not enabled!");
				}
			}

			boolean worldguardEnabled = getConfiguration().getBoolean("protections"
					+ ".worldguard.enabled");

			if(server.getPluginManager().getPlugin("WorldGuard") != null) {
				if(worldguardEnabled) {
					registerWorldGuardProtection();
					if(WorldGuardFlag.isRegistered()) {
						getPluginLogger().info("WorldGuard protection is enabled!");
					}else {
						getPluginLogger().warning("Can't register worldguard flag,"
								+ " protection is disabled!");
					}
				}else {
					getPluginLogger().warning("WorldGuard protection is disabled!");
				}	
			}else {
				if(worldguardEnabled) {
					getPluginLogger().warning("WorldGuard is not installed, "
							+ "protection not enabled!");
				}
			}
		}else {
			getPluginLogger().warning("Protectons disabled!");
		}
	}

	private static void registerTownyProtection() {
		TownyAPI townyApi = TownyAPI.getInstance();

		protectionsList.addProtection(new TownyProtection(townyApi));
	}

	private static void registerWorldGuardProtection() {
		WorldGuard worldGuard = WorldGuard.getInstance();
		WorldGuardPlugin worldGuardPlugin = WorldGuardPlugin.inst();

		protectionsList.addProtection(new WorldGuardProtection(worldGuard, worldGuardPlugin));
	}

	private void enableMetrics() {
		try {
			int pluginId = 17282;
			Metrics metrics = new Metrics(this, pluginId);

			metrics.addCustomChart(new Metrics.SimplePie("used_protections", () -> {
				return protectionsList.getStringOfEnabledProtections();
			}));
			
			metrics.addCustomChart(new Metrics.SimplePie("particles", () -> {
				return ConfigurationUtils.isParticleRegistered() ? "Enabled" : "Disabled";
			}));
			
			metrics.addCustomChart(new Metrics.SimplePie("the_sound_of_picking", () -> {
				return SoundsUtils.getPickUpSound().isRegistered() ? "Enabled" : "Disabled";
			}));
			
			metrics.addCustomChart(new Metrics.SimplePie("the_sound_of_placing", () -> {
				return SoundsUtils.getPlaceSound().isRegistered() ? "Enabled" : "Disabled";
			}));
			
			getPluginLogger().info("Metrics enabled!");
		} catch(Exception e) {
			getPluginLogger().warning("Can't enable metrics!");
		}
	}

	@Override
	public void onDisable() {
		getPluginLogger().info("Plugin disabled!");
	}

	public static Logger getPluginLogger() {
		return logger;
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

		registerProtections();
	}

}
