package io.kryptoblocks.msa.network.stream.service.netflow.ipfix;

public interface IPFIXHeader {
	
	 // 2bytes, 10
    public abstract int version();

    // 2bytes
    public abstract int length();

    // 4bytes
    public abstract long exportTime();

    // 4bytes
    public abstract long flowSequence();
    
    public abstract long observationDomainID();
    
    

}
