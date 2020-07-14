package io.kryptoblocks.msa.user.service.business.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.NoResultException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.BeansEndpoint;
import org.springframework.boot.actuate.endpoint.DumpEndpoint;
import org.springframework.boot.actuate.endpoint.EnvironmentEndpoint;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.actuate.endpoint.InfoEndpoint;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.actuate.endpoint.TraceEndpoint;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.jsonwebtoken.Claims;
import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.common.jwt.JWTTokenUtil;
import io.kryptoblocks.msa.common.jwt.UserMap;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.StringEncryptionUtil;
import io.kryptoblocks.msa.user.repository.impl.UserServiceRepository;
import io.kryptoblocks.msa.user.repository.key.UserTokenKey;
 
import io.kryptoblocks.msa.user.repository.model.PasswordResetToken;
import io.kryptoblocks.msa.user.repository.model.User;
import io.kryptoblocks.msa.user.repository.model.UserLogin;
 
import io.kryptoblocks.msa.user.repository.model.UserToken;

 
import io.kryptoblocks.msa.user.service.audit.event.UserActivityEventSender;
import io.kryptoblocks.msa.user.service.business.UserService;
import io.kryptoblocks.msa.user.service.message.UserBusinessMessage;
import io.kryptoblocks.msa.user.service.model.AuthType;
import io.kryptoblocks.msa.user.service.model.LoginInput;
import io.kryptoblocks.msa.user.service.model.SignUpInput;
import io.kryptoblocks.msa.user.service.model.TokenInput;
import io.kryptoblocks.msa.user.service.model.UserActivity;
import io.kryptoblocks.msa.user.service.model.UserInput;
import lombok.Getter;
import lombok.Setter;

