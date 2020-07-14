package io.kryptoblocks.msa.data.nfr.udt;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class City.
 */
@Embeddable

/**
 * Instantiates a new city.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new city.
 *
 * @param geonameId the geoname id
 * @param localeCode the locale code
 * @param continentCode the continent code
 * @param continentName the continent name
 * @param countryIsoCode the country iso code
 * @param countryName the country name
 * @param subdivision1IsoCode the subdivision 1 iso code
 * @param subdivision1Name the subdivision 1 name
 * @param subdivision2IsoCode the subdivision 2 iso code
 * @param subdivision2Name the subdivision 2 name
 * @param cityName the city name
 * @param metroCode the metro code
 * @param timeZone the time zone
 */
@AllArgsConstructor
public class City {

	/** The geoname id. */
	@Column
	String geonameId;
	
	/** The locale code. */
	@Column
	String localeCode;
	
	/** The continent code. */
	@Column
	String continentCode;
	
	/** The continent name. */
	@Column
	String continentName;
	
	/** The country iso code. */
	@Column
	String countryIsoCode;
	
	/** The country name. */
	@Column
	String countryName;
	
	/** The subdivision 1 iso code. */
	@Column
	String subdivision1IsoCode;
	
	/** The subdivision 1 name. */
	@Column
	String subdivision1Name;
	
	/** The subdivision 2 iso code. */
	@Column
	String subdivision2IsoCode;
	
	/** The subdivision 2 name. */
	@Column
	String subdivision2Name;
	
	/** The city name. */
	@Column
	String cityName;
	
	/** The metro code. */
	@Column
	String metroCode;
	
	/** The time zone. */
	@Column
	String timeZone;

}
