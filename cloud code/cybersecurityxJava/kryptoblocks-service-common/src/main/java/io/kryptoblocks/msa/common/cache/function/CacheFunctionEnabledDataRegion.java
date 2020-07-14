package io.kryptoblocks.msa.common.cache.function;

// TODO: Auto-generated Javadoc
/**
 * The Enum CacheFunctionEnabledDataRegion.
 */
public enum CacheFunctionEnabledDataRegion {

	/** The replicated region. */
	REPLICATED_REGION("REPLICATED_REGION"),
	
	/** The partitioned region. */
	PARTITIONED_REGION("PARTITIONED_REGION"),
	
	/** The replicatedregion. */
	REPLICATEDREGION("REPLICATEDREGION"),
	
	/** The partitionedregion. */
	PARTITIONEDREGION("PARTITIONEDREGION"),
	
	/** The ridmgmtbidreplicatedregion. */
	RIDMGMTBIDREPLICATEDREGION("RIDMGMTBIDREPLICATEDREGION"),
	
	/** The ridmgmtbidpartitionedregion. */
	RIDMGMTBIDPARTITIONEDREGION("RIDMGMTBIDPARTITIONEDREGION"),
	
	/** The ridemgmtexecutionreplicatedregion. */
	RIDEMGMTEXECUTIONREPLICATEDREGION("RIDEMGMTEXECUTIONREPLICATEDREGION"),
	
	/** The ridemgmtexecutionpartitionedregion. */
	RIDEMGMTEXECUTIONPARTITIONEDREGION("RIDEMGMTEXECUTIONPARTITIONEDREGION");
	
	/** The Constant SECURITY_PARTITIONED_REGION_NAME. */
	private static final String SECURITY_PARTITIONED_REGION_NAME = "securityMasterPartitionedRegion";
	
	/** The Constant SECURITY_REPLICATED_REGION_NAME. */
	private static final String SECURITY_REPLICATED_REGION_NAME = "securityMasterReplicatedRegion";

	/** The Constant POSITION_REPLICATED_REGION_NAME. */
	private static final String POSITION_REPLICATED_REGION_NAME = "positionReplicatedRegion";
	
	/** The Constant POSITION_PARTITIONED_REGION_NAME. */
	private static final String POSITION_PARTITIONED_REGION_NAME = "positionPartitionedRegion";

	/** The Constant CLIENT_REQUEST_PARTITIONED_REGION_NAME. */
	private static final String CLIENT_REQUEST_PARTITIONED_REGION_NAME = "clientRequestPartitionedRegion";
	
	/** The Constant CLIENT_REQUEST_REPLICATED_REGION_NAME. */
	private static final String CLIENT_REQUEST_REPLICATED_REGION_NAME = "clientRequestReplicatedRegion";

	/** The Constant DATA_REQUEST_ACKNOWLEDGMENT_PARTITIONED_REGION_NAME. */
	private static final String DATA_REQUEST_ACKNOWLEDGMENT_PARTITIONED_REGION_NAME = "dataRequestAcknowledgmentPartitionedRegion";
	
	/** The Constant DATA_REQUEST_ACKNOWLEDGMENT_REPLICATED_REGION_NAME. */
	private static final String DATA_REQUEST_ACKNOWLEDGMENT_REPLICATED_REGION_NAME = "dataRequestAcknowledgmentReplicatedRegion";

	/** The Constant DATA_REQUEST_RESPONSE_PARTITIONED_REGION_NAME. */
	private static final String DATA_REQUEST_RESPONSE_PARTITIONED_REGION_NAME = "dataRequestResponsePartitionedRegion";
	
	/** The Constant DATA_REQUEST_RESPONSE_REPLICATED_REGION_NAME. */
	private static final String DATA_REQUEST_RESPONSE_REPLICATED_REGION_NAME = "dataRequestResponseReplicatedRegion";

	/** The Constant TASK_EVENT_PARTITIONED_REGION_NAME. */
	private static final String TASK_EVENT_PARTITIONED_REGION_NAME = "taskEventPartitionedRegion";
	
	/** The Constant TASK_EVENT_REPLICATED_REGION_NAME. */
	private static final String TASK_EVENT_REPLICATED_REGION_NAME = "taskEventReplicatedRegion";

	/** The Constant DATA_REQUEST_PAYLOAD_PARTITIONED_REGION_NAME. */
	private static final String DATA_REQUEST_PAYLOAD_PARTITIONED_REGION_NAME = "dataRequestPartitionedRegion";
	
	/** The Constant DATA_REQUEST_PAYLOAD_REPLICATED_REGION_NAME. */
	private static final String DATA_REQUEST_PAYLOAD_REPLICATED_REGION_NAME = "dataRequestReplicatedRegion";

