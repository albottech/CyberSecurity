package io.kryptoblocks.msa.common.names;

// TODO: Auto-generated Javadoc
/**
 * The Class ChannelQuery.
 */
public class ChannelQuery {
	
	/** The Constant FIND_CHANNEL_BYNAME_USING_OPTION. */
	public static final String FIND_CHANNEL_BYNAME_USING_OPTION = "SELECT * FROM CHANNEL WHERE NAME = ?0";
	
	/** The Constant FIND_CHANNEL_BYCITY_USING_STREAM. */
	public static final String FIND_CHANNEL_BYCITY_USING_STREAM = "SELECT * FROM CHANNEL WHERE COUNTRY = ?0 AND STATE = ?1 AND CITY = ?2 ";
		
	/** The Constant FIND_CHANNEL_BYSTATE_USING_STREAM. */
	public static final String FIND_CHANNEL_BYSTATE_USING_STREAM = "SELECT * FROM CHANNEL WHERE COUNTRY = ?0 AND STATE = ?1";
	
	/** The Constant FIND_CHANNEL_BYCOUNTRY_USING_STREAM. */
	public static final String FIND_CHANNEL_BYCOUNTRY_USING_STREAM = "SELECT * FROM CHANNEL WHERE COUNTRY = ?0";
	

}
