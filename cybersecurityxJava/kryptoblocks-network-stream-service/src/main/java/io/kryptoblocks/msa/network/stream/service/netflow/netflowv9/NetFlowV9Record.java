package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import com.google.common.collect.ImmutableMap;

public interface  NetFlowV9Record {
	
	ImmutableMap<String, Object> fields();
}
