package org.kalbinvv.carryon.protections;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ProtectionsList {

	private final List<Protection> protections = new LinkedList<Protection>();
	private String message;
	private String stringOfEnabledProtections = "";

	public void addProtection(Protection protection) {
		protections.add(protection);

		if(!stringOfEnabledProtections.isEmpty()) {
			stringOfEnabledProtections += ", ";
		}
		
		stringOfEnabledProtections += protection.getName();
	}

	public boolean checkAll(Player player, Entity entity) {

		if(player.hasPermission("carryonanimals.bypass")) {
			return true;
		}

		for(Protection protection : protections) {
			if(!protection.check(player, entity)) {
				message = protection.getMessage();
				return false;
			}
		}

		return true;
	}

	public String getMessage() {
		return message;
	}

	public String getStringOfEnabledProtections() {
		if(protections.isEmpty()) {
			return "Nothing";
		}
		
		return stringOfEnabledProtections;
	}

}
