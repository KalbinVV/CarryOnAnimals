package org.kalbinvv.carryonanimals.updates.migrations;

import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.Configuration;
import org.kalbinvv.carryonanimals.CarryOnAnimals;

public class MigrationTo15Version extends Migration{

	@Override
	public Double getVersionOfMigration() {
		return 1.5;
	}
	
	@Override
	protected Map<String, Object> getConfigurationChanges() {
		var configurationChanges = new HashMap<String, Object>();
		
		// Reconstruct messages configuration
		Configuration configuration = CarryOnAnimals.getConfiguration();
		
		configurationChanges.put(
				"messages.enabled", 
				configuration.getBoolean("messagesEnabled"));
		configurationChanges.put(
				"messages.prefix", 
				configuration.getString("prefix"));
		configurationChanges.put(
				"messages.permission", 
				configuration.getString("permissionMessage"));
		configurationChanges.put(
				"messages.invalidEntity", 
				configuration.getString("invalidEntityMessage"));
		configurationChanges.put(
				"messages.reload",
				configuration.getString("reloadMessage")
				);
		configurationChanges.put(
				"messages.protections", 
				configuration.getString("protectionsMessage"));
		configurationChanges.put(
				"messages.help", 
				configuration.getString("helpMessage"));
		
		return configurationChanges;
	}
	
	@Override
	public List<String> getPathsToRemove() {
		return Arrays.asList(
				"messagesEnabled",
				"prefix",
				"permissionMessage", 
				"invalidEntityMessage",
				"reloadMessage",
				"protectionsMessage",
				"helpMessage");
	}

}
