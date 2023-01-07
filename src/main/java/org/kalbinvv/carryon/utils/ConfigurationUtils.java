package org.kalbinvv.carryon.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Particle;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.kalbinvv.carryon.CarryOn;

public class ConfigurationUtils{

	private static Set<EntityType> allowedEntitiesTypes = null;
	
	private static Particle particle;
	private static Integer particleCount;
	private static boolean particleRegistered = false;

	public static List<String> getDefaultAllowedEntitiesTypes() {
		return new ArrayList<String>(Arrays.asList("Sheep", "Chicken", 
				"Cow", "Pig", "Wolf"));
	}

	public static Set<EntityType> initAllowedEntitiesTypes(Configuration configuration) {
		if(allowedEntitiesTypes != null) {
			return allowedEntitiesTypes;
		}

		allowedEntitiesTypes = new HashSet<EntityType>();

		List<String> allowedEntitiesTypesList = configuration.getStringList("allowedEntities");

		Logger logger = CarryOn.getPluginLogger();

		for(String entityTypeName : allowedEntitiesTypesList) {
			EntityType entityType = EntityType.valueOf(entityTypeName.toUpperCase());

			if(entityType != null) {
				allowedEntitiesTypes.add(entityType);
				logger.info(String.format("%s was registered as allowed!", 
						entityTypeName));
			} else {
				logger.info(String.format("Can't register %s as allowed!", 
						entityTypeName));
			}
		}

		return allowedEntitiesTypes;
	}

	public static void loadParticleFromConfiguration(Configuration configuration) {
		
		boolean particleEnabled = configuration.getBoolean("particles.enabled");
		
		if(!particleEnabled) {
			particleRegistered = false;
			return;
		}
		
		String particleName = configuration.getString("particles.type");

		Logger logger = CarryOn.getPluginLogger();

		try {
			particle = Particle.valueOf(particleName.toUpperCase());
			particleCount = configuration.getInt("particles.count");
			particleRegistered = true;

			logger.info(String.format("Particle '%s' registered!", 
					particleName));
		} catch (IllegalArgumentException e) {
			particleRegistered = false;
			
			logger.warning(String.format("Can't register '%s' particle!", 
					particleName));
			logger.warning(String.format("Reason: %s", e.getMessage()));
		}
	}

	public static Particle getParticle() {
		return particle;
	}

	public static Integer getParticleCount() {
		return particleCount;
	}

	public static boolean isParticleRegistered() {
		return particleRegistered;
	}
	

}
