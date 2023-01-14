package org.kalbinvv.carryon.updates.migrations;

import java.util.HashMap;
import java.util.Map;

public class MigrationTo17Version extends Migration{

	@Override
	public Double getVersionOfMigration() {
		return 1.7;
	}
	
	@Override
	protected Map<String, Object> getConfigurationChanges() {
		var configurationChanges = new HashMap<String, Object>();
		
		configurationChanges.put(
				"protections.towny.onlyKing", 
				false);
		
		return configurationChanges;
	}

}
