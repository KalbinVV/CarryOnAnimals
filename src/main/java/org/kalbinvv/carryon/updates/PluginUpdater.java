package org.kalbinvv.carryon.updates;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.kalbinvv.carryon.CarryOn;
import org.kalbinvv.carryon.updates.migrations.Migration;

public class PluginUpdater {

	private static final Double LAST_PLUGIN_VERSION = 1.3;
	private static final String CONFIGURATION_FILE_NAME = "config.yml";

	public void update(FileConfiguration configuration) {
		Double currentPluginVersion = 1.0;

		if(configuration.contains(Migration.VERSION_PATH)) {
			currentPluginVersion = configuration.getDouble(Migration.VERSION_PATH);
		}

		if(currentPluginVersion < LAST_PLUGIN_VERSION) {
			Migration migration = Migration.getRequiredMigration(currentPluginVersion);

			migration.migrate(configuration);

			try {
				configuration.save(new File(
						CarryOn.getPlugin().getDataFolder(), 
						CONFIGURATION_FILE_NAME));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(migration.getVersionOfMigration() < LAST_PLUGIN_VERSION) {
				update(configuration);
			}
		}
	}

}
