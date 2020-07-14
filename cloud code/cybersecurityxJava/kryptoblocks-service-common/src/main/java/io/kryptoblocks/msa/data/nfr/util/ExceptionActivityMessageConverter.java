package io.kryptoblocks.msa.data.nfr.util;

import io.kryptoblocks.msa.common.model.ExceptionActivityEntity;
import io.kryptoblocks.msa.data.nfr.exception.key.ExceptionActivityKey;
import io.kryptoblocks.msa.data.nfr.exception.model.ExceptionActivity;

// TODO: Auto-generated Javadoc
/**
 * The Class ExceptionActivityMessageConverter.
 */
public class ExceptionActivityMessageConverter {
	
	/** The Constant DESTINATION. */
	private static final String DESTINATION = "DB";
	
	/**
	 * Generated key and data.
	 *
	 * @param activity the activity
	 * @return the object[]
	 */
	public Object[] generatedKeyAndData(ExceptionActivityEntity activity) {
		
		Object[] returnValue = new Object[2];
		ExceptionActivityKey key = new ExceptionActivityKey();
		key.setId(activity.getId());
		key.setServiceName(activity.getServiceName());		
		ExceptionActivity exceptionActivity = new ExceptionActivity();
		exceptionActivity.setDestination(DESTINATION);
		exceptionActivity.setAlertNotificationMsg(activity.getAlertNotificationMsg());
		//exceptionActivity.setAlertStatus(false);		
		//exceptionActivity.setDestination();
		exceptionActivity.setDetails(activity.getDetails());
		exceptionActivity.setKey(key);
		exceptionActivity.setLocation(activity.getLocation());
		exceptionActivity.setProssedTimeTime(activity.getProssedTimeTime());
		exceptionActivity.setReportedTime(activity.getProssedTimeTime());
		exceptionActivity.setTime(activity.getTime());		
		returnValue[0] = key;
		returnValue[1] = exceptionActivity;
		return returnValue;
	}
	
}
