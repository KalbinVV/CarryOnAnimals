package org.kalbinvv.carryon.sounds;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public interface CarrySound {

	public void load(Configuration configuration);
	public boolean isRegistered();
	public Float getVolume();
	public Float getPitch();
	public void play(Player player);
	
}
