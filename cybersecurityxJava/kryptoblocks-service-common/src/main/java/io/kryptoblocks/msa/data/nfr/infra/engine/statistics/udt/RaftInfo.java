package io.kryptoblocks.msa.data.nfr.infra.engine.statistics.udt;

import javax.persistence.Column;

// TODO: Auto-generated Javadoc
/**
 * The Class RaftInfo.
 */
public class RaftInfo {
	
	/** The snapshot interval. */
	@Column
	Integer snapshotInterval;

	/** The keep old snapshots. */
	@Column
	Integer keepOldSnapshots;

	/** The log entries for slow followers. */
	@Column
	Integer logEntriesForSlowFollowers;

	/** The election tick. */
	@Column
	Integer electionTick;

	/** The heartbeat tick. */
	@Column
	Integer heartbeatTick;

}