/**
 
 *
 */
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Value("${service.login.retryThreshold:5}")
	private int loginRetryThreshold;

	@Autowired
	private UserServiceRepository userRepository;


	@Autowired
	StringEncryptionUtil stringEncryptionUtil;

	@Autowired
	UserActivityEventSender userActivityEventSender;

	@Autowired
	JWTTokenUtil jwtTokenUtil;
	
	@Autowired
	HealthEndpoint healthEndpoint;
	
	@Autowired
	BeansEndpoint beansEndpoint;
	
	@Autowired
	InfoEndpoint infoEndpoint;
	
	@Autowired
	EnvironmentEndpoint environmentEndpoint;
	
	@Autowired
	MetricsEndpoint metricsEndpoint;
	
	@Autowired
	TraceEndpoint traceEndpoint;
	
	@Autowired
	DumpEndpoint dumpEndpoint;
	

	@Setter
	@Getter
	private String systemUserVerificationMsg = null;
	
	private String methodName;

	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

	 
	 
	@Override	 
	public User registerNewUser(SignUpInput event) throws BusinessException {
		User returnValue = null;
		 
			try {
				methodName = new Object() {
				}.getClass().getEnclosingMethod().getName();
				returnValue = findUserByEmail(event.getEmail());
				if(returnValue == null) {
					
					AuthType authType = event.getProviderType();					 
					UserActivity userActivity = new UserActivity();
					userActivity.setType(UserActivity.Type.SIGNUP_ACTIVITY.getValue());
					
					switch (authType){
					
					case SYSTEM:
						returnValue = handleSystemSignup(event, userActivity);						
						break;
						
					case FACEBOOK:
						//TODO
						//no sign up here						
						break;
					
					default:	
						break;					
						
					}
					
					/*healthEndpoint.invoke().getDetails();
					metricsEndpoint.invoke();
					infoEndpoint.*/
					
										
				}else {
					throw new BusinessException(UserBusinessMessage.USER_ALREADY_EXIST);					
				}
			} catch (Exception e) {
				handleMethodException(e, methodName);
			}
		 
		return returnValue;
	}
	
	@Override	
	public User  confirmSignup(String signupToken) throws BusinessException{
		
		User returnValue = null;
		UserToken userToken = null;
		String email = null;
		
		try {
			String persistedToken  = null;
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			
			Claims receivedClaim = jwtTokenUtil.getClaimsFromToken(signupToken);			
			email = (String)receivedClaim.get(UserMap.EMAIL);
			String createdTime = (String)receivedClaim.get(UserMap.SIGN_UP_CONFIRM_CREATED_TIME);
			String verificationType =  UserInput.VerificationType.SIGN_UP_VERIFICATION.getValue();
			if((email != null) && (createdTime != null ) && (verificationType != null) ) {
				userToken = userRepository.findUserToken(email, verificationType, createdTime);
				persistedToken = userToken.getToken();
			}
			
			if(jwtTokenUtil.checkSignupConfirmation(signupToken, persistedToken)) {				
				userToken.setVerifiedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
				userRepository.saveUserToken(userToken);
				returnValue = userRepository.findUserByEmail(email);				
			}
			 
		} catch (Exception e) {
			handleMethodException(e, methodName);
		}
		return returnValue;
	}


	 
	@Override
	public User saveUser(User user) throws BusinessException {
		User returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
		returnValue = userRepository.saveUser(user);
		}catch(Exception e) {
			handleMethodException(e, methodName);	
		}
		return returnValue;
	}

	 
	@Override
	public User findUserByEmail(String email) throws BusinessException {

		User returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			
			returnValue = userRepository.findUserByEmail(email);
			if (returnValue == null) {
				throw new BusinessException(UserBusinessMessage.USER_NOT_FOUND);
			}

		} catch (Exception e) {
			if(e instanceof NoResultException) {
				LOGGER.debug("no result exist..");
			}else {
				handleMethodException(e, methodName);
			}
		}
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.kryptoblocks.uaa.service.IUAAService#saveUserLogin(io.kryptoblocks.uaa.
	 * persistence.model.UserLogin)
	 */
	@Override
	public void saveUserLogin(UserLogin userLogin) throws BusinessException {
		
		methodName = new Object() {
		}.getClass().getEnclosingMethod().getName();
		userRepository.saveUserLogin(userLogin);
	}

	/*
	 *
	 */
	@Override
	public User verifyUser(LoginInput signInEvent) throws BusinessException {

		User user = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			AuthType authType = signInEvent.getProviderType();
			UserActivity userActivity = new UserActivity();
			userActivity.setType(UserActivity.Type.LOGIN_ACTIVITY.getValue());

			switch (authType) {

			case SYSTEM:
				user = handleSystemLogin(signInEvent, userActivity);
				if (user == null) {
					throw new BusinessException(systemUserVerificationMsg);
				}
				break;
			case FACEBOOK:
				user = handleFBLogin(signInEvent, userActivity);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			handleMethodException(e, methodName);
		}
		return user;
	}

	@Override
	public UserToken createUserVerificationToken(TokenInput tokenInput) throws BusinessException {

		UserToken returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			returnValue = new UserToken();
			UserTokenKey key = new UserTokenKey();
			key.setEmail(tokenInput.getEmail());
			key.setType(tokenInput.getType());
			returnValue.setCreatedTime(tokenInput.getCreatedTime());
			returnValue.setKey(key);
			User user = new User();
			user.setEmail(tokenInput.getEmail());
			Map<String, Object> userMap = getUserMap(user);
			userMap.put(UserMap.TYPE, tokenInput.getType());			
			String sessionToken = jwtTokenUtil.generateToken(userMap, tokenInput.getDevice());
			returnValue.setToken(sessionToken);
			userRepository.saveUserToken(returnValue);

		} catch (Exception e) {
			handleMethodException(e, methodName);
		}
		return returnValue;
	}
	
	@Override
	public UserToken createUserToken(TokenInput tokenInput) throws BusinessException {
		
		UserToken returnValue = null;
		try {	
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			 UserToken userToken = new UserToken();
			 UserTokenKey key = new UserTokenKey();
			 key.setEmail(tokenInput.getEmail());
			 key.setType(tokenInput.getType());
			 userToken.setCreatedTime(tokenInput.getCreatedTime());
			 userToken.setToken(tokenInput.getToken());
			 userToken.setKey(key);
			 userRepository.saveUserToken(userToken);
			 
		} catch (Exception e) {
			handleMethodException(e, methodName);
		}
		return returnValue;
	}

	 
	@Override
	public User validateVerificationToken(String token) throws BusinessException {
		User returnValue = null;
		try {
			
			UserToken userToken = null;
			String persistedToken = null;
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			
			Claims receivedClaim = jwtTokenUtil.getClaimsFromToken(token);			
			 
			
			String email = (String)receivedClaim.get(UserMap.EMAIL);
			String createdTime = (String)receivedClaim.get(UserMap.CREATED_TIME);
			String type = (String)receivedClaim.get(UserMap.TYPE);
			if((email != null) && (createdTime != null ) && (type != null) ) {
				userToken = userRepository.findUserToken(email, type, createdTime);
				persistedToken = userToken.getToken();
			}
			 
			 
			if (userToken == null) {
				throw new BusinessException(UserBusinessMessage.USER_TOKEN_VALIDATION_NOT_FOUND);
			}
			
			 

			if (!persistedToken.equalsIgnoreCase(token)) {
				throw new BusinessException(UserBusinessMessage.USER_TOKEN_VALIDATION_INVALID);
			}
			String cretedTime = (String)jwtTokenUtil.getClaimsFromToken(token).get(UserMap.CREATED_TIME);
			String currentTime = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString();
			long timeDifference = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateDifferenceInMilliSeconds(cretedTime, currentTime);
			long ttl = new Long((String)receivedClaim.get(UserMap.VERIFICATION_TOKEN_TTL)).longValue();
			
			if(timeDifference > ttl) {
				throw new BusinessException(UserBusinessMessage.USER_TOKEN_VALIDATION_EXPIRED);
			}else {				
				returnValue = userRepository.findUserByEmail(email);
			}			
		}catch(Exception e) {
			handleMethodException(e, methodName);
		}
		return returnValue;		
	}
		

	 
	@Override
	public PasswordResetToken createPasswordResetToken(String email, Device device) throws BusinessException {
		
		PasswordResetToken prtn = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
		prtn = new PasswordResetToken();		 
		prtn.setEmail(email);		
		Map<String, Object> tokenMap = new HashMap<String, Object>();
		tokenMap.put(UserMap.EMAIL, email);
		tokenMap.put(UserMap.VERIFICATION_TOKEN_TTL, String.valueOf(TimeUnit.DAYS.toMillis(1)));			 
		tokenMap.put(UserMap.CREATED_TIME, DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());		
		String token = jwtTokenUtil.generateToken(tokenMap, device);
		prtn.setToken(token);
		userRepository.saveResetToken(prtn);
		}catch(Exception e) {
			handleMethodException(e, methodName);			
		}		
		return prtn;
	}

	 
	@Override
	public User validatePasswordResetToken(String token) throws BusinessException {
		
		User returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			String email = (String)jwtTokenUtil.getClaimsFromToken(token).get(UserMap.EMAIL);
			LOGGER.debug("email from password reset token: {}", email);
			PasswordResetToken pvt = userRepository.findResetToken(email);
			
			if (pvt == null) {
				throw new BusinessException(UserBusinessMessage.PWD_TOKEN_VALIDATION_NOT_FOUND);
			}
			
			String savedToken = pvt.getToken();

			if (!savedToken.equalsIgnoreCase(token)) {
				throw new BusinessException(UserBusinessMessage.PWD_TOKEN_VALIDATION_INVALID);
			}
			String cretedTime = (String)jwtTokenUtil.getClaimsFromToken(token).get(UserMap.CREATED_TIME);
			String currentTime = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString();
			long timeDifference = DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateDifferenceInMilliSeconds(cretedTime, currentTime);
			long ttl = new Long((String)jwtTokenUtil.getClaimsFromToken(token).get(UserMap.VERIFICATION_TOKEN_TTL)).longValue();
			
			if(timeDifference > ttl) {
				throw new BusinessException(UserBusinessMessage.USER_TOKEN_VALIDATION_EXPIRED);
			}else {				
				returnValue = userRepository.findUserByEmail(email);
			}			
		}catch(Exception e) {
			handleMethodException(e, methodName);
		}
		return returnValue;		
	}

	 
	 
	 

	 
	@Override
	public User changeUserPassword(String sessionToken, String oldPassword, String newPassword) throws BusinessException {
		
		User user = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			String email = (String)jwtTokenUtil.getClaimsFromToken(sessionToken).get(UserMap.EMAIL);
			user = userRepository.findUserByEmail(email);
			String encodedOldPassword = user.getPassword();
			String decodedOldPassword = stringEncryptionUtil.decrypt(encodedOldPassword);
			
			if(decodedOldPassword.equalsIgnoreCase(oldPassword)) {
				LOGGER.debug("password reset business method: old password match");	
				String encodedNewPassword = stringEncryptionUtil.encrypt(newPassword);
				user.setPassword(encodedNewPassword);
				userRepository.saveUser(user);
			}else {
				LOGGER.debug("password reset business method: old password match");
				throw new BusinessException(UserBusinessMessage.PWD_RESET_OLD_PWD_MISMATCH);				
			}
			
		}
		catch(Exception e) {
			handleMethodException(e, methodName);			
		}
		return user;
	}

	 
	private User createUserFromSocialLogin(LoginInput userLoginEvent) {
		User user = new User();
		user.setEmail(userLoginEvent.getEmail());
		user.setUserName(userLoginEvent.getSocialUserName());		 
		List<String> loginHistory = new ArrayList();
		loginHistory.add(userLoginEvent.getProviderType().getValue());		 
		user.setCreatedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		user.setUpdatedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		return user;

	}

	private UserLogin getUserLogin(LoginInput signInEvent) {
		UserLogin login = new UserLogin();
		login.setEmail(signInEvent.getEmail());
		login.setProviderType(signInEvent.getProviderType().getValue());
		login.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		return login;
	}
	
	 
	 
	
	private User handleSystemLogin(LoginInput loginInput , UserActivity userActivity) throws InvalidKeyException,
			NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
		User returnValue = null;
		 
		User user = userRepository.findUserByEmail(loginInput.getEmail());
		if (user != null) {
			LOGGER.debug("handleSystemLogin: valid user object returned from DB");
			String unEncryptedStoredPassword = stringEncryptionUtil.decrypt(user.getPassword());

			if (user.isLocked()) {
				setSystemUserVerificationMsg(UserBusinessMessage.USER_LOCKED);
			} else {
				if (unEncryptedStoredPassword.equalsIgnoreCase(loginInput.getPassword())) {
					String sessionToken = jwtTokenUtil.generateToken(getUserMap(user), loginInput.getDevice());
					 
					user.setSessionToken(sessionToken);
					returnValue = user;
				} else {
					setSystemUserVerificationMsg(UserBusinessMessage.USER_INVALID_PASSWORD);
				}
			}
		} else {
			setSystemUserVerificationMsg(UserBusinessMessage.USER_NOT_FOUND);
		}
		userActivity.setLoginInput(loginInput);
		userActivityEventSender.sendUserActivity(userActivity);
		return returnValue;
	}
	
	private User handleSystemSignup(SignUpInput event, UserActivity userActivity) throws InvalidKeyException,
			NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
		User returnValue = null;
		 
		returnValue = new User();
		returnValue.setEmail(event.getEmail());
		returnValue.setPassword(stringEncryptionUtil.encrypt(event.getPassword()));
		String sessionToken = jwtTokenUtil.generateToken(getUserMap(returnValue), event.getDevice());
		returnValue.setSessionToken(sessionToken);		
		userRepository.saveUser(returnValue);
		userActivity.setSignupInput(event);
		userActivityEventSender.sendUserActivity(userActivity);
		return returnValue;
	}

	private User handleFBLogin(LoginInput loginInput, UserActivity userActivity) {
		UserLogin userLogin = getUserLogin(loginInput);
		User user = userRepository.findUserByEmail(loginInput.getEmail());
		if (user == null) {
			// fist time login with social authentication, create the user
			user = createUserFromSocialLogin(loginInput);			
		}
		// create the session token
		String sessionToken = jwtTokenUtil.generateToken(getUserMap(user), loginInput.getDevice());
		userLogin.setSessionToken(sessionToken);
		userLogin.setSocialToken(loginInput.getSessionToken());
		
		//Trigger the user login event and save user to DB
		userActivity.setLoginInput(loginInput);
		userActivity.setSocialFirstTimeLogin(true);
		userActivity.setUserFromSocialLogin(user);
		userActivityEventSender.sendUserActivity(userActivity);
		user.setSessionToken(sessionToken);

		return user;
	}

	private Map<String, Object> getUserMap(User user) {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put(UserMap.USER_NAME, user.getUserName());
		returnValue.put(UserMap.EMAIL, user.getEmail());
		 
		 
		return returnValue;
	}
	
	 
	
	private void handleMethodException(Exception e, String methodName) throws BusinessException {
		String exceptionMsg = ExceptionUtils.getFullStackTrace(e);
		LOGGER.debug("exception in {} business method: exception details are: {}", methodName, exceptionMsg);
		throw new BusinessException(exceptionMsg);		
	}

}
