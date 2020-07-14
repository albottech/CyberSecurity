package io.kryptoblocks.msa.network.stream.service.exceptions;

public class InvalidFlowVersionException extends FlowException{
	
	 public InvalidFlowVersionException(int version) {
	        super("Invalid NetFlow version " + version);
	    }
}
