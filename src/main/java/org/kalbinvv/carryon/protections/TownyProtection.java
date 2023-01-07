package org.kalbinvv.carryon.protections;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.kalbinvv.carryon.CarryOn;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;

public class TownyProtection implements Protection{

	private final TownyAPI townyApi;
	
	public TownyProtection(TownyAPI townyApi) {
		this.townyApi = townyApi;
	}
	
	@Override
	public boolean check(Player player, Entity entity) {
		Location location = entity.getLocation();
		
		if(townyApi.isWilderness(location)) {
			return true;
		}
		
		Town town = townyApi.getTown(location);
		
		if(town.hasResident(player)) {
			return true;
		}else {
			return false;
		}

	}

	@Override
	public String getMessage() {
		return CarryOn.getConfiguration().getString("protections.towny.message");
	}

	@Override
	public String getName() {
		return "Towny";
	}

}
