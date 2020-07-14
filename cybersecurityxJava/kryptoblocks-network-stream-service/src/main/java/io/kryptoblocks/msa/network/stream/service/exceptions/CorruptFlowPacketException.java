package io.kryptoblocks.msa.network.stream.service.exceptions;

public class CorruptFlowPacketException extends FlowException {
	
    public CorruptFlowPacketException() {
        super();
    }

    public CorruptFlowPacketException(String message) {
        super(message);
    }
}
