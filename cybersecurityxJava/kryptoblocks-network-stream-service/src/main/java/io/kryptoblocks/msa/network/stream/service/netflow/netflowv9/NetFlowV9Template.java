package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import java.util.List;

public interface NetFlowV9Template {
	
	    int templateId();

	    int fieldCount();

	    List<NetFlowV9FieldDef> definitions();
}
