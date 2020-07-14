package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import lombok.Getter;

@Getter
public class NetFlowV9ScopeDefImpl implements NetFlowV9ScopeDef{
	
	private final int type;
	
	private final int length;
	
	@Override
	public int type() {
		return type;
	}

	@Override
	public int length() {		
		return length;
	}

	/**
	 * @param type
	 * @param length
	 */
	public NetFlowV9ScopeDefImpl(int type, int length) {
		super();
		this.type = type;
		this.length = length;
	}
	

}
