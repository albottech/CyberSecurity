package io.kryptoblocks.msa.user.service.model;

import java.io.Serializable;

import io.kryptoblocks.msa.user.repository.model.User;
import io.kryptoblocks.msa.user.repository.model.UserLogin;
 
import lombok.Data;

@Data
public class UserActivity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String type;
	boolean socialFirstTimeLogin = false;
	User userFromSocialLogin = null;
	LoginInput loginInput;
	SignUpInput signupInput;
	 
	
	public static enum Type {
		
		LOGIN_ACTIVITY("LOGIN_ACTIVITY"),
		SIGNUP_ACTIVITY("SIGNUP_ACTIVITY");
		
		
		
		public static Type findByValue(String value) {
			if (value != null) {
				for (Type type : Type.values()) {
					if (value.equalsIgnoreCase(type.getValue())) {
						return type;
					}
				}
			}

			return null;
		}

		 
		private Type(String value) {
			this.value = value;
		}

		 
		private final String value;

		 
		public String getValue() {
			return value;
		}

	}

}
