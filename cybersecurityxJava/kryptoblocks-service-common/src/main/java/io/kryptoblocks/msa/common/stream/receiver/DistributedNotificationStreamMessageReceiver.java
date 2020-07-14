package io.kryptoblocks.msa.common.stream.receiver;

import java.util.Map;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.google.gson.JsonObject;

import io.kryptoblocks.msa.common.names.NameCollection;
import io.kryptoblocks.msa.common.stream.sink.DistributedNotificationStreamMessageSink;
import io.kryptoblocks.msa.common.util.CityLoader; 


// TODO: Auto-generated Javadoc
/**
 * The Class DistributedNotificationStreamMessageReceiver.
 */
public class DistributedNotificationStreamMessageReceiver {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DistributedNotificationStreamMessageReceiver.class);
	
	/** The city loader. */
	@Autowired
	CityLoader cityLoader;
	
	/** The application context. */
	@Autowired
    ApplicationContext applicationContext;

	
	/**
	 * Process message.
	 *
	 * @param message the message
	 */
	@ServiceActivator(inputChannel = DistributedNotificationStreamMessageSink.DISTRIBUTED_NOTIFICATION_STREAM_MESSAGE_INPUT)	
    public void processMessage(Message<?> message) {
		/*try {
		Coordinator pnContainer = null;		
		String payloadAsString = (String)message.getPayload();	
		LOGGER.debug("distributed notification stream message sink: {}", payloadAsString);
		ObjectMapper objectMapper = new ObjectMapper();		
		Notification notificationMessage = objectMapper.readValue(payloadAsString, Notification.class);
		String cityName = notificationMessage.getCity();
		String destinaton = notificationMessage.getDestination();
		Map<String, String> content = notificationMessage.getContent();
		JsonObject jsonObject = new JsonObject();
		for(Map.Entry<String, String> entry : content.entrySet()) {
			jsonObject.addProperty(entry.getKey(), entry.getValue());			
		}
		RecipientType notificationDestinationType = RecipientType.findByValue(destinaton);
		switch(notificationDestinationType) {
		case CONSUMER:
			pnContainer = getCityConsumerCordernator(cityName);
			pnContainer.publishConsumerMessage(jsonObject);
			break;
		case PROVIDER:
			pnContainer = getCityProviderCordernator(cityName);
			pnContainer.publishProviderMessage(jsonObject);
			break;
		default:
			break;			
		}		
		
		}catch(Exception e) {
			LOGGER.debug("exception in distributed message processing: {}", ExceptionUtils.getFullStackTrace(e));
		}		*/
    }
	
	
	/*private Coordinator getCityProviderCordernator(String cityName) {
		String pncContainerBeanName = cityLoader.getCityCordinatorName(cityName,
			NameCollection.PUB_NUB_CONTAINER_ROLE,
			NameCollection.PROVIDER_NOTIFICATION_CORDINATOR_NAME);
		Coordinator pnContainer = (Coordinator) applicationContext.getBean(pncContainerBeanName);
		return pnContainer;		
	}
	
	private Coordinator getCityConsumerCordernator(String cityName) {
		String pncContainerBeanName = cityLoader.getCityCordinatorName(cityName,
			NameCollection.PUB_NUB_CONTAINER_ROLE,
			NameCollection.CONSUMER_NOTIFICATION_CORDINATOR_NAME);
		Coordinator pnContainer = (Coordinator) applicationContext.getBean(pncContainerBeanName);
		return pnContainer;		
	}*/


}
