package org.kalbinvv.carryon.updates.migrations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.Configuration;

public abstract class Migration {
	
	public static final String VERSION_PATH = "version";
	
	public abstract Double getVersionOfMigration();
	
	protected abstract Map<String, Object> getConfigurationChanges();
	
	protected List<String> getPathsToRemove() {
		return new ArrayList<String>();
	}
	
	public void migrate(Configuration configuration) {
		for(var entry : getConfigurationChanges().entrySet()) {
			configuration.set(entry.getKey(), entry.getValue());
		}
		
		for(String path : getPathsToRemove()) {
			configuration.set(path, null);
		}
		
		configuration.set(VERSION_PATH, getVersionOfMigration());
	}
	
	// Return required migration, if migration not exists - return null
	public static Migration getRequiredMigration(Double currentPluginVersion) {
		Migration migration = null;
		
		if(currentPluginVersion.equals(1.0)) {
			migration = new MigrationTo11Version();
		}else if(currentPluginVersion.equals(1.1)) {
			migration = new MigrationTo12Version();
		}else if(currentPluginVersion.equals(1.2)) {
			migration = new MigrationTo13Version();
		}
		
		return migration;
	}
}
