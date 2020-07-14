package io.kryptoblocks.msa.siem.stream.service.model;

import lombok.Data;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new siem data inject input.
 */
@Data
public class SiemDataInjectInput implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The client ID. */
	String clientID;
	
	/** The date. */
	String date;
	
	/** The input data. */
	String inputData;

}
