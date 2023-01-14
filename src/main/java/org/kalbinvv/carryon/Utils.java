package org.kalbinvv.carryon;

import org.bukkit.plugin.java.JavaPlugin;
import org.kalbinvv.carryon.protections.ProtectionsList;
import org.kalbinvv.carryon.sounds.SoundsUtils;
import org.kalbinvv.carryon.utils.ConfigurationUtils;

public class Utils {

	public static void enableMetrics() {
		int pluginId = 17282;
		JavaPlugin plugin = CarryOn.getPlugin();
		
		try {
			ProtectionsList protectionsList = CarryOn.getProtectionsList();
			
			Metrics metrics = new Metrics(plugin, pluginId);

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

			plugin.getLogger().info("Metrics enabled!");
		} catch(Exception e) {
			plugin.getLogger().warning("Can't enable metrics!");
		}
	}
	
	
}