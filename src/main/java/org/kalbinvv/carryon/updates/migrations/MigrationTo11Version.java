package org.kalbinvv.carryon.updates.migrations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kalbinvv.carryon.utils.ConfigurationUtils;

public class MigrationTo11Version extends Migration{

	@Override
	public Double getVersionOfMigration() {
		return 1.1;
	}

	@Override
	protected Map<String, Object> getConfigurationChanges() {
		var configurationChanges = new HashMap<String, Object>();
		
		configurationChanges.put(
				"messagesEnabled", 
				true);
		configurationChanges.put(
				"allowedEntities", 
				ConfigurationUtils.getDefaultAllowedEntitiesTypes());
		configurationChanges.put(
				"invalidEntityMessage", 
				"");
		
		return configurationChanges;
	}
	
	@Override
	protected List<String> getPathsToRemove() {
		return Arrays.asList("canCarryPlayers");
	}

}
