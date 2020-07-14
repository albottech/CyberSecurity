package io.kryptoblocks.msa.udp.listner.service.model;

import java.io.Serializable;

 

import lombok.Data;

@Data
public class UDPListnerServiceActivity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String emailId;
	String time;
	String activity;
	String newData;
	String oldData;
	Object networkDataProcessActivity;
	
	 
	
	public static enum Type {
		
		NETWORK_DATA_SOURCING("NETWORK_DATA_SOURCING"),
		NETWORK_DATA_ENRICHMENT("NETWORK_DATA_ENRICHMENT"),
		NETWORK_DATA_SOR("NETWORK_DATA_SOR"),
		NETWORK_DATA_INDEX("NETWORK_DATA_INDEX"),
		NETWORK_DATA_AI("NETWORK_DATA_AI"),
		NETWORK_DATA_STRAIGHT_INDEXING("NETWORK_DATA_STRAIGHT_INDEXING"),
		NETWORK_DATA_SOR_INBOUND_STREAMING("NETWORK_DATA_SOR_INBOUND_STREAMING"),
		NETWORK_DATA_SOR_OUTBOUND_STREAMING("NETWORK_DATA_SOR_OUTBOUND-STREAMING"),
		NETWORK_DATA_AI_INBOUND_STREAMING("NETWORK_DATA_AI_INBOUND_STREAMING"),
		NETWORK_DATA_AI_OUTBOUND_STREAMING("NETWORK_DATA_AI_OUTBOUND_STREAMING");
		
		 
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

		 
		private Type(String value) {
			this.value = value;
		}

		 
		private final String value;

		 
		public String getValue() {
			return value;
		}

	}
	
public static enum Status {
		
		SUCCESS("SUCCESS"),
		FAILED("FAILED");
		
		 
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

		 
		private Status(String value) {
			this.value = value;
		}

		 
		private final String value;

		 
		public String getValue() {
			return value;
		}

	}

}
