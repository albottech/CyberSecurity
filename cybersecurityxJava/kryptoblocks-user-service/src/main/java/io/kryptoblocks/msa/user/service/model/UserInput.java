package io.kryptoblocks.msa.user.service.model;

 

public class UserInput {
	
	public enum ActivityType {			
		 
		SIGN_UP("SIGN_UP"),
		SIGN_UP_CONFIRM("SIGN_UP_CONFIRM"),
		SEND_ACTIVATION_CODE("SEND_ACTIVATION_CODE");
		
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
	
	public enum VerificationType {			
		 
		SIGN_UP_VERIFICATION("SIGN_UP_VERIFICATION"),
		CREDIT_CARD_ADDED_VERIFICATION("CREDIT_CARD_ADDED_VERIFICATION"),
		DRIVER_LICENSE_ADDED_VERIFICATION("DRIVER_LICENSE_ADDED_VERIFICATION"),
		VEHICLE_ADDED_VERIFICATION("VEHICLE_ADDED_VERIFICATION");
		
		
		
		public static VerificationType findByValue(String value) {
			if (value != null) {
				for (VerificationType verificationType : VerificationType.values()) {
					if (value.equalsIgnoreCase(verificationType.getValue())) {
						return verificationType;
					}
				}
			}

			return null;
		}
		 
		private VerificationType(String value) {
			this.value = value;
		}
		 
		private final String value;
		 
		public String getValue() {
			return value;
		}
	}


}
