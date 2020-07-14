package io.kryptoblocks.msa.data.nfr.notification.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.data.nfr.config.ConfigName;
import io.kryptoblocks.msa.data.nfr.notification.key.NotificationKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationActivity.
 */
@Entity
@Table(name = ConfigName.NOTIFICATION_ACTIVITY_TABLE_NAME, schema=ConfigName.NFR_PERSISTENCE_SCHEMA_NAME)

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new notification activity.
 */
@NoArgsConstructor

/**
 * Instantiates a new notification activity.
 *
 * @param streamType the stream type
 * @param collectionType the collection type
 * @param key the key
 * @param collectedTime the collected time
 * @param reportedTime the reported time
 * @param receipients the receipients
 * @param mimeType the mime type
 * @param deliveryCount the delivery count
 * @param acknoledgment the acknoledgment
 * @param content the content
 */
@AllArgsConstructor
public class NotificationActivity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The stream type. */
	String streamType;
	
	/** The collection type. */
	String collectionType;
	
	/** The key. */
	@EmbeddedId
	NotificationKey key;
	
	/** The collected time. */
	@Column
	String collectedTime;
	
	/** The reported time. */
	@Column
	String reportedTime;
	
	/** The receipients. */
	@Column
	List<String> receipients;
	
	/** The mime type. */
	@Column
	String mimeType;
	
	/** The delivery count. */
	@Column
	int deliveryCount;
	
	/** The acknoledgment. */
	@Column
	String acknoledgment;
	
	/** The content. */
	@Column
	String content;

}
