package io.kryptoblocks.msa.user.repository.name;

public class ConfigName {
	
	public static final String USER_PERSISTENCE_UNIT_NAME = "cassandra_user";
	public static final String USER_PERSISTENCE_XML_LOCATION = "classpath*:META-INF/persistence.xml";	
	public static final String USER_PERSISTENCE_SCHEMA_NAME = "kryptoblocks_user@cassandra_user";
	
	public static final String USER_DL_TABLE_NAME = "user_dl";
	public static final String USER_CREDIT_CARD_TABLE_NAME = "user_credit_card";
	public static final String USER_BABK_ACCOUNT_TABLE_NAME = "user_bank_account";
	public static final String USER_ADDRESS_TABLE_NAME = "user_address";
	public static final String USER_VEHICLE_TABLE_NAME = "user_vehicle";
	
	public static final String USER_TABLE_NAME = "user";
	public static final String USER_LOGIN_TABLE_NAME = "user_login";
	public static final String USER_PW_RESET_TABLE_NAME = "user_pwreset_token";
	public static final String USER_VER_TOKEN_TABLE_NAME = "user_verify_token";
	
	
}
