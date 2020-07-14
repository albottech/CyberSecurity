package io.kryptoblocks.msa.network.stream.service.exceptions;

public class FlowException extends RuntimeException{
    public FlowException() {
        super();
    }

    public FlowException(String message) {
        super(message);
    }
}
