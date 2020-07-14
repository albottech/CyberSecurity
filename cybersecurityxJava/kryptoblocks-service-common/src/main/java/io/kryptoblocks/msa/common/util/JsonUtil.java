package io.kryptoblocks.msa.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.Getter;
import lombok.Setter;

 
// TODO: Auto-generated Javadoc
/**
 * The Class JsonUtil.
 */
public class JsonUtil {
	
	
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	
	/**
	 * Instantiates a new json util.
	 */
	public JsonUtil() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);   
        setObjectMapper(objectMapper);
		
	}
	
	/**
	 * Sets the object mapper.
	 *
	 * @param objectMapper the new object mapper
	 */
	@Setter
	private ObjectMapper objectMapper;
	
	
	/**
	 * Gets the object mapper with root.
	 *
	 * @return the object mapper with root
	 */
	public ObjectMapper getObjectMapperWithRoot() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE,true);      
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE,true);
        return objectMapper;        
	}
	
	/**
	 * Gets the object mapper.
	 *
	 * @return the object mapper
	 */
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);         
        return objectMapper;        
	}
	
	/**
	 * Gets the object as json.
	 *
	 * @param object the object
	 * @return the object as json
	 */
	public String getObjectAsJson(Object object) {
		try {
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			return objectMapper.writeValueAsString(object);
		}catch(Exception e) {
			LOGGER.error("error in json util get object as json method: {}", ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}
	
	/**
	 * Parses the json string without type.
	 *
	 * @param json the json
	 * @return the map
	 * @throws JsonProcessingException the json processing exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Map<String, String> parseJsonStringWithoutType(String json) throws JsonProcessingException, IOException  {
		
		
		   Map<String, String> returnValue = new HashMap<String, String>();
	       JsonFactory factory = new JsonFactory();

	       ObjectMapper mapper = new ObjectMapper(factory);
	       JsonNode rootNode = mapper.readTree(json);  

	       java.util.Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
	       while (fieldsIterator.hasNext()) {
	           Map.Entry<String,JsonNode> field = fieldsIterator.next();
	           returnValue.put(field.getKey(), field.getValue().asText());	           
	       }
	       return returnValue;
	}
	
	 
	

}
