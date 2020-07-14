package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class NetFlowV9RecordImpl  implements NetFlowV9Record{
	
	ImmutableMap<String, Object> fields;
	
	@Override
	public ImmutableMap<String, Object> fields() {
		return fields;
	}

}
