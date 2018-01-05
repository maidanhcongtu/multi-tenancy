package com.mhdanh.constant;

public final class Queries {
	private Queries() { }
	
	public static final String RESET_ROLE = "RESET ROLE";
	public static final String SET_ROLE = "SET ROLE ?";
        public static final String SET_FLYWAY_TABLE_OWNER = "ALTER TABLE ?.schema_version OWNER TO ?;";
}
