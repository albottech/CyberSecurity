package io.kryptoblocks.msa.data.nfr.infra.engine.statistics.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;

import io.kryptoblocks.msa.data.nfr.infra.engine.statistics.key.SwamSpecKey;
import io.kryptoblocks.msa.data.nfr.infra.engine.statistics.udt.RaftInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class SwamSpec.
 */
public class SwamSpec {
	
	 
	 /** The key. */
 	@EmbeddedId
	 SwamSpecKey key;

	  /** The labels. */
  	@Column
	  Map<String, String> labels;	   
	  
	  /** The orchestration config task history retention limit. */
  	Integer orchestrationConfigTaskHistoryRetentionLimit;

	  /** The raft. */
  	@Embedded
	  RaftInfo raft;

	  /** The dispatcher config heartbeat period. */
  	@Column
	  String dispatcherConfigHeartbeatPeriod;

	  /** The ca config. */
  	@Column
	  String caConfig;

	  /** The encryption config. */
  	@Column
	  String encryptionConfig;

	  /** The task defaults. */
  	@Column
	  String taskDefaults;

}
