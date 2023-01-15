package org.kalbinvv.carryonanimals;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.kalbinvv.carryonanimals.commands.CarryOnAnimalsCommand;
import org.kalbinvv.carryonanimals.commands.CarryOnAnimalsTabCompleter;
import org.kalbinvv.carryonanimals.configuration.PluginConfiguration;
import org.kalbinvv.carryonanimals.events.CarryEvent;
import org.kalbinvv.carryonanimals.events.QuitEvent;
import org.kalbinvv.carryonanimals.protections.worldguard.WorldGuardFlag;
import org.kalbinvv.carryonanimals.updates.PluginUpdater;

public class CarryOnAnimals extends JavaPlugin{

	private static JavaPlugin plugin;
	private static PluginConfiguration configuration;

	@Override
	public void onEnable() {
		plugin = this;

		saveDefaultConfig();
		
		configuration = new PluginConfiguration(
				getDataFolder() + File.separator + "config.yml"
		);
		configuration.load();

		PluginUpdater pluginUpdater = new PluginUpdater();
		pluginUpdater.update(configuration);

		if(pluginUpdater.isPluginWasUpdated()) {
			getLogger().info(String.format(
					"Plugin was updated to %s!",
					pluginUpdater.getCurrentPluginVersion()));
			
			configuration.load();
		}

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

	@Override
	public FileConfiguration getConfig() {
		return configuration;
	}
	
	public static JavaPlugin getPlugin() {
		return plugin;
	}

}
