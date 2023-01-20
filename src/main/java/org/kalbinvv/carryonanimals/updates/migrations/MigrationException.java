package org.kalbinvv.carryonanimals.updates.migrations;

public class MigrationException extends Exception{

	private static final long serialVersionUID = 7739364360899730940L;

	private final String message;
	private final Double version;
	
	public MigrationException(String message, Double version) {
		this.message = message;
		this.version = version;
	}
	
	@Override
	public String getMessage() {
		return String.format(
				"Migration exception\nMessage: %s\nVersion: %s", 
				message, version.toString());
	}
	
}
