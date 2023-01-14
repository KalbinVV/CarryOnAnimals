package org.kalbinvv.carryon.updates.migrations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MigrationTo18Version extends Migration{

	@Override
	public Double getVersionOfMigration() {
		return 1.8;
	}
	
	@Override
	public Map<String, Object> getConfigurationChanges() {
		var configurationChanges = new HashMap<String, Object>();
		
		configurationChanges.put(
				"worlds",
				Arrays.asList("world")
		);
		configurationChanges.put(
				"messages.notEnabledWorld",
				"§cThis world is not enabled!"
		);
		
		return configurationChanges;
	}

}