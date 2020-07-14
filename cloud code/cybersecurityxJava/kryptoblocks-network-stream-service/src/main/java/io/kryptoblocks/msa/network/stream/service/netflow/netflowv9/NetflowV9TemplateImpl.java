package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import java.util.List;

public class NetflowV9TemplateImpl implements NetFlowV9Template{
	
	private int templateId;
	private int fieldCount;
	private List<NetFlowV9FieldDef> definitions;
	
	@Override
	public int templateId() {
		return templateId;
	}

	@Override
	public int fieldCount() {
		return fieldCount;
	}

	@Override
	public List<NetFlowV9FieldDef> definitions() {
		return definitions;
	}

}
