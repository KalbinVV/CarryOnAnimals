package org.kalbinvv.carryonanimals.api;

import org.kalbinvv.carryonanimals.protections.Protection;
import org.kalbinvv.carryonanimals.protections.ProtectionsList;

public class ApiManager {
	
	private static boolean apiWasUsed = false;

	public static void registerProtection(Protection protection) {
		ProtectionsList.init().addProtection(protection);
		apiWasUsed = true;
	}
	
	public static boolean isApiWasUsed() {
		return apiWasUsed;
	}
	
}
