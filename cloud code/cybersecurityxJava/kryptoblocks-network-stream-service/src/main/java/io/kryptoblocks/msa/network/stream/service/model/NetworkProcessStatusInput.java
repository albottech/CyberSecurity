package io.kryptoblocks.msa.network.stream.service.model;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new network process status input.
 */
@Data
public class NetworkProcessStatusInput implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The client ID. */
	String clientID;
	
	/** The date. */
	String date;
	
	/** The data center. */
	String dataCenter;

}
