package org.kalbinvv.carryon.sounds;

import org.bukkit.configuration.Configuration;

public class SoundsUtils {

	private static CarrySound pickUpSound = new PickUpSound();
	private static CarrySound placeSound = new PlaceSound();
	
	public static void loadSounds(Configuration configuration) {
		pickUpSound.load(configuration);
		placeSound.load(configuration);
	}
	
	public static CarrySound getPickUpSound() {
		return pickUpSound;
	}
	
	public static CarrySound getPlaceSound() {
		return placeSound;
	}
	
}
