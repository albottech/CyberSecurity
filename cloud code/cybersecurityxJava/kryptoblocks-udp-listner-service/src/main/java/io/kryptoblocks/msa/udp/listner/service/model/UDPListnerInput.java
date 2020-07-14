package io.kryptoblocks.msa.udp.listner.service.model;

 

public class UDPListnerInput {
	
	public enum ActivityType {	
		
		GET_PROCESS_STATUS("GET_PROCESS_STATUS");
		
		 
		
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