	/** The Constant DATA_MAPPING_PARTITIONED_REGION_NAME. */
	private static final String DATA_MAPPING_PARTITIONED_REGION_NAME = "dataMappingPartitionedRegion";
	
	/** The Constant DATA_MAPPING_REPLICATED_REGION_NAME. */
	private static final String DATA_MAPPING_REPLICATED_REGION_NAME = "dataMappingReplicatedRegion";

	/** The Constant DATA_TRANSLATION_PARTITIONED_REGION_NAME. */
	private static final String DATA_TRANSLATION_PARTITIONED_REGION_NAME = "dataTranslationPartitionedRegion";
	
	/** The Constant DATA_TRANSLATION_REPLICATED_REGION_NAME. */
	private static final String DATA_TRANSLATION_REPLICATED_REGION_NAME = "dataTranslationDataReplicatedRegion";

	/** The Constant TASK_SERVICE_NON_PERSISTENT_REGION_NAME. */
	private static final String TASK_SERVICE_NON_PERSISTENT_REGION_NAME = "taskServiceNonPersistentRegion";

	/**
	 * Instantiates a new cache function enabled data region.
	 *
	 * @param value the value
	 */
	private CacheFunctionEnabledDataRegion(String value) {
		this.value = value;
	}

	/** The value. */
	private final String value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Gets the original region name.
	 *
	 * @return the original region name
	 */
	public String getOriginalRegionName() {

		String returnValue = null;

		switch (value) {

		case "SECURITY_REPLICATED_REGION":
			returnValue = SECURITY_REPLICATED_REGION_NAME;
			break;

		case "SECURITY_PARTITIONED_REGION":
			returnValue = SECURITY_PARTITIONED_REGION_NAME;
			break;

		case "POSITION_REPLICATED_REGION":
			returnValue = POSITION_REPLICATED_REGION_NAME;
			break;

		case "POSITION_PARTITIONED_REGION":
			returnValue = POSITION_PARTITIONED_REGION_NAME;
			break;

		case "CLIENT_REQUEST_PARTITIONED_REGION":
			returnValue = CLIENT_REQUEST_PARTITIONED_REGION_NAME;
			break;

		case "CLIENT_REQUEST_REPLICATED_REGION":
			returnValue = CLIENT_REQUEST_REPLICATED_REGION_NAME;
			break;

		case "DATA_REQUEST_ACKNOWLEDGMENT_PARTITIONED_REGION":
			returnValue = DATA_REQUEST_ACKNOWLEDGMENT_PARTITIONED_REGION_NAME;
			break;

		case "DATA_REQUEST_ACKNOWLEDGMENT_REPLICATED_REGION":
			returnValue = DATA_REQUEST_ACKNOWLEDGMENT_REPLICATED_REGION_NAME;
			break;

		case "DATA_REQUEST_RESPONSE_PARTITIONED_REGION":
			returnValue = DATA_REQUEST_RESPONSE_PARTITIONED_REGION_NAME;
			break;

		case "DATA_REQUEST_RESPONSE_REPLICATED_REGION":
			returnValue = DATA_REQUEST_RESPONSE_REPLICATED_REGION_NAME;
			break;

		case "DATA_REQUEST_PAYLOAD_PARTITIONED_REGION":
			returnValue = DATA_REQUEST_PAYLOAD_PARTITIONED_REGION_NAME;
			break;

		case "DATA_REQUEST_PAYLOAD_REPLICATED_REGION":
			returnValue = DATA_REQUEST_PAYLOAD_REPLICATED_REGION_NAME;
			break;

		case "DATA_MAPPING_PARTITIONED_REGION":
			returnValue = DATA_MAPPING_PARTITIONED_REGION_NAME;
			break;

		case "DATA_MAPPING_REPLICATED_REGION":
			returnValue = DATA_MAPPING_REPLICATED_REGION_NAME;
			break;

		case "DATA_TRANSLATION_REPLICATED_REGION":
			returnValue = DATA_TRANSLATION_REPLICATED_REGION_NAME;
			break;

		case "DATA_TRANSLATION_PARTITIONED_REGION":
			returnValue = DATA_TRANSLATION_PARTITIONED_REGION_NAME;
			break;

		case "TASK_EVENT_PARTITIONED_REGION":
			returnValue = TASK_EVENT_PARTITIONED_REGION_NAME;
			break;

		case "TASK_EVENT_REPLICATED_REGION":
			returnValue = TASK_EVENT_REPLICATED_REGION_NAME;
			break;

		case "TASK_SERVICE_NON_PERSISTENT_REGION":
			returnValue = TASK_SERVICE_NON_PERSISTENT_REGION_NAME;
			break;

		}

		return returnValue;

	}

}
