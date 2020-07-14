/**
 * 
 */
package io.kryptoblocks.msa.user.service.model;
import java.io.Serializable;

import org.springframework.mobile.device.Device;

 

import lombok.Data;

/**
 * @author insignia
 *
 */
@Data
public class SignUpInput implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String name;
	String email;
	String password;
	String rePassword;
	AuthType providerType;
	Device device;
	 
}
