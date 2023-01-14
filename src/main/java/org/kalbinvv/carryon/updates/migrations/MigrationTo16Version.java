package org.kalbinvv.carryon.updates.migrations;

import java.util.HashMap;
import java.util.Map;

public class MigrationTo16Version extends Migration{

	@Override
	public Double getVersionOfMigration() {
		return 1.6;
	}
	
	@Override
	protected Map<String, Object> getConfigurationChanges() {
		var configurationChanges = new HashMap<String, Object>();
		
		configurationChanges.put(
				"protections.towny.allowAlliance",
				false);
		configurationChanges.put(
				"protections.towny.allowNation", 
				false);
		
		return configurationChanges;
	}

}
