package org.kalbinvv.carryonanimals.protections;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

// Singleton pattern
public class ProtectionsList {

	private static ProtectionsList protectionsListPtr = null;
	
	private List<Protection> protections;
	private String message;
	private String stringOfEnabledProtections = "";

	private ProtectionsList() {
		protections = new LinkedList<Protection>();
	}
	
	public static ProtectionsList init() {
		if(protectionsListPtr != null) {
			protectionsListPtr = new ProtectionsList();
		}
		
		return protectionsListPtr;
	}
	
	// Set protections list pointer to null to make it reload.
	public static void clear() {
		protectionsListPtr = null;
	}
	
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
