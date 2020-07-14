package io.kryptoblocks.msa.common.factory;

 
import java.util.Collection;
import java.util.List;

import io.kryptoblocks.msa.common.exception.ConfigException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating MultiBean objects.
 *
 * @param <T> the generic type
 */
public interface MultiBeanFactory<T> {   
	  
  	/**
  	 * Gets the object.
  	 *
  	 * @param name the name
  	 * @return the object
  	 * @throws Exception the exception
  	 */
  	T getObject(String name) throws Exception;
	  
  	/**
  	 * Gets the object type.
  	 *
  	 * @return the object type
  	 */
  	Class<?> getObjectType();
	  
  	/**
  	 * Gets the names.
  	 *
  	 * @return the names
  	 * @throws ConfigException the config exception
  	 */
  	Collection<String> getNames()  throws ConfigException ;
}

