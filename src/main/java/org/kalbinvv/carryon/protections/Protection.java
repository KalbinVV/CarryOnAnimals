package org.kalbinvv.carryon.protections;

import java.util.logging.Logger;


import org.bukkit.Server;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.kalbinvv.carryon.CarryOn;
import org.kalbinvv.carryon.protections.worldguard.WorldGuardProtection;

import com.palmergames.bukkit.towny.TownyAPI;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public interface Protection {

	public boolean check(Player player, Entity entity);
	public String getMessage();
	public String getName();

	public static void registerProtections() {
		CarryOn.setProtectionsList(new ProtectionsList());

		Logger logger = CarryOn.getPlugin().getLogger();

		Configuration configuration = CarryOn.getConfiguration();

		if(configuration.getBoolean("protections.enabled")) {
			registerProtection("Towny", () -> {
				registerTownyProtection();
			});

			registerProtection("WorldGuard", () -> {
				registerWorldGuardProtection();
			});
		}else {
			logger.warning("Protectons disabled!");
		}
	}

	private static void registerProtection(String name, Runnable registerRunnable) {
		Configuration configuration = CarryOn.getConfiguration();

		boolean protectionEnabled = configuration.getBoolean(String.format(
				"protections.%s.enabled", 
				name.toLowerCase()));

		Server server = CarryOn.getPlugin().getServer();
		Logger logger = CarryOn.getPlugin().getLogger();

		if(server.getPluginManager().getPlugin(name) != null) {
			if(protectionEnabled) {
				registerRunnable.run();
				logger.info(String.format(
						"%s protection is enabled!", 
						name));
			} else {
				logger.warning(String.format(
						"%s protections is disabled", 
						name));
			}
		} else {
			if(protectionEnabled) {
				logger.warning(String.format(
						"%s is not installed, protection not enabled!", 
						name));
			}
		}
	}

	private static void registerTownyProtection() {
		TownyAPI townyApi = TownyAPI.getInstance();

		CarryOn.getProtectionsList()
		       .addProtection(new TownyProtection(townyApi));
	}

	private static void registerWorldGuardProtection() {
		WorldGuard worldGuard = WorldGuard.getInstance();
		WorldGuardPlugin worldGuardPlugin = WorldGuardPlugin.inst();

		CarryOn.getProtectionsList()
			   .addProtection(new WorldGuardProtection(worldGuard, worldGuardPlugin));
	}

}
