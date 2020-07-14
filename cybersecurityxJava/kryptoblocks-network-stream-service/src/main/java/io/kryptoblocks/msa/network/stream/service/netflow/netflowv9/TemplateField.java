package io.kryptoblocks.msa.network.stream.service.netflow.netflowv9;

public interface TemplateField {

	/**
	 * Type.
	 *
	 * @return the short
	 */
	short type();

    /**
	 * Length.
	 *
	 * @return the short
	 */
	short length();
}
