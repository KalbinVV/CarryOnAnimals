package org.kalbinvv.carryon.updates.migrations;

import java.util.HashMap;
import java.util.Map;

public class MigrationTo13Version extends Migration{

	@Override
	public Double getVersionOfMigration() {
		return 1.3;
	}

	@Override
	protected Map<String, Object> getConfigurationChanges() {
		var configurationChanges = new HashMap<String, Object>();
		
		configurationChanges.put(
				"helpMessage", 
				"§aHelp:\n"
				+ "§e/carryonanimals help - §fShow help\n"
				+ "§e/carryonanimals reload - §fReload plugin\n"
				+ "§e/carryonanimals protections - §fShow enabled protections");
		configurationChanges.put(
				"reloadMessage", 
				"§aPlugin was reloaded!");
		configurationChanges.put(
				"protectionsMessage", 
				"§eEnabled protections: §a");
		
		return configurationChanges;
	}

}
