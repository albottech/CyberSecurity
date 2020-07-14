package io.kryptoblocks.msa.common.model;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionActivityEntity.
 */
@JsonIgnoreProperties( { "destination" })
public class ExceptionActivityEntity implements Serializable {
	
	
		
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
	 * Gets the destination.
	 *
	 * @return the destination
	 */
	@Getter /**
  * Sets the destination.
  *
  * @param destination the new destination
  */
 @Setter(AccessLevel.PUBLIC)
	@Value("${service.exception.activity.stream}")
	String destination;
	
	
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Getter /**
  * Sets the id.
  *
  * @param id the new id
  */
 @Setter(AccessLevel.PUBLIC)
	String id;
	
	/**
	 * Gets the reported time.
	 *
	 * @return the reported time
	 */
	@Getter /**
  * Sets the reported time.
  *
  * @param reportedTime the new reported time
  */
 @Setter(AccessLevel.PUBLIC)
	String reportedTime;
	
	/**
	 * Gets the prossed time time.
	 *
	 * @return the prossed time time
	 */
	@Getter /**
  * Sets the prossed time time.
  *
  * @param prossedTimeTime the new prossed time time
  */
 @Setter(AccessLevel.PUBLIC)
	String prossedTimeTime;
	
	/**
	 * Gets the indexed time.
	 *
	 * @return the indexed time
	 */
	@Getter /**
  * Sets the indexed time.
  *
  * @param indexedTime the new indexed time
  */
 @Setter(AccessLevel.PUBLIC)
	String indexedTime;
	
	/**
	 * Checks if is alert status.
	 *
	 * @return true, if is alert status
	 */
	@Getter /**
  * Sets the alert status.
  *
  * @param alertStatus the new alert status
  */
 @Setter(AccessLevel.PUBLIC)
	boolean alertStatus;
	
	/**
	 * Gets the alert notification msg.
	 *
	 * @return the alert notification msg
	 */
	@Getter /**
  * Sets the alert notification msg.
  *
  * @param alertNotificationMsg the new alert notification msg
  */
 @Setter(AccessLevel.PUBLIC)
	String alertNotificationMsg;
	
	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	@Getter /**
  * Sets the time.
  *
  * @param time the new time
  */
 @Setter(AccessLevel.PUBLIC)
	String time;
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	@Getter /**
  * Sets the location.
  *
  * @param location the new location
  */
 @Setter(AccessLevel.PUBLIC)
	String location;
	
	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	@Getter /**
  * Sets the details.
  *
  * @param details the new details
  */
 @Setter(AccessLevel.PUBLIC)
	String details;
	
	
	
	
	
	
	
	

}
