package org.kalbinvv.carryonanimals.updates;

import java.io.File;

import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.kalbinvv.carryonanimals.CarryOnAnimals;
import org.kalbinvv.carryonanimals.configuration.PluginConfiguration;
import org.kalbinvv.carryonanimals.updates.migrations.Migration;
import org.kalbinvv.carryonanimals.updates.migrations.MigrationException;

public class PluginUpdater {

	private static final Double LAST_PLUGIN_VERSION = 2.0;
	private static final String CONFIGURATION_FILE_NAME = "config.yml";

	private boolean pluginWasUpdated = false;
	private Double currentPluginVersion = 1.0;

	public void update(FileConfiguration configuration) throws MigrationException {

		if(configuration.contains(Migration.VERSION_PATH)) {
			currentPluginVersion = configuration.getDouble(Migration.VERSION_PATH);
		}

		if(currentPluginVersion < LAST_PLUGIN_VERSION) {
			var migration = Migration.getRequiredMigration(currentPluginVersion);

			migration.migrate(configuration);

			try {
				configuration.save(new File(
						CarryOnAnimals.getPlugin().getDataFolder(), 
						CONFIGURATION_FILE_NAME));
				
				// BugFix: last version was saved in map
				((PluginConfiguration) configuration).clearSavedData();
			} catch (IOException e) {
				e.printStackTrace();
			}

			pluginWasUpdated = true;
			currentPluginVersion = migration.getVersionOfMigration();

			if(currentPluginVersion < LAST_PLUGIN_VERSION) {
				update(configuration);
			}
		}
	}

	public boolean isPluginWasUpdated() {
		return pluginWasUpdated;
	}

	public Double getCurrentPluginVersion() {
		return currentPluginVersion;
	}

}
