package org.kalbinvv.carryonanimals.updates.migrations;

import java.util.HashMap;
import java.util.Map;

public class MigrationTo12Version extends Migration{

	@Override
	public Double getVersionOfMigration() {
		return 1.2;
	}

	@Override
	protected Map<String, Object> getConfigurationChanges() {
		var configurationChanges = new HashMap<String, Object>();
		
		// Initialize of pick up sound configuration
		configurationChanges.put(
				"sounds.onPickUp.enabled", 
				true);
		configurationChanges.put(
				"sounds.onPickUp.name", 
				"BLOCK_ANVIL_BREAK");
		configurationChanges.put(
				"sounds.onPickUp.volume", 
				4);
		configurationChanges.put(
				"sounds.onPickUp.pitch", 
				0.5);

		// Initialize of place sound configuration
		configurationChanges.put(
				"sounds.onPlace.enabled", 
				true);
		configurationChanges.put(
				"sounds.onPlace.name", 
				"BLOCK_ANVIL_FALL");
		configurationChanges.put(
				"sounds.onPlace.volume", 
				4);
		configurationChanges.put(
				"sounds.onPlace.pitch", 
				0.5);

		// Initialize of particles configuration
		configurationChanges.put(
				"particles.enabled", 
				true);
		configurationChanges.put(
				"particles.type", 
				"Cloud");
		configurationChanges.put(
				"particles.count", 
				10);
		
		return configurationChanges;
	}

}
