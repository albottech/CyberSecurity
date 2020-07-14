/**
 * The sign up API controller class
 * <p>
 *  
 * @author      Lxur LLC
 * @author      Associate-1
 * @version     %I%, %G%
 * @since       1.0
 */
package io.kryptoblocks.msa.security.service.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.dropwizard.DropwizardMetricServices;
import org.springframework.cloud.sleuth.SpanAccessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.kerberos.authentication.KerberosServiceRequestToken;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.common.model.AuthenticationResult;
import io.kryptoblocks.msa.common.model.AuthorizationVerificationResult;
import io.kryptoblocks.msa.common.model.ExceptionHandlerType;
import io.kryptoblocks.msa.common.util.ExceptionUtil;

import io.kryptoblocks.msa.security.service.business.AuthenticationService;
import io.kryptoblocks.msa.security.service.business.UserDetailsService;
import io.kryptoblocks.msa.security.service.message.SecurityMessage;
import io.kryptoblocks.msa.security.service.model.LoginUser;
import io.kryptoblocks.msa.security.service.util.JwtTokenUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityController.
 */
@RestController
public class SecurityController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);
	

	/** The service auth jwt token. */
	@Value("${service.auth.jwt.token}")
	private String serviceAuthJwtToken;

	/** The service auth kerberos ticket header. */
	@Value("${service.auth.kerberos.ticker.header}")
	private String serviceAuthKerberosTicketHeader;

	/** The auth manager. */
	@Autowired
	AuthenticationManager authManager;

	/** The ad authentication provider. */
	@Autowired
	ActiveDirectoryLdapAuthenticationProvider adAuthenticationProvider;

	 
	/** The jwt token util. */
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	/** The user details service. */
	@Resource(name = "userDetailsService")
	UserDetailsService userDetailsService;
	
	/** The dropwizard metric services. */
	@Autowired
	DropwizardMetricServices dropwizardMetricServices;

	/** The authentication service. */
	@Autowired
	AuthenticationService authenticationService;

	 

	/**
	 * Gets the service authentication.
	 *
	 * @param request the request
	 * @param response the response
	 * @param device the device
	 * @return the service authentication
	 * @throws ApiException the api exception
	 */
	@RequestMapping(value = "${service.route.authentication.path}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<AuthenticationResult> getServiceAuthentication(HttpServletRequest request, HttpServletResponse response,
			Device device) throws ApiException {

		dropwizardMetricServices.increment("security service authentication api invoked");
		String operationName = request.getMethod();
		ResponseEntity<AuthenticationResult> returnValue = null;
		boolean authenticationMethodFinalized = false;
		LoginUser loginUser = null;

		/*
		 * 1st, try whether the request header has a valid and previously assigned
		 * service authentication token
		 * 
		 * if valid service authentication token is not present, try whether request has
		 * a KERBEROS ticket and process it to establish a service authentication token
		 * 
		 * if KERBEROS ticket NOT present check the request has a username password JSON
		 * object and process it to establish a service authentication token
		 * 
		 * finally if all the above scenario failed or an exception thrown during any of
		 * the above step return an exception reponse
		 */

		// check on valid service authorization token
		if (request.getHeader(serviceAuthJwtToken) != null) {
			authenticationMethodFinalized = true;
			AuthenticationResult jwtTokenAuthenticationResult = jwtTokenAuthentication(request, operationName);
			
			switch(jwtTokenAuthenticationResult.getMsg()) {
			case SecurityMessage.SUCCESSFUL_AUTHENTICATION_RESPONSE_MESSAGE:
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add(serviceAuthJwtToken, jwtTokenAuthenticationResult.getToken());
				returnValue = new ResponseEntity<>(jwtTokenAuthenticationResult, responseHeaders, HttpStatus.OK);
				break;
			case SecurityMessage.UNABLE_TO_AUTHENTICATE_USERNAME_PASSWORD_AUTHENTICATION_RESPONSE_MESSAGE:
				returnValue = new ResponseEntity<>(jwtTokenAuthenticationResult, HttpStatus.UNPROCESSABLE_ENTITY);
				break;
			default:
				returnValue = new ResponseEntity<>(jwtTokenAuthenticationResult, HttpStatus.INTERNAL_SERVER_ERROR);
				break;
			}	
		}

		// check on kerberos ticket and authenticate the user using ticket
		if ((!authenticationMethodFinalized) && (request.getHeader(serviceAuthKerberosTicketHeader) != null)) {

			authenticationMethodFinalized = true;
			AuthenticationResult krbAuthenticationResult = kerberosTickerAuthentication(request, operationName, device);
			switch(krbAuthenticationResult.getMsg()) {
			case SecurityMessage.SUCCESSFUL_AUTHENTICATION_RESPONSE_MESSAGE:
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add(serviceAuthJwtToken, krbAuthenticationResult.getToken());
				returnValue = new ResponseEntity<>(krbAuthenticationResult, responseHeaders, HttpStatus.OK);
				break;
			case SecurityMessage.UNABLE_TO_AUTHENTICATE_KRB_TICKET_AUTHENTICATION_RESPONSE_MESSAGE:
				returnValue = new ResponseEntity<>(krbAuthenticationResult, HttpStatus.UNPROCESSABLE_ENTITY);
				break;
			default:
				returnValue = new ResponseEntity<>(krbAuthenticationResult, HttpStatus.INTERNAL_SERVER_ERROR);
				break;
			}			
		}

		// fire negotiate response		 
		if ((!authenticationMethodFinalized)) {
			authenticationMethodFinalized = true;
			LOGGER.debug("All previous authentication options are not successful, returning negotiate header");
			// fire the 401 response with negotiate token
			response.addHeader("WWW-Authenticate", "Negotiate");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			try {
				response.flushBuffer();
			} catch (IOException ioException) {
				LOGGER.error("exception in negotiate token request");
				LOGGER.error(ExceptionUtils.getStackTrace(ioException));
				throw ExceptionUtil.generateApiException("No valid authentication input", 
						ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(),operationName);
			}
		}
		
		if(returnValue != null) {
		LOGGER.debug("returning this response value for get operation : status: {}, body: {}", returnValue.getBody(), returnValue.getStatusCodeValue());
		}else {
			LOGGER.debug("return value is NULL");
		}

		return returnValue;
	}

	/**
	 * Service authentication.
	 *
	 * @param request the request
	 * @param response the response
	 * @param device the device
	 * @return the response entity
	 * @throws ApiException the api exception
	 */
	@RequestMapping(value = "${service.route.authentication.path}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<AuthenticationResult> serviceAuthentication(HttpServletRequest request, HttpServletResponse response,
			Device device) throws ApiException {

		dropwizardMetricServices.increment("security service authentication api invoked");
		String operationName = request.getMethod();
		ResponseEntity<AuthenticationResult> returnValue = null;
		boolean authenticationMethodFinalized = false;
		LoginUser loginUser = null;

		/*
		 * 1st, try whether the request header has a valid and previously assigned
		 * service authentication token
		 * 
		 * if valid service authentication token is not present, try whether request has
		 * a KERBEROS ticket and process it to establish a service authentication token
		 * 
		 * if KERBEROS ticket NOT present check the request has a username password JSON
		 * object and process it to establish a service authentication token
		 * 
		 * finally if all the above scenario failed or an exception thrown during any of
		 * the above step return an exception reponse
		 */

		// check on valid service authorization token
		if (request.getHeader(serviceAuthJwtToken) != null) {
			authenticationMethodFinalized = true;
			AuthenticationResult jwtTokenAuthenticationResult = jwtTokenAuthentication(request, operationName);
			
			switch(jwtTokenAuthenticationResult.getMsg()) {
			case SecurityMessage.SUCCESSFUL_AUTHENTICATION_RESPONSE_MESSAGE:
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add(serviceAuthJwtToken, jwtTokenAuthenticationResult.getToken());
				returnValue = new ResponseEntity<>(jwtTokenAuthenticationResult, responseHeaders, HttpStatus.OK);
				break;
			case SecurityMessage.UNABLE_TO_AUTHENTICATE_USERNAME_PASSWORD_AUTHENTICATION_RESPONSE_MESSAGE:
				returnValue = new ResponseEntity<>(jwtTokenAuthenticationResult, HttpStatus.UNPROCESSABLE_ENTITY);
				break;
			default:
				returnValue = new ResponseEntity<>(jwtTokenAuthenticationResult, HttpStatus.INTERNAL_SERVER_ERROR);
				break;
			}	
		}


		// check on kerberos ticket and authenticate the user using ticket
		if ((!authenticationMethodFinalized) && (request.getHeader(serviceAuthKerberosTicketHeader) != null)) {

			authenticationMethodFinalized = true;
			AuthenticationResult krbAuthenticationResult = kerberosTickerAuthentication(request, operationName, device);
			switch(krbAuthenticationResult.getMsg()) {
			case SecurityMessage.SUCCESSFUL_AUTHENTICATION_RESPONSE_MESSAGE:
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add(serviceAuthJwtToken, krbAuthenticationResult.getToken());
				returnValue = new ResponseEntity<>(krbAuthenticationResult, responseHeaders, HttpStatus.OK);
				break;
			case SecurityMessage.UNABLE_TO_AUTHENTICATE_KRB_TICKET_AUTHENTICATION_RESPONSE_MESSAGE:
				returnValue = new ResponseEntity<>(krbAuthenticationResult, HttpStatus.UNPROCESSABLE_ENTITY);
				break;
			default:
				returnValue = new ResponseEntity<>(krbAuthenticationResult, HttpStatus.INTERNAL_SERVER_ERROR);
				break;
			}			
		}

		// check on user data and authenticate the user using user data
		loginUser = extractLoginUser(request, operationName);
		if ((!authenticationMethodFinalized) && (loginUser != null)) {
			authenticationMethodFinalized = true;
			AuthenticationResult userNamePasswrdAuthenticationResult = userNameAuthentication(request, operationName, device, loginUser);
			
			switch(userNamePasswrdAuthenticationResult.getMsg()) {
			case SecurityMessage.SUCCESSFUL_AUTHENTICATION_RESPONSE_MESSAGE:
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add(serviceAuthJwtToken, userNamePasswrdAuthenticationResult.getToken());
				returnValue = new ResponseEntity<>(userNamePasswrdAuthenticationResult, responseHeaders, HttpStatus.OK);
				break;
			case SecurityMessage.UNABLE_TO_AUTHENTICATE_USERNAME_PASSWORD_AUTHENTICATION_RESPONSE_MESSAGE:
				returnValue = new ResponseEntity<>(userNamePasswrdAuthenticationResult, HttpStatus.UNPROCESSABLE_ENTITY);
				break;
			default:
				returnValue = new ResponseEntity<>(userNamePasswrdAuthenticationResult, HttpStatus.INTERNAL_SERVER_ERROR);
				break;
			}			
			 
		} else {
			// start negotiations
			if (loginUser == null) {
				LOGGER.debug("Login user authentication is failed returning negotiate header");
				// fire the 401 response with negotiate token
				response.addHeader("WWW-Authenticate", "Negotiate");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				try {
					response.flushBuffer();
				} catch (IOException ioException) {					
					LOGGER.error(ExceptionUtils.getStackTrace(ioException));
				}
			}
		}

		// finally throw exception if no supported authentication method input is not
		// provided
		if ((!authenticationMethodFinalized)) {
			throw ExceptionUtil.generateApiException("No valid authentication input", 
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(),operationName);

		}
		
		LOGGER.debug("returning this response value for get operation : status: {}, body: {}", returnValue.getBody(), returnValue.getStatusCodeValue());
		
		return returnValue;
	}

	/**
	 * Service token verification.
	 *
	 * @param request the request
	 * @return the response entity
	 * @throws ApiException the api exception
	 */
	@RequestMapping(value = "${service.route.token.verification.path}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> serviceTokenVerification(HttpServletRequest request) throws ApiException {

		ResponseEntity<String> returnValue = null;

		try {

		} catch (Exception e) {

			LOGGER.debug("Exception in security verification");
			e.printStackTrace();
			returnValue = new ResponseEntity<>("Exception in security service token verification",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return returnValue;
	}

	/**
	 * Service authorization verification.
	 *
	 * @param request the request
	 * @return the response entity
	 * @throws ApiException the api exception
	 */
	@RequestMapping(value = "${service.route.authorization.verification.path}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<AuthorizationVerificationResult> serviceAuthorizationVerification(HttpServletRequest request) throws ApiException{

		LOGGER.debug("service authorization verification called:");
		ResponseEntity<AuthorizationVerificationResult> returnValue = null;
		String operationName = request.getMethod();

		try {
			// check on valid service authorization token
			if (request.getHeader(serviceAuthJwtToken) != null) {	
				//call the real method
				AuthorizationVerificationResult authResult = getAuthorizationAsList(request.getHeader(serviceAuthJwtToken), operationName);
				if(authResult.getAuthorizationList() != null) {
					returnValue = new ResponseEntity<>(authResult, HttpStatus.OK);
				}else {
					returnValue = new ResponseEntity<>(authResult, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}else {
				//raise an exception
				throw ExceptionUtil.generateApiException("Exception in security service authorization verification", 
						ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(),operationName);
			}

		} catch (Exception e) {
			//check on any unexpected exceptions and throw a generic API exception
			LOGGER.debug("exception in service security authorization verification: {}", ExceptionUtils.getFullStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in security service authorization verification", 
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(),operationName);
		}
		//log what is returned		
		LOGGER.debug("service authorization verification call completed: body: {}, status code: {}", returnValue.getBody(), returnValue.getStatusCode());
		return returnValue;
	}

	/**
	 * Extract login user.
	 *
	 * @param request the request
	 * @param operationName the operation name
	 * @return the login user
	 * @throws ApiException the api exception
	 */
	private LoginUser extractLoginUser(HttpServletRequest request, String operationName) throws ApiException {
		LoginUser returnValue = null;
		InputStream requestBody = null;

		// check on user name password credential
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			requestBody = request.getInputStream();
			if(requestBody !=null) {
				LOGGER.debug("Input body stream is not NULL return login user object to start user name authentication");	
				returnValue = objectMapper.readValue(request.getInputStream(), LoginUser.class);
				String jsonInUser = objectMapper.writeValueAsString(returnValue);
				LOGGER.debug("Input user  ### :  {}", jsonInUser);				
			}else {
				LOGGER.debug("Input body stream is NULL return null login user object to start a negotiation");	
			}
			
		} catch (Exception e) {
			LOGGER.debug("Exception in log in data extraction from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in username password extraction from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), operationName);

		}

		return returnValue;

	}

	/**
	 * Jwt token authentication.
	 *
	 * @param request the request
	 * @param operationName the operation name
	 * @return the authentication result
	 * @throws ApiException the api exception
	 */
	private AuthenticationResult jwtTokenAuthentication(HttpServletRequest request, String operationName)
			throws ApiException {

		AuthenticationResult returnValue = new AuthenticationResult();

		try {
			final String serviceAuthToken = request.getHeader(serviceAuthJwtToken);

			AuthenticationResult jwtTokenAuthenticationResult = authenticationService
					.jwtTokenAuthentication(serviceAuthToken);
			if (jwtTokenAuthenticationResult.getToken() != null) {
				returnValue = jwtTokenAuthenticationResult;
				//HttpHeaders responseHeaders = new HttpHeaders();
				//responseHeaders.add(serviceAuthJwtToken, userNamePasswordAuthentication.getToken());
				//returnValue = new ResponseEntity<String>(userNamePasswordAuthentication.getUserName() + " authentication successfull", responseHeaders, HttpStatus.OK);
			} else {
				throw ExceptionUtil.generateApiException(jwtTokenAuthenticationResult.getMsg(), ExceptionHandlerType.BUSINESS_EXCEPTION_HANDLER.getValue(),operationName);

			}
		} catch (BusinessException be) {
			throw ExceptionUtil.generateApiExceptionFromBusinessException(be, operationName);
		}

		return returnValue;
	}

	/**
	 * Kerberos ticker authentication.
	 *
	 * @param request the request
	 * @param operationName the operation name
	 * @param device the device
	 * @return the authentication result
	 * @throws ApiException the api exception
	 */
	private AuthenticationResult kerberosTickerAuthentication(HttpServletRequest request, String operationName,
			Device device) throws ApiException {
		AuthenticationResult returnValue = new AuthenticationResult();
		try {

			String kerberosHeader = request.getHeader(serviceAuthKerberosTicketHeader);

			if (kerberosHeader != null
					&& (kerberosHeader.startsWith("Negotiate ") || kerberosHeader.startsWith("Kerberos "))) {

				AuthenticationResult authenticationResult = authenticationService
						.kerberosTicketAuthentication(request, kerberosHeader, device);
				if (authenticationResult.getToken() != null) {
					HttpHeaders responseHeaders = new HttpHeaders();					
					responseHeaders.add(serviceAuthJwtToken, authenticationResult.getToken());
					LOGGER.debug("username from service: {}", authenticationResult.getUserName() );
					returnValue = authenticationResult;					
				} else {
					throw ExceptionUtil.generateApiException(authenticationResult.getMsg(),
							ExceptionHandlerType.BUSINESS_EXCEPTION_HANDLER.getValue(),operationName);

				}
			} else {
				throw ExceptionUtil.generateApiException("Invalid kerberose ticket",
						ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(),operationName);
			}

		} catch (BusinessException be) {
			throw ExceptionUtil.generateApiExceptionFromBusinessException(be, operationName);
		}

		return returnValue;

	}
	
	/**
	 * Gets the authorization as list.
	 *
	 * @param jwtToken the jwt token
	 * @param operationName the operation name
	 * @return the authorization as list
	 * @throws ApiException the api exception
	 */
	private AuthorizationVerificationResult getAuthorizationAsList(String jwtToken, String operationName) throws ApiException{
		
		AuthorizationVerificationResult returnValue = new AuthorizationVerificationResult();
		try{
			AuthorizationVerificationResult authVerificationResult = authenticationService.deriveAuthorization(jwtToken);
			
			if(authVerificationResult.getAuthorizationList()!= null) {
				returnValue = authVerificationResult;
			}else {
				returnValue.setMsg(authVerificationResult.getMsg());				
			}
			
		}catch (BusinessException be) {
			throw ExceptionUtil.generateApiExceptionFromBusinessException(be, operationName);
		}
		return returnValue;		
	}

	/**
	 * User name authentication.
	 *
	 * @param request the request
	 * @param operationName the operation name
	 * @param device the device
	 * @param loginUser the login user
	 * @return the authentication result
	 * @throws ApiException the api exception
	 */
	private AuthenticationResult userNameAuthentication(HttpServletRequest request, String operationName,
			Device device, LoginUser loginUser) throws ApiException {
		AuthenticationResult returnValue = new AuthenticationResult();

		try {
			AuthenticationResult userNamePasswordAuthentication = authenticationService
					.userNamePasswordAuthentication(loginUser, device);
			if (userNamePasswordAuthentication.getToken() != null) {
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add(serviceAuthJwtToken, userNamePasswordAuthentication.getToken());				
				returnValue = userNamePasswordAuthentication;
			} else {
				throw ExceptionUtil.generateApiException(userNamePasswordAuthentication.getMsg(),
						ExceptionHandlerType.BUSINESS_EXCEPTION_HANDLER.getValue(),operationName);
			}

		} catch (BusinessException be) {
			throw ExceptionUtil.generateApiExceptionFromBusinessException(be, operationName);
		}

		return returnValue;
	}
	
	/**
	 * Creates the authorization verification result.
	 *
	 * @param msg the msg
	 * @param status the status
	 * @return the response entity
	 */
	private ResponseEntity<AuthorizationVerificationResult> createAuthorizationVerificationResult(String msg, HttpStatus status){
			ResponseEntity<AuthorizationVerificationResult> returnValue = null;
			AuthorizationVerificationResult value = new AuthorizationVerificationResult();
			value.setMsg(msg);
			value.setAuthorizationList(null);
			returnValue = new ResponseEntity<>(value, status);
			return returnValue;			
	}

}
