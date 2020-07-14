package io.kryptoblocks.msa.user.service.model;

 

public class AuthInput {
	
	public enum ActivityType {	
		
		AUTH_USER("AUTH_USER"),
		GET_LOGGED_USER("GET_LOGGED_USER");
		 
		
		public static ActivityType findByValue(String value) {
			if (value != null) {
				for (ActivityType activityType : ActivityType.values()) {
					if (value.equalsIgnoreCase(activityType.getValue())) {
						return activityType;
					}
				}
			}

			return null;
		}
		 
		private ActivityType(String value) {
			this.value = value;
		}
		 
		private final String value;
		 
		public String getValue() {
			return value;
		}
	}

}
