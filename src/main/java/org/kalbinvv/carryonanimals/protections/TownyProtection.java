package org.kalbinvv.carryonanimals.protections;

import org.bukkit.Location;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.kalbinvv.carryonanimals.CarryOnAnimals;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
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

		Town townInEntityLocation = townyApi.getTown(location);

		if(townInEntityLocation.hasResident(player)) {
			return true;
		}else {
			Configuration configuration = CarryOnAnimals.getConfiguration();

			Town playerTown = townyApi.getTown(player);

			if(playerTown == null) {
				return false;
			}

			if(configuration.getBoolean("protections.towny.allowAlliance")) {
				if(townInEntityLocation.getAllies().contains(playerTown)) {
					return true;
				}
			}

			if(configuration.getBoolean("protections.towny.allowNation")) {
				Nation nation = townInEntityLocation.getNationOrNull();			

				if(nation == null) {
					return false;
				}

				if(configuration.getBoolean("protections.towny.onlyKing")) {
					if(nation.isKing(townyApi.getResident(player))) {
						return true;
					}
				}else {
					if(nation.getTowns().contains(playerTown)) {
						return true;
					}
				}
			}

			return false;
		}

	}

	@Override
	public String getMessage() {
		return CarryOnAnimals.getConfiguration().getString("protections.towny.message");
	}

	@Override
	public String getName() {
		return "Towny";
	}

}
