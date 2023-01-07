package org.kalbinvv.carryon.protections.worldguard;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

public class WorldGuardFlag {

	public static StateFlag CarryOnFlag;
	public static String FlagName = "carry-on-animals";
	private static boolean registered = false;
	
	public static void register() {
		FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
	    try {
	        StateFlag flag = new StateFlag(FlagName, true);
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
