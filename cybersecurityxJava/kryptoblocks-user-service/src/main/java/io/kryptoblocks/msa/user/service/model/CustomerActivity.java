package io.kryptoblocks.msa.user.service.model;

import java.io.Serializable;

 

import lombok.Data;

@Data
public class CustomerActivity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String emailId;
	String time;
	String activity;
	String newData;
	String oldData;
	
	 
	
	public static enum Type {
		
		CREDIT_CARD_GET_ACTIVITY("CREDIT_CARD_GET_ACTIVITY"),
		CREDIT_CARD_ADD_ACTIVITY("CREDIT_CARD_ADD_ACTIVITY"),
		CREDIT_CARD_UPDATE_ACTIVITY("CREDIT_CARD_UPDATE_ACTIVITY"),
		CREDIT_CARD_REMOVE_ACTIVITY("CREDIT_CARD_REMOVE_ACTIVITY"),
		DRIVER_LICENSE_GET_ACTIVITY("DRIVER_LICENSE_GET_ACTIVITY"),
		DRIVER_LICENSE_ADD_ACTIVITY("DRIVER_LICENSE_ADD_ACTIVITY"),
		DRIVER_LICENSE_UPDATE_ACTIVITY("DRIVER_LICENSE_UPDATE_ACTIVITY"),
		DRIVER_LICENSE_REMOVE_ACTIVITY("DRIVER_LICENSE_REMOVE_ACTIVITY"),
		VEHICLE_ADD_ACTIVITY("VEHICLE_ADD_ACTIVITY"),
		VEHICLE_GET_ACTIVITY("VEHICLE_GET_ACTIVITY"),
		VEHICLE_UPDATE_ACTIVITY("VEHICLE_UPDATE_ACTIVITY"),
		VEHICLE_REMOVE_ACTIVITY("VEHICLE_REMOVE_ACTIVITY"),
		BANK_ACCOUNT_ADD_ACTIVITY("BANK_ACCOUNT_ADD_ACTIVITY"),
		BANK_ACCOUNT_GET_ACTIVITY("BANK_ACCOUNT_GET_ACTIVITY"),
		BANK_ACCOUNT_UPDATE_ACTIVITY("BANK_ACCOUNT_UPDATE_ACTIVITY"),
		BANK_ACCOUNT_REMOVE_ACTIVITY("BANK_ACCOUNT_REMOVE_ACTIVITY");
		
		
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

}
