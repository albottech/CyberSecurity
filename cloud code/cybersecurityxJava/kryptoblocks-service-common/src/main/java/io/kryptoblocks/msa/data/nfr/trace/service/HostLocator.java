/*
 *  
 */

package io.kryptoblocks.msa.data.nfr.trace.service;

import org.springframework.cloud.sleuth.Span;

import io.kryptoblocks.msa.common.model.Host;

// TODO: Auto-generated Javadoc
/**
 * Strategy for locating a {@link Host "host"} from a Spring Cloud Span (and whatever other
 * environment properties might be available).
 *
 * @author Associate-1
 * @since 1.0.0
 * 
 */
 
public interface HostLocator {

	/**
	 * Locate.
	 *
	 * @param span the span
	 * @return the host
	 */
	Host locate(Span span);

}
