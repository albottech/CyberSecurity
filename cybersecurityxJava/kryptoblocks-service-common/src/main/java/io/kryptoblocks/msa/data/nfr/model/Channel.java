package io.kryptoblocks.msa.data.nfr.model;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.key.ChannelKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// TODO: Auto-generated Javadoc
/**
 * The Class Channel.
 */
@Entity
@Table(name = ConfigName.CHANNEL_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new channel.
 */
@NoArgsConstructor

/**
 * Instantiates a new channel.
 *
 * @param key the key
 * @param country the country
 * @param state the state
 * @param city the city
 * @param status the status
 * @param createdTime the created time
 * @param updatedTime the updated time
 */
@AllArgsConstructor
public class Channel {
		 
		/** The key. */
		@EmbeddedId
		@OrderBy("key.id ASC")
		ChannelKey key;		
		
		/** The country. */
		@Column
		String country;
		
		/** The state. */
		@Column
		String state;
		
		/** The city. */
		@Column
		String city;
		
		/** The status. */
		@Column
		String status;
		
		/** The created time. */
		@Column
		String createdTime;
		
		/** The updated time. */
		@Column
		String updatedTime;
}

 
