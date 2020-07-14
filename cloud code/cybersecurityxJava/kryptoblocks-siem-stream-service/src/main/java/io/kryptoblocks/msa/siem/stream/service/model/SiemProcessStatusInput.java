package io.kryptoblocks.msa.siem.stream.service.model;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new siem process status input.
 */
@Data
public class SiemProcessStatusInput implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The client ID. */
	String clientID;
	
	/** The date. */
	String date;
	
	/** The data center. */
	String dataCenter;

}
