package io.kryptoblocks.msa.network.stream.service.netflow.ipfix;

public class IPFIXHeaderImpl implements IPFIXHeader{
	
	/** The version. */
	private final int version;
    
	/** The count. */
	private final int length;
    
	/** The exportTime. */
	private final long exportTime;
    
	/** The flow sequence. */
	private final long flowSequence;
    
	/** The source ID. */
	private final long observationDomainID;

    /**
	 * Instantiates a new header.
	 *
	 * @param version the version
	 * @param length the count
	 * @param uptime the exportTime
	 * @param flowSequence the flow sequence
	 * @param sourceID the source ID
	 * @param sender the sender
	 * @param recipient the recipient
	 */
	public IPFIXHeaderImpl(int version, int length, long exportTime, long flowSequence, long observationDomainID) {
      this.version = version;
      this.length = length;
      this.exportTime = exportTime;
      this.flowSequence = flowSequence;
      this.observationDomainID = observationDomainID;
	}
	
	

	@Override
	public String toString() {
		return "IPFIXHeaderImpl [version=" + version + ", length=" + length + ", exportTime=" + exportTime
				+ ", flowSequence=" + flowSequence + ", observationDomainID=" + observationDomainID + "]";
	}



	@Override
	public int version() {
		return version;
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public long exportTime() {
		return exportTime;
	}

	@Override
	public long flowSequence() {
		return flowSequence;
	}

	@Override
	public long observationDomainID() {
		return observationDomainID;
	}
}
