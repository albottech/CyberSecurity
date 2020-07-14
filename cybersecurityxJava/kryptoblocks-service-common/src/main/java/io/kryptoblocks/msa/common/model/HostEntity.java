package io.kryptoblocks.msa.common.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class HostEntity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HostEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The service name. */
	@Value("${spring.application.name}")
	
	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	@Getter 
 /**
  * Sets the service name.
  *
  * @param serviceName the new service name
  */
 @Setter(AccessLevel.PUBLIC)
	private String serviceName;	
	
	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	@Getter /**
  * Sets the address.
  *
  * @param address the new address
  */
 @Setter(AccessLevel.PUBLIC)
	private String address;
	
	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	@Getter 
 /**
  * Sets the port.
  *
  * @param port the new port
  */
 @Setter(AccessLevel.PUBLIC)
	private Integer port;
	
	/**
	 * Instantiates a new host entity.
	 *
	 * @param address the address
	 * @param port the port
	 */
	public HostEntity( String address, Integer port) {		
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


}
