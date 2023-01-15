package org.kalbinvv.carryonanimals.protections;

import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.kalbinvv.carryonanimals.CarryOnAnimals;
import org.kalbinvv.carryonanimals.protections.worldguard.WorldGuardProtection;

import com.palmergames.bukkit.towny.TownyAPI;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public interface Protection {

	public boolean check(Player player, Entity entity);
	public String getMessage();
	public String getName();

	public static void registerProtections() {
		CarryOnAnimals.setProtectionsList(new ProtectionsList());

		final Logger logger = CarryOnAnimals.getPlugin().getLogger();

		final Configuration configuration = CarryOnAnimals.getConfiguration();

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
		Configuration configuration = CarryOnAnimals.getConfiguration();

		boolean protectionEnabled = configuration.getBoolean(String.format(
				"protections.%s.enabled", 
				name.toLowerCase()));

		Server server = CarryOnAnimals.getPlugin().getServer();
		Logger logger = CarryOnAnimals.getPlugin().getLogger();

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

		CarryOnAnimals.getProtectionsList()
		.addProtection(new TownyProtection(townyApi));
	}

	private static void registerWorldGuardProtection() {
		WorldGuard worldGuard = WorldGuard.getInstance();
		WorldGuardPlugin worldGuardPlugin = WorldGuardPlugin.inst();

		CarryOnAnimals.getProtectionsList()
		.addProtection(new WorldGuardProtection(worldGuard, worldGuardPlugin));
	}

}
