package io.kryptoblocks.msa.siem.stream.service.model;

import java.io.Serializable;

 

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new siem process service activity.
 */
@Data
public class SiemProcessServiceActivity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The email id. */
	String emailId;
	
	/** The time. */
	String time;
	
	/** The activity. */
	String activity;
	
	/** The new data. */
	String newData;
	
	/** The old data. */
	String oldData;
	
	/** The siem data process activity. */
	Object siemDataProcessActivity;
	
	 
	
	/**
	 * The Enum Type.
	 */
	public static enum Type {
		
		/** The siem data landing. */
		SIEM_DATA_LANDING("SIEM_DATA_LANDING"),
		
		/** The siem data sourcing. */
		SIEM_DATA_SOURCING("SIEM_DATA_SOURCING"),
		
		/** The siem data enrichment. */
		SIEM_DATA_ENRICHMENT("SIEM_DATA_ENRICHMENT"),
		
		/** The siem data sor. */
		SIEM_DATA_SOR("SIEM_DATA_SOR"),
		
		/** The siem data index. */
		SIEM_DATA_INDEX("SIEM_DATA_INDEX"),
		
		/** The siem data ai. */
		SIEM_DATA_AI("SIEM_DATA_AI"),
		
		/** The siem data straight indexing. */
		SIEM_DATA_STRAIGHT_INDEXING("SIEM_DATA_STRAIGHT_INDEXING"),
		
		/** The siem data sor inbound streaming. */
		SIEM_DATA_SOR_INBOUND_STREAMING("SIEM_DATA_SOR_INBOUND_STREAMING"),
		
		/** The siem data sor outbound streaming. */
		SIEM_DATA_SOR_OUTBOUND_STREAMING("SIEM_DATA_SOR_OUTBOUND-STREAMING"),
		
		/** The siem data ai inbound streaming. */
		SIEM_DATA_AI_INBOUND_STREAMING("SIEM_DATA_AI_INBOUND_STREAMING"),
		
		/** The siem data ai outbound streaming. */
		SIEM_DATA_AI_OUTBOUND_STREAMING("SIEM_DATA_AI_OUTBOUND_STREAMING");
		
		 
		/**
		 * Find by value.
		 *
		 * @param value the value
		 * @return the type
		 */
		public static Type findByValue(String value) {
			if (value != null) {
				for (Type type : Type.values()) {
					if (value.equalsIgnoreCase(type.getValue())) {
						return type;
					}
				}
			}

			return null;
		}

		 
		/**
		 * Instantiates a new type.
		 *
		 * @param value the value
		 */
		private Type(String value) {
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
	
/**
 * The Enum Status.
 */
public static enum Status {
		
		/** The success. */
		SUCCESS("SUCCESS"),
		
		/** The failed. */
		FAILED("FAILED");
		
		 
		/**
		 * Find by value.
		 *
		 * @param value the value
		 * @return the type
		 */
		public static Type findByValue(String value) {
			if (value != null) {
				for (Type type : Type.values()) {
					if (value.equalsIgnoreCase(type.getValue())) {
						return type;
					}
				}
			}

			return null;
		}

		 
		/**
		 * Instantiates a new status.
		 *
		 * @param value the value
		 */
		private Status(String value) {
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
