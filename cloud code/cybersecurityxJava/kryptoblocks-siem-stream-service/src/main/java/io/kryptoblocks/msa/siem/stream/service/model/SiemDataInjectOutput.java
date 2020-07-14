package io.kryptoblocks.msa.siem.stream.service.model;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new siem data inject output.
 */
@Data
public class SiemDataInjectOutput implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The client ID. */
	String clientID;
	
	/** The date. */
	String date;
	
	/** The output data. */
	String outputData;

}
