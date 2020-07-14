package io.kryptoblocks.msa.network.stream.service.model;

import java.io.Serializable;

 

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new network process service activity.
 */
@Data
public class NetworkProcessServiceActivity implements Serializable {
	
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
	
	/** The network data process activity. */
	Object networkDataProcessActivity;
	
	 
	
	/**
	 * The Enum Type.
	 */
	public static enum Type {
		
		/** The network data sourcing. */
		NETWORK_DATA_SOURCING("NETWORK_DATA_SOURCING"),
		
		/** The network data enrichment. */
		NETWORK_DATA_ENRICHMENT("NETWORK_DATA_ENRICHMENT"),
		
		/** The network data sor. */
		NETWORK_DATA_SOR("NETWORK_DATA_SOR"),
		
		/** The network data index. */
		NETWORK_DATA_INDEX("NETWORK_DATA_INDEX"),
		
		/** The network data ai. */
		NETWORK_DATA_AI("NETWORK_DATA_AI"),
		
		/** The network data straight indexing. */
		NETWORK_DATA_STRAIGHT_INDEXING("NETWORK_DATA_STRAIGHT_INDEXING"),
		
		/** The network data sor inbound streaming. */
		NETWORK_DATA_SOR_INBOUND_STREAMING("NETWORK_DATA_SOR_INBOUND_STREAMING"),
		
		/** The network data sor outbound streaming. */
		NETWORK_DATA_SOR_OUTBOUND_STREAMING("NETWORK_DATA_SOR_OUTBOUND-STREAMING"),
		
		/** The network data ai inbound streaming. */
		NETWORK_DATA_AI_INBOUND_STREAMING("NETWORK_DATA_AI_INBOUND_STREAMING"),
		
		/** The network data ai outbound streaming. */
		NETWORK_DATA_AI_OUTBOUND_STREAMING("NETWORK_DATA_AI_OUTBOUND_STREAMING");
		
		 
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
