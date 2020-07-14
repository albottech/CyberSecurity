package io.kryptoblocks.msa.common.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import io.kryptoblocks.msa.common.exception.ConfigException;
import io.kryptoblocks.msa.common.model.City;
import io.kryptoblocks.msa.common.names.MaxmindNames;
import io.kryptoblocks.msa.common.names.NameCollection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
 

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new city loader.
 */
@Data
public class CityLoader {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CityLoader.class);
	
	 
	/** The active country. */
	@Value("${service.ride.bid.mgmt.enabled.country}")	
	private String activeCountry;
	
	/** The active state. */
	@Value("${service.ride.bid.mgmt.enabled.state}")	
	private String activeState;
	
	/** The app name. */
	@Value("${spring.application.name}")
	private String appName;

	/** The app host name. */
	@Value("${spring.cloud.client.hostname}")
	private String appHostName;

	/** The app host port. */
	@Value("${server.port}")
	private String appHostPort;
	
	/**
	 * Gets the cities.
	 *
	 * @return the cities
	 */
	@Getter /**
  * Sets the cities.
  *
  * @param cities the new cities
  */
 @Setter (AccessLevel.PRIVATE)
	List<City> cities;
	
	/** The city id. */
	private String cityId;
	
	/** The environment. */
	@Autowired
    private Environment environment;
	
	
	/**
	 * Initialize.
	 *
	 * @throws ConfigException the config exception
	 */
	@PostConstruct
	public void initialize() throws ConfigException {
		 
		try {
		cities = loadObjectList(City.class, MaxmindNames.CITY_DATA_FILE_NAME);
		}catch(Exception e) {
			throw new ConfigException(e);
		}
	}

	/**
	 * Load object list.
	 *
	 * @param <T> the generic type
	 * @param type the type
	 * @param fileName the file name
	 * @return the list
	 * @throws Exception the exception
	 */
	private <T>List<T> loadObjectList(Class<T> type, String fileName) throws Exception {
	    try {
	        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
	        CsvMapper mapper = new CsvMapper();
	        Resource resource = new DefaultResourceLoader().getResource(fileName);
	        InputStream resourceStream = resource.getInputStream();
	        //File file = new ClassPathResource(fileName).getFile();
	        @SuppressWarnings("deprecation")
			MappingIterator<T> readValues = 
	          mapper.reader(type).with(bootstrapSchema).readValues(resourceStream);
	        return readValues.readAll();
	    } catch (Exception e) {
	        LOGGER.error("Error occurred while loading object list from file: {} ", fileName);
	        throw e;
	    }
	}
	
	/**
	 * Gets the cities by country and state.
	 *
	 * @param countryCode the country code
	 * @param stateCode the state code
	 * @return the cities by country and state
	 * @throws ConfigException the config exception
	 */
	public List<City> getCitiesByCountryAndState(String countryCode, String stateCode) throws ConfigException{
		List<City> returnValue = new ArrayList<City>();
		initialize();
		for(City city: cities) {
			if((city.getCountry_iso_code().toUpperCase().equals(countryCode)) && (city.getSubdivision_1_iso_code().equals(stateCode)) )	{
				returnValue.add(city);
			}		
		}		
		return returnValue;
	}
	
	/**
	 * Gets the city names by active country and state.
	 *
	 * @param countryCode the country code
	 * @param stateCode the state code
	 * @return the city names by active country and state
	 * @throws ConfigException the config exception
	 */
	public Collection<String> getCityNamesByActiveCountryAndState(String countryCode, String stateCode) throws ConfigException{
		Collection<String> returnValue = new ArrayList<String>();
		initialize();
		LOGGER.debug("cities count: {}", cities.size());
		LOGGER.debug("active country: {}", countryCode);
		LOGGER.debug("active state: {}", stateCode);
		for(City city: cities) {			
			if((city.getCountry_iso_code().toUpperCase().equals(countryCode)) && (city.getSubdivision_1_iso_code().equals(stateCode)) )	{
				String selectedCityName = city.getCity_name();
				LOGGER.debug("selected city name: {}", selectedCityName);
				returnValue.add(selectedCityName);
			}		
		}		
		return returnValue;
	}
	
	/**
	 * Gets the city names by active country and state.
	 *
	 * @return the city names by active country and state
	 */
	public Collection<String> getCityNamesByActiveCountryAndState(){
		Collection<String> returnValue = new ArrayList<String>();
		for(City city: cities) {
			if((city.getCountry_iso_code().toUpperCase().equals(activeCountry)) && (city.getSubdivision_1_iso_code().equals(activeState)) )	{
				returnValue.add(city.getCity_name());
			}		
		}		
		return returnValue;
	}
	
	/**
	 * Gets the city cordinator name.
	 *
	 * @param cityName the city name
	 * @param roleType the role type
	 * @param roleName the role name
	 * @return the city cordinator name
	 */
	public String getCityCordinatorName(String cityName, String roleType, String roleName){		
		 
		StringBuilder stringBuilder = new StringBuilder();		 
		stringBuilder.append(cityName.toUpperCase());
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);		
		if(this.getCityId() == null) {
			this.setCityId(UUID.randomUUID().toString().toUpperCase());
		}
		stringBuilder.append(getCityId());
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		stringBuilder.append(roleType);
		stringBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		stringBuilder.append(roleName);		
		return stringBuilder.toString();		
		 
	}	 
	
	 
}
