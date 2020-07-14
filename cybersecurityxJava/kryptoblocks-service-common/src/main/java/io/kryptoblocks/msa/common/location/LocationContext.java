package io.kryptoblocks.msa.common.location;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.maps.GaeRequestHandler;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.GaeRequestHandler;
import com.google.maps.GeoApiContext;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.ElevationApi;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import io.kryptoblocks.msa.common.exception.ConfigException;
import io.kryptoblocks.msa.common.exception.LocationException;
import io.kryptoblocks.msa.common.messages.LocationMessage;
import io.kryptoblocks.msa.common.messages.MessageCollection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

// TODO: Auto-generated Javadoc
/**
 * The Class LocationContext.
 */
public class LocationContext {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LocationContext.class);
	
	/** The google api key. */
	@Value("${google.api.key}")
	private String googleApiKey;
	
	/**
	 * Gets the geo api context.
	 *
	 * @return the geo api context
	 */
	@Getter /**
  * Sets the geo api context.
  *
  * @param geoApiContext the new geo api context
  */
 @Setter (AccessLevel.PUBLIC)
	GeoApiContext geoApiContext;
	
	/**
	 * Post construct.
	 *
	 * @throws ConfigException the config exception
	 */
	@PostConstruct
	public void postConstruct() throws ConfigException {
		try {
		GeoApiContext geoApiContext = new GeoApiContext.Builder(new GaeRequestHandler.Builder())
			    .apiKey(googleApiKey)
			    .build();
		setGeoApiContext(geoApiContext);
		}catch(Exception e) {
			LOGGER.error(LocationMessage.LOCATION_CONFIG_EX_MS + MessageCollection.LOGGER_POSTFIX, ExceptionUtils.getFullStackTrace(e));
			throw new ConfigException(e);
		}
	}
	
	/**
	 * Gets the geo code from address.
	 *
	 * @param address the address
	 * @return the geo code from address
	 * @throws LocationException the location exception
	 */
	public String getGeoCodeFromAddress(String address) throws LocationException {
		String returnValue = null;
		try {
		GeocodingApiRequest req = GeocodingApi.geocode(geoApiContext, address);
		GeocodingResult[] results = req.await();
		returnValue = results[0].geometry.location.toString();		
		}catch(Exception e) {
			LOGGER.error(LocationMessage.GET_GEOCODE_FRM_ADDRESS_EX_MS + MessageCollection.LOGGER_POSTFIX, ExceptionUtils.getFullStackTrace(e));
			throw new LocationException(ExceptionUtils.getFullStackTrace(e));	
		}
		return returnValue;
	}
	
	/**
	 * Gets the address from geo code.
	 *
	 * @param geoCode the geo code
	 * @return the address from geo code
	 * @throws LocationException the location exception
	 */
	public String getAddressFromGeoCode(LatLng geoCode) throws LocationException{
		String returnValue = null;
		try {
		GeocodingApiRequest req = GeocodingApi.reverseGeocode(geoApiContext, geoCode);
		GeocodingResult[] results = req.await();
		returnValue = results[0].formattedAddress.toString();		
		}catch(Exception e) {
			LOGGER.error(LocationMessage.GET_ADDRESS_FRM_GEOCCODE_EX_MS + MessageCollection.LOGGER_POSTFIX, ExceptionUtils.getFullStackTrace(e));
			throw new LocationException(ExceptionUtils.getFullStackTrace(e));				
		}
		return returnValue;
	}
	
	/**
	 * Gets the direction.
	 *
	 * @param origin the origin
	 * @param destination the destination
	 * @return the direction
	 * @throws LocationException the location exception
	 */
	public DirectionsResult getDirection(String origin, String destination) throws LocationException {
		DirectionsResult returnValue = null;
		DirectionsApiRequest directionRequest = DirectionsApi.getDirections(geoApiContext, origin, destination);
		try {
			returnValue = directionRequest.await();
		} catch (ApiException | InterruptedException | IOException e) {
			LOGGER.error(LocationMessage.GET_DIRECTION_EX_MS + MessageCollection.LOGGER_POSTFIX, ExceptionUtils.getFullStackTrace(e));
			throw new LocationException(ExceptionUtils.getFullStackTrace(e));			
		}
		return returnValue;
	}
	
}
