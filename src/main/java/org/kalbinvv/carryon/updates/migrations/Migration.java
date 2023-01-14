package org.kalbinvv.carryon.updates.migrations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.Configuration;

public abstract class Migration {
	
	public static final String VERSION_PATH = "version";
	
	private static Map<Double, Migration> migrationsMap;
	
	// Migrations map initialization block
	static {
		migrationsMap = new HashMap<Double, Migration>();
		
		migrationsMap.put(
				/* Current version = */ 1.0,
				/* Migration to update = */ new MigrationTo11Version()
		);
		migrationsMap.put(
				/* Current version = */ 1.1,
				/* Migration to update = */ new MigrationTo12Version()
		);
		migrationsMap.put(
				/* Current version = */ 1.2,
				/* Migration to update = */ new MigrationTo13Version()
		);
		migrationsMap.put(
				/* Current version = */ 1.3,
				/* Migration to update = */ new MigrationTo14Version()
		);
		migrationsMap.put(
				/* Current version = */ 1.4,
				/* Migration to update = */ new MigrationTo15Version()
		);
		migrationsMap.put(
				/* Current version = */ 1.5,
				/* Migration to update = */ new MigrationTo16Version()
		);
		migrationsMap.put(
				/* Current version = */ 1.6,
				/* Migration to update = */ new MigrationTo17Version()
		);
		migrationsMap.put(
				/* Current version = */ 1.7,
				/* Migration to update = */ new MigrationTo18Version()
		);
	}
	
	public abstract Double getVersionOfMigration();
	
	protected Map<String, Object> getConfigurationChanges(){
		return new HashMap<String, Object>();
	}
	
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
		return migrationsMap.get(currentPluginVersion);
	}
}
