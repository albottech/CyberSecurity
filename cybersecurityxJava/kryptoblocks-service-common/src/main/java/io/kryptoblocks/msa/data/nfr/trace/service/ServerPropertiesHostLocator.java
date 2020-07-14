/*
 *  
 */

package io.kryptoblocks.msa.data.nfr.trace.service;

import java.lang.invoke.MethodHandles;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.sleuth.Span;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import io.kryptoblocks.msa.common.model.Host;

// TODO: Auto-generated Javadoc
/**
 * A {@link HostLocator} that retrieves:
 * 
 * <ul>
 *     <li><b>service name</b> - either from {@link Span#getProcessId()} or current application name</li>
 *     <li><b>address</b> - from {@link ServerProperties}</li>
 *     <li><b>port</b> - from lazily assigned port or {@link ServerProperties}</li>
 * </ul>.
 *
 * @author Associate-1
 * @since 1.0.0
 */
public class ServerPropertiesHostLocator implements HostLocator, EnvironmentAware {

	/** The Constant log. */
	private static final Log log = LogFactory.getLog(MethodHandles.lookup().lookupClass());
	
	/** The Constant IP_ADDRESS_PROP_NAME. */
	private static final String IP_ADDRESS_PROP_NAME = "spring.cloud.client.ipAddress";

	/** The server properties. */
	private final ServerProperties serverProperties; // Nullable
	
	/** The app name. */
	private final String appName;
	
	/** The inet utils. */
	private final InetUtils inetUtils;	
	
	/** The port. */
	private Integer port; // Lazy assigned
	
	/** The resolver. */
	private RelaxedPropertyResolver resolver;

	 
	 
	/**
	 * Instantiates a new server properties host locator.
	 *
	 * @param serverProperties the server properties
	 * @param appName the app name
	 * @param inetUtils the inet utils
	 */
	public ServerPropertiesHostLocator(ServerProperties serverProperties, String appName,
			 InetUtils inetUtils) {
		this.serverProperties = serverProperties;
		this.appName = appName;
		Assert.notNull(this.appName, "appName");		 
		if (inetUtils == null) {
			this.inetUtils = new InetUtils(new InetUtilsProperties());
		} else {
			this.inetUtils = inetUtils;
		}
	}

	/**
	 * Instantiates a new server properties host locator.
	 *
	 * @param serverProperties the server properties
	 * @param environment the environment
	 * @param inetUtils the inet utils
	 */
	public ServerPropertiesHostLocator(ServerProperties serverProperties,
			Environment environment, InetUtils inetUtils) {
		this(serverProperties, "", inetUtils);
		this.resolver = new RelaxedPropertyResolver(environment);
	}

	/**
	 * Locate.
	 *
	 * @param span the span
	 * @return the host
	 */
	@Override
	public Host locate(Span span) {
		String serviceName = getServiceName(span);
		String address = getAddress();
		Integer port = getPort();
		return new Host(serviceName, address, port);
	}

	/**
	 * Grab port.
	 *
	 * @param event the event
	 */
	@EventListener(EmbeddedServletContainerInitializedEvent.class)
	public void grabPort(EmbeddedServletContainerInitializedEvent event) {
		this.port = event.getEmbeddedServletContainer().getPort();
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	private Integer getPort() {
		if (this.port != null) {
			return this.port;
		}
		Integer port;
		if (this.serverProperties != null && this.serverProperties.getPort() != null && this.serverProperties.getPort() > 0) {
			port = this.serverProperties.getPort();
		}
		else {
			port = 8080;
		}
		return port;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	private String getAddress() {
		String address;
		if (this.serverProperties != null && this.serverProperties.getAddress() != null) {
			address = this.serverProperties.getAddress().getHostAddress();
		}
		else if (this.resolver != null) {
			address = this.resolver.getProperty(IP_ADDRESS_PROP_NAME, String.class);
		}
		else {
			address = this.inetUtils.findFirstNonLoopbackAddress().getHostAddress();
		}
		return address;
	}

	/**
	 * Gets the service name.
	 *
	 * @param span the span
	 * @return the service name
	 */
	private String getServiceName(Span span) {
		String serviceName = "unknown";		 
		if (log.isDebugEnabled()) {
			log.debug("Span will contain serviceName [" + serviceName + "]");
		}
		return serviceName;
	}

	/**
	 * Sets the environment.
	 *
	 * @param environment the new environment
	 */
	@Override
	public void setEnvironment(Environment environment) {
		this.resolver = new RelaxedPropertyResolver(environment);
	}
}
