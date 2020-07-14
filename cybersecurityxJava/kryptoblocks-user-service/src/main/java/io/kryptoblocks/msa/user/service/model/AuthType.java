/**
 * 
 */
package io.kryptoblocks.msa.user.service.model;


/**
 * @author insignia
 *
 */
public enum AuthType {
	
	
	FACEBOOK("FACEBOOK"), GOOGLE("GOOGLE"), TWITTER("TWITTER"), LINKEDIN("LINKEDIN"), SYSTEM("SYSTEM");
	

	private final String value;

	private AuthType(String value) {
		this.value = value;
	}

	
	public String getValue() {
		return value;
	}
	
	

	public static AuthType findByValue(String value) {
		if (value != null) {
			for (AuthType authType : AuthType.values()) {
				if (value.equalsIgnoreCase(authType.getValue())) {
					return authType;
				}
			}
		}

		return null;
	}

}
