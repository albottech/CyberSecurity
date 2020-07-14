
package io.kryptoblocks.msa.user.service.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.mobile.device.Device;

import lombok.Data;

/**
 * The login user class. This class is used to represent a user name password based login object
 * <p>
 *  
 * @author      Anoop Manghat
 * @version     %I%, %G%
 * @since       1.0
 */
@Data
public class LoginInput implements Serializable {

	private static final long serialVersionUID = 1L;

	String email;

	String password;
	 
	AuthType providerType;
	
	String socialToken;
	
	String sessionToken;
	
	String socialUserName;
	
	Device device;

}
