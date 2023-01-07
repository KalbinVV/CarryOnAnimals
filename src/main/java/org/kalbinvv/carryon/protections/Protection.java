package org.kalbinvv.carryon.protections;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface Protection {

	public boolean check(Player player, Entity entity);
	public String getMessage();
	public String getName();
	
}
