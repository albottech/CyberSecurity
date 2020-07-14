/*
 *  
 */

package io.kryptoblocks.msa.common.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

// TODO: Auto-generated Javadoc
/**
 * Represents the host from which the span was sent.
 *
 * @author Associate-1
 * @since 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Host {

	/** The service name. */
	private String serviceName;
	
	/** The address. */
	private String address;
	
	/** The port. */
	private Integer port;

	/**
	 * Instantiates a new host.
	 */
	@SuppressWarnings("unused")
	private Host() {
	}

	/**
	 * Instantiates a new host.
	 *
	 * @param serviceName the service name
	 * @param address the address
	 * @param port the port
	 */
	public Host(String serviceName, String address, Integer port) {
		this.serviceName = serviceName;
		this.address = address;
		this.port = port;
	}

	/**
	 * Gets the ipv 4.
	 *
	 * @return the ipv 4
	 */
	@JsonIgnore
	public int getIpv4() {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(this.address);
		}
		catch (final UnknownHostException e) {
			throw new IllegalArgumentException(e);
		}
		return ByteBuffer.wrap(inetAddress.getAddress()).getInt();
	}

	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	public String getServiceName() {
		return this.serviceName;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public Integer getPort() {
		return this.port;
	}

	/**
	 * Sets the service name.
	 *
	 * @param serviceName the new service name
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(Integer port) {
		this.port = port;
	}
}
