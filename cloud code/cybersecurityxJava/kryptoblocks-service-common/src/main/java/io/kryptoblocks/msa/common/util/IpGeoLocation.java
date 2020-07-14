package io.kryptoblocks.msa.common.util;

import java.io.File;
import java.net.InetAddress;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.maxmind.geoip2.DatabaseReader;

import io.kryptoblocks.msa.common.exception.ConfigException;
import io.kryptoblocks.msa.common.names.MaxmindNames;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class IpGeoLocation.
 */
public class IpGeoLocation {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(IpGeoLocation.class);

	/**
	 * Gets the database reader.
	 *
	 * @return the database reader
	 */
	@Getter /**
  * Sets the database reader.
  *
  * @param databaseReader the new database reader
  */
 @Setter(AccessLevel.PRIVATE)
	DatabaseReader databaseReader;

	/**
	 * Post construct.
	 *
	 * @throws ConfigException the config exception
	 */
	@PostConstruct
	public void postConstruct() throws ConfigException {
		try {
			File file = new ClassPathResource(MaxmindNames.CITY_IP_DATA_FILE_NAME).getFile();
			databaseReader = new DatabaseReader.Builder(file).build();			
		}catch(Exception e) {
			throw new ConfigException(e);
		}
	}
	
	/**
	 * Gets the city from ip.
	 *
	 * @return the city from ip
	 */
	public String getCityFromIp() {
		String returnValue = null;
		try {
			return databaseReader.city(InetAddress.getLocalHost()).getCity().getName();
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		return returnValue;
		
	}
		
	

}

