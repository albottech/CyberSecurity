package io.kryptoblocks.msa.user.service.controller.impl;

import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.common.jwt.JWTTokenUtil;
import io.kryptoblocks.msa.common.model.ExceptionHandlerType;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.ExceptionUtil;
import io.kryptoblocks.msa.user.repository.model.User;
import io.kryptoblocks.msa.user.repository.model.UserToken;

import io.kryptoblocks.msa.user.service.business.UserService;
import io.kryptoblocks.msa.user.service.controller.SignUpController;
import io.kryptoblocks.msa.user.service.message.UserApiMessage;
import io.kryptoblocks.msa.user.service.message.UserBusinessMessage;
import io.kryptoblocks.msa.user.service.model.ActivationCodeInput;
import io.kryptoblocks.msa.user.service.model.ActivationCodeOutput;
import io.kryptoblocks.msa.user.service.model.SignUpInput;
import io.kryptoblocks.msa.user.service.model.SignupConfirmation;
import io.kryptoblocks.msa.user.service.model.TokenInput;
import io.kryptoblocks.msa.user.service.model.UserInput;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
 
// TODO: Auto-generated Javadoc
/**
 * The Class SignUpController.
 */
@RestController  
public class SignUpControllerImpl  implements SignUpController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SignUpControllerImpl.class);

	 
	/** The user service. */
	@Autowired
	private UserService userService;
 
	/** The jwt token util. */
	@Autowired
	JWTTokenUtil jwtTokenUtil;

	/** The service auth jwt token. */
	@Value("${service.auth.jwt.token}")
	private String serviceAuthJwtToken;

	/** The method name. */
	String methodName = null;

	/** The activity type. */
	UserInput.ActivityType activityType;

	/** The user. */
	String user = null;

	/** The user. */
	Object input = null;

	/**
	 * Sign up.
	 *
	 * @param request the request
	 * @param response the response
	 * @param device the device
	 * @return the response entity
	 * @throws ApiException the api exception
	 */
	@ApiOperation(value = "User signup", notes = "Make sure the input is adhering to basic user signup requirements", nickname = "signup", tags = {
			"user", "user service " }, response = User.class)
	@ApiResponses({ @ApiResponse(code = 400, message = UserApiMessage.USER_API_SIGNUP_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = UserApiMessage.USER_API_SIGNUP_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = UserApiMessage.USER_API_SIGNUP_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = UserApiMessage.USER_API_SIGNUP_ERROR_USR_MSG) })
	@RequestMapping(value = "${user.service.route.signup}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<User> signUp(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<User> returnValue = null;
		try {

			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			activityType = UserInput.ActivityType.SIGN_UP;
			LOGGER.debug("signup called");
			input = extractSignupRequest(request, methodName);
			if (input != null) {
				SignUpInput signupInput = (SignUpInput)input;
				signupInput.setDevice(device);
				User user = userService.registerNewUser(signupInput);
				String securityToken = user.getSessionToken();
				HttpHeaders responseHeaders = getHeaders(request);
				responseHeaders.add(serviceAuthJwtToken, securityToken);
				returnValue = new ResponseEntity<>(user, responseHeaders, HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		
	}
	 
	/**
	 * Confirm sign up.
	 *
	 * @param conSignupTkn the con signup tkn
	 * @param request the request
	 * @param response the response
	 * @param device the device
	 * @return the response entity
	 * @throws ApiException the api exception
	 */
	@ApiOperation(value = "User signup confirmation", notes = "Make sure the input is adhering to basic user signup confirmation requirements", nickname = "signup confirm", tags = {
			"user", "user service " }, response = SignupConfirmation.class)
	@ApiResponses({ @ApiResponse(code = 400, message = UserApiMessage.USER_API_SIGNUP_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = UserApiMessage.USER_API_SIGNUP_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = UserApiMessage.USER_API_SIGNUP_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = UserApiMessage.USER_API_SIGNUP_ERROR_USR_MSG) })
	@RequestMapping(value = "${user.service.route.signup.confirm}", method = RequestMethod.POST, produces = "application/json")
	
	public ResponseEntity<SignupConfirmation> confirmSignUp(@PathVariable("conSignupTkn")String conSignupTkn,HttpServletRequest request, HttpServletResponse response, Device device)
			throws ApiException {
		
		ResponseEntity<SignupConfirmation> returnValue = null;
		
		try {
			SignupConfirmation signupConfirmation = new SignupConfirmation();
			String securityToken = null;
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			activityType = UserInput.ActivityType.SIGN_UP_CONFIRM;
			//user = jwtTokenUtil.extractUserFromRequest(request);
			input = conSignupTkn;
			if (input != null) {
				User user = userService.confirmSignup((String)input);
				if(user != null) {
					signupConfirmation.setConfirmed(true);
					signupConfirmation.setUser(user);
					securityToken = user.getSessionToken();
				}else {
					signupConfirmation.setConfirmed(false);
					signupConfirmation.setUser(null);
					//TODO
					signupConfirmation.setMessage("");
				}
				
				HttpHeaders responseHeaders = getHeaders(request);
				responseHeaders.add(serviceAuthJwtToken, securityToken);
				returnValue = new ResponseEntity<>(signupConfirmation, responseHeaders, HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		
	}

	/**
	 * Send activation code.
	 *
	 * @param request the request
	 * @param response the response
	 * @param device the device
	 * @return the response entity
	 * @throws ApiException the api exception
	 */
	@ApiOperation(value = "User activation code generation", notes = "Make sure the input is adhering to basic user activation code generation requirements", nickname = "user activation code generation", tags = {
			"user", "user service " }, response = SignupConfirmation.class)
	@ApiResponses({ @ApiResponse(code = 400, message = UserApiMessage.USER_API_ACTIVATION_REQUEST_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = UserApiMessage.USER_API_ACTIVATION_REQUEST_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = UserApiMessage.USER_API_ACTIVATION_REQUEST_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = UserApiMessage.USER_API_ACTIVATION_REQUEST_INPUT_ERROR_USR_MSG) })
	@RequestMapping(value = "${user.service.route.activation}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ActivationCodeOutput> sendActivationCode(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<ActivationCodeOutput> returnValue = null;
		
		try {
			ActivationCodeOutput activationCodeResponse = new ActivationCodeOutput();
			 
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			activityType = UserInput.ActivityType.SEND_ACTIVATION_CODE;
			//user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractActivationCodeRequest(request, methodName);
			if (input != null) {
				ActivationCodeInput activationCodeRequest = (ActivationCodeInput)input;
				TokenInput tokenInput = new TokenInput();
				tokenInput.setCreatedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
				tokenInput.setEmail(activationCodeRequest.getEmail());
				tokenInput.setType(activationCodeRequest.getType());				
				UserToken userToken = userService.createUserVerificationToken(tokenInput);
				if(userToken != null) {
					activationCodeResponse.setEmail(activationCodeRequest.getEmail());
					activationCodeResponse.setMsg("succee msg");
					 
				}else {
					activationCodeResponse.setEmail(activationCodeRequest.getEmail());
					activationCodeResponse.setMsg("failed msg");
				}				
				HttpHeaders responseHeaders = getHeaders(request);				 
				returnValue = new ResponseEntity<>(activationCodeResponse, responseHeaders, HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		
	}

	 
	 

	 
	/**
	 * Change user password.
	 *
	 * @param request the request
	 * @param response the response
	 * @param device the device
	 * @return the response entity
	 */
	public ResponseEntity<String> changeUserPassword(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException
	 {
		return null;
	}

	 
	 
	 

	 
	 
	/**
	 * Extract signup request.
	 *
	 * @param request the request
	 * @param operationName the operation name
	 * @return the user sign up request
	 * @throws ApiException the api exception
	 */
	private SignUpInput extractSignupRequest(HttpServletRequest request, String operationName) throws ApiException {
		SignUpInput returnValue = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting user signup input");
				returnValue = objectMapper.readValue(request.getInputStream(), SignUpInput.class);
				LOGGER.debug("Input user signup data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input body stream is NULL return null user signup object");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract user signup from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting user signup input from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), operationName);
		}
		return returnValue;
		
	}
	
	/**
	 * Extract activation code request.
	 *
	 * @param request the request
	 * @param operationName the operation name
	 * @return the activation code request
	 * @throws ApiException the api exception
	 */
	private ActivationCodeInput extractActivationCodeRequest(HttpServletRequest request, String operationName) throws ApiException {
		ActivationCodeInput returnValue = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting activation input");
				returnValue = objectMapper.readValue(request.getInputStream(), ActivationCodeInput.class);
				LOGGER.debug("Input user activation data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input body stream is NULL return null user activation request object");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract user activaiton request from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting user activation input from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), operationName);
		}
		return returnValue;
		
	}
	
	/**
	 * Gets the headers.
	 *
	 * @param request the request
	 * @return the headers
	 */
	private HttpHeaders getHeaders(HttpServletRequest request) {
		HttpHeaders  returnValue = new HttpHeaders();
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String curHeaderName = headerNames.nextElement();
			returnValue.add(curHeaderName, request.getHeader(curHeaderName));
		}
		return returnValue;
		
	}
	
	/**
	 * Handle input exception.
	 *
	 * @throws ApiException the api exception
	 */
	private void handleInputException() throws ApiException {

		String msg = null;

		switch (activityType) {

		case SIGN_UP:
			 
			if (input == null) {
				msg = UserApiMessage.USER_API_SIGNUP_INPUT_ERROR_USR_MSG;
			}			
			break;
		 
		default:
			break;
		}
		throw new ApiException(msg, ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), methodName);
	}
	
	/**
	 * Handle business exception.
	 *
	 * @param e the e
	 * @throws ApiException the api exception
	 */
	private void handleBusinessException(Exception e) throws ApiException {

		String userMsg = null;
		String exceptionMessage = ExceptionUtils.getFullStackTrace(e);

		switch (activityType) {

		case SIGN_UP:			 
			userMsg = UserBusinessMessage.USER_SIGNUP_ERROR_MSG;	 		
			break;
		 
		default:
			break;
		}
		throw new ApiException(exceptionMessage, e,userMsg, methodName);
	}


}
