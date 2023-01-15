package org.kalbinvv.carryonanimals.utils;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticlesUtils {

	public static void spawnParticle(Particle particle, Location location, int count) {
		location.getWorld().spawnParticle(particle, location.getX(), 
				location.getY(), location.getZ(), count);
	}
	
}
