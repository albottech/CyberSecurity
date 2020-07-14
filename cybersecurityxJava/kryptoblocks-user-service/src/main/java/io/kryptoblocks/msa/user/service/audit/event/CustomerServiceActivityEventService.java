package io.kryptoblocks.msa.user.service.audit.event;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.kryptoblocks.msa.common.names.NameCollection;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.JsonUtil;
import io.kryptoblocks.msa.common.util.LoggerUtil;
 
import io.kryptoblocks.msa.user.repository.impl.UserServiceRepository;
import io.kryptoblocks.msa.user.repository.model.UserLogin;

import io.kryptoblocks.msa.user.service.model.CustomerActivity;
import io.kryptoblocks.msa.user.service.model.UserActivity;
 
public class CustomerServiceActivityEventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceActivityEventService.class);
	
	private static final String USER_EVENT_LOG_NAME = "CUSTOMER_EVENT_LOG";
	
	@Value("${spring.application.name}")
    private String serviceName;
	
	@Value("${spring.cloud.client.hostname}")
	private String hostName;
	
	@Value("${spring.cloud.client.hostname}")
	private String portNumber;
	
	@Autowired
	JsonUtil jsonUtil;
		
	@Autowired
	UserServiceRepository userServiceRepository;
	
	@PostConstruct
	public void onStartUp() {

	}

	public void processCustomerActivity(CustomerActivity customerActivity) {
		
		Object activity = null;
		try {
			String activityType = customerActivity.getActivity();
			LOGGER.debug("current customer activity type: {}", activityType );
			CustomerActivity.Type customerActivityType = CustomerActivity.Type.findByValue(activityType);
			switch(customerActivityType) {
			case CREDIT_CARD_ADD_ACTIVITY:
				break;
			case CREDIT_CARD_UPDATE_ACTIVITY:
				break;
			case CREDIT_CARD_REMOVE_ACTIVITY:
				break;
			case DRIVER_LICENSE_ADD_ACTIVITY:
				break;
			case DRIVER_LICENSE_UPDATE_ACTIVITY:
				break;
			case DRIVER_LICENSE_REMOVE_ACTIVITY:
				break;
			case VEHICLE_ADD_ACTIVITY:
				break;
			case VEHICLE_UPDATE_ACTIVITY:
				break;
			case VEHICLE_REMOVE_ACTIVITY:
				break;
			case BANK_ACCOUNT_ADD_ACTIVITY:
				break;
			case BANK_ACCOUNT_UPDATE_ACTIVITY:
				break;
			case BANK_ACCOUNT_REMOVE_ACTIVITY:
				break;
			default:
				break;				
			}
			
		}catch (Exception e) {
			LOGGER.error("exception in user event persistence log the activity to event log file : {}", ExceptionUtils.getFullStackTrace(e));
			getEventLogger().info(jsonUtil.getObjectAsJson(activity));			
		}
	}
	
	private Logger getEventLogger() {
		
		StringBuilder logFileBuilder = new StringBuilder();
		logFileBuilder.append(serviceName);
		logFileBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		logFileBuilder.append(hostName);
		logFileBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		logFileBuilder.append(portNumber);
		logFileBuilder.append(NameCollection.UNDERSCORE_CHARACTER);
		logFileBuilder.append(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString(new Date()));
		return LoggerUtil.createLoggerFor(USER_EVENT_LOG_NAME, logFileBuilder.toString());
		
	}
}
