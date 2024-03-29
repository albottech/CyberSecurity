package io.kryptoblocks.msa.siem.stream.service.model;

 

// TODO: Auto-generated Javadoc
/**
 * The Class SiemProcessInput.
 */
public class SiemProcessInput {
	
	/**
	 * The Enum ActivityType.
	 */
	public enum ActivityType {	
		
		/** The get process status. */
		GET_PROCESS_STATUS("GET_PROCESS_STATUS"),
		
		/** The put data. */
		PUT_DATA("PUT_DATA");
		
		 
		
		/**
		 * Find by value.
		 *
		 * @param value the value
		 * @return the activity type
		 */
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
		 
		/**
		 * Instantiates a new activity type.
		 *
		 * @param value the value
		 */
		private ActivityType(String value) {
			this.value = value;
		}
		 
		/** The value. */
		private final String value;
		 
		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
	}

}
