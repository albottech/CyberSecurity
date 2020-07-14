package io.kryptoblocks.msa.udp.listner.service.model;

import lombok.Data;

@Data
public class UDPListnerStatusInput implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String clientID;
	String date;
	String dataCenter;

}
