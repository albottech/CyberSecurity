package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

import java.util.List;

public interface TemplateFlowSet extends FlowSet{
	/**
	 * Template ID.
	 *
	 * @return the short
	 */
	short templateID();

    /**
	 * Fields.
	 *
	 * @return the list
	 */
	List<TemplateField> fields();
}
