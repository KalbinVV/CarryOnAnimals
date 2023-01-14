package org.kalbinvv.carryon.protections.worldguard;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

public class WorldGuardFlag {
	
	public static final String FLAG_NAME = "carry-on-animals";
	
	public static StateFlag CarryOnFlag;
	private static boolean registered = false;
	
	public static void register() {
		FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
	    try {
	        StateFlag flag = new StateFlag(FLAG_NAME, true);
	        registry.register(flag);
	        
	        CarryOnFlag = flag;
	        registered = true;
	    } catch (FlagConflictException e) {
	    	System.out.print(e.getMessage());
	    }
	}

	public static boolean isRegistered() {
		return registered;
	}
	
}
