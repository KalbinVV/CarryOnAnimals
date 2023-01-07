package org.kalbinvv.carryon.sounds;

import java.util.logging.Logger;

import org.bukkit.Sound;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.kalbinvv.carryon.CarryOn;

public abstract class CarryEventSound implements CarrySound {

	public abstract String getName();
	
	private Sound sound;
	private Float volume;
	private Float pitch;
	private boolean registered;

	@Override
	public void load(Configuration configuration) {
		boolean soundEnabled = configuration.getBoolean(String.format("sounds"
				+ ".%s.enabled", getName()));
		
		if(!soundEnabled) {
			registered = false;
			return;
		}
		
		String soundName = configuration.getString(String.format("sounds"
				+ ".%s.name", getName()));
		
		Logger logger = CarryOn.getPlugin().getLogger();
		
		try {
			sound = Sound.valueOf(soundName.toUpperCase());
			volume = (float) configuration.getDouble(String.format("sounds"
					+ ".%s.volume", getName()));
			pitch = (float) configuration.getDouble(String.format("sounds"
					+ ".%s.pitch", getName()));
			
			registered = true;
			
			logger.info(String.format("Sound '%s' registred!", soundName));
			
		} catch (IllegalArgumentException e) {
			registered = false;
		
			logger.warning(String.format("Can't register '%s' sound!", soundName));
			logger.warning(String.format("Reason: %s", e.getMessage()));
		}
		
	}

	@Override
	public boolean isRegistered() {
		return registered;
	}

	@Override
	public Float getVolume() {
		return volume;
	}

	@Override
	public Float getPitch() {
		return pitch;
	}

	@Override
	public void play(Player player) {
		player.playSound(player.getLocation(), sound, volume, pitch);
	}
}
