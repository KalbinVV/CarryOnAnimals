package org.kalbinvv.carryon.protections.worldguard;

import org.bukkit.entity.Entity;

import org.bukkit.entity.Player;
import org.kalbinvv.carryon.CarryOn;
import org.kalbinvv.carryon.protections.Protection;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

public class WorldGuardProtection implements Protection{


	private final WorldGuard worldGuard;
	private final WorldGuardPlugin worldGuardPlugin;

	public WorldGuardProtection(WorldGuard worldGuard, 
			WorldGuardPlugin worldGuardPlugin) {
		this.worldGuard = worldGuard;
		this.worldGuardPlugin = worldGuardPlugin;
	}

	@Override
	public boolean check(Player player, Entity entity) {
		if(!WorldGuardFlag.isRegistered()) {
			CarryOn.getPlugin().getLogger().warning(
					"WorldGuard flag is not registered, "
					+ "can't check protection!");
			return true;
		}

		LocalPlayer localPlayer = worldGuardPlugin.wrapPlayer(player);

		RegionContainer container = worldGuard.getPlatform().getRegionContainer();
		
		Location location = new Location(
				localPlayer.getWorld(), 
				entity.getLocation().getX(),
				entity.getLocation().getY(), 
				entity.getLocation().getZ()
				);

		RegionQuery query = container.createQuery();

		if(!query.testState(location, localPlayer, WorldGuardFlag.CarryOnFlag)) {
			return false;
		}

		return true;
	}

	@Override
	public String getMessage() {
		return CarryOn.getConfiguration().getString("protections.worldguard.message");
	}

	@Override
	public String getName() {
		return "WorldGuard";
	}

}
