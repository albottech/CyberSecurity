package io.kryptoblocks.msa.data.nfr.app.statistics.model;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.app.statistics.key.DataSourceInfoKey;
import io.kryptoblocks.msa.data.nfr.app.statistics.udt.DataSource;
import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class DataSourceInfo.
 */
@Entity
@Table(name = ConfigName.APP_CONTAINER_DATA_SOURCE_INFO_TABLE_NAME, schema= ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new data source info.
 */
@NoArgsConstructor

/**
 * Instantiates a new data source info.
 *
 * @param key the key
 * @param dataSources the data sources
 */
@AllArgsConstructor
public class DataSourceInfo {
	
	/** The key. */
	@EmbeddedId
	DataSourceInfoKey key;
	
	/** The data sources. */
	@ElementCollection
	Map<String, DataSource> dataSources;
	
	 
	 
}
