package io.kryptoblocks.msa.index.service.util;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

// TODO: Auto-generated Javadoc
/**
 * The Class IndexResourceUtil.
 */
public class IndexResourceUtil {
	
	
/**
 * Gets the index resource.
 *
 * @param location the location
 * @return the index resource
 * @throws IOException Signals that an I/O exception has occurred.
 */
public Resource[] getIndexResource(String location) throws IOException {
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] indexResources = resolver.getResources(location);
	    return indexResources;
	}

	/**
	 * Gets the resource as stream.
	 *
	 * @param resource the resource
	 * @return the resource as stream
	 */
	public InputStream getResourceAsStream(String resource) {
	    final InputStream in
	            = getContextClassLoader().getResourceAsStream(resource);

	    return in == null ? getClass().getResourceAsStream(resource) : in;
	}

	/**
	 * Gets the context class loader.
	 *
	 * @return the context class loader
	 */
	public ClassLoader getContextClassLoader() {
	    return Thread.currentThread().getContextClassLoader();
	}

}
