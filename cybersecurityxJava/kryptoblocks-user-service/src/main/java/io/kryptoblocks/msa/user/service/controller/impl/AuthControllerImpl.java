package io.kryptoblocks.msa.user.service.controller.impl;

import java.io.InputStream;

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
 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.common.jwt.JWTTokenUtil;
import io.kryptoblocks.msa.common.model.ExceptionHandlerType;
import io.kryptoblocks.msa.common.util.ExceptionUtil;
import io.kryptoblocks.msa.user.repository.model.User;

import io.kryptoblocks.msa.user.service.business.UserService;
import io.kryptoblocks.msa.user.service.controller.AuthController;
import io.kryptoblocks.msa.user.service.message.UserApiMessage;
import io.kryptoblocks.msa.user.service.message.UserBusinessMessage;
import io.kryptoblocks.msa.user.service.model.AuthInput;
import io.kryptoblocks.msa.user.service.model.LoginInput;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST API for all user requests
 * 
 * @author smartride
 *
 */
@RestController 
public class AuthControllerImpl implements AuthController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthControllerImpl.class);

	@Autowired
	private UserService userService;


	@Autowired
	JWTTokenUtil jwtTokenUtil;

	@Value("${service.auth.jwt.token}")
	private String serviceAuthJwtToken;

	/** The method name. */
	String methodName = null;

	AuthInput.ActivityType activityType;

	/** The user. */
	String user = null;

	/** The user. */
	Object input = null;

	 

	@ApiOperation(value = "Get user authorization", notes = "Make sure the input is adhering to basic user autherization requirements", nickname = "getUserAuth", tags = {
			"user", "user service get user authentication" }, response = User.class)
	@ApiResponses({ @ApiResponse(code = 400, message = UserApiMessage.USER_API_AUTH_GET_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = UserApiMessage.USER_API_AUTH_GET_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = UserApiMessage.USER_API_AUTH_GET_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = UserApiMessage.USER_API_AUTH_GET_ERROR_USR_MSG) })
	@RequestMapping(value = "${user.service.route.signin.local}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<User> signinLocal(HttpServletRequest request, HttpServletResponse response, Device device)
			throws ApiException {

		ResponseEntity<User> returnValue = null;
		try {

			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			activityType = AuthInput.ActivityType.AUTH_USER;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractUserAuthInput(request, methodName);
			if (input != null) {
				User user = userService.verifyUser((LoginInput)input);
				String securityToken = user.getSessionToken();
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add(serviceAuthJwtToken, securityToken);
				returnValue = new ResponseEntity<>(user, responseHeaders, HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e, activityType);
		}
		return returnValue;
	}

	@ApiOperation(value = "Get social user authorization", notes = "Make sure the input is adhering to basic social user autherization requirements", nickname = "getSocialUserAuth", tags = {
			"user", "user service get social user authentication" }, response = User.class)
	@ApiResponses({ @ApiResponse(code = 400, message = UserApiMessage.USER_API_AUTH_GET_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = UserApiMessage.USER_API_AUTH_GET_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = UserApiMessage.USER_API_AUTH_GET_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = UserApiMessage.USER_API_AUTH_GET_ERROR_USR_MSG) })
	@RequestMapping(value = "${user.service.route.signin.social}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<User> signinSocial(HttpServletRequest request, HttpServletResponse response,
			Device device) throws ApiException {

		ResponseEntity<User> returnValue = null;
		try {

			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			activityType = AuthInput.ActivityType.AUTH_USER;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractUserAuthInput(request, methodName);
			if ((input != null) && (user != null)) {
				User user = userService.verifyUser((LoginInput)input);
				String securityToken = user.getSessionToken();
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.add(serviceAuthJwtToken, securityToken);
				returnValue = new ResponseEntity<>(user, responseHeaders, HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e, activityType);
		}
		return returnValue;
	}

	private void handleBusinessException(Exception e, AuthInput.ActivityType activityType) throws ApiException {

		String exceptionMessage = ExceptionUtils.getFullStackTrace(e);
		switch (activityType) {

		case AUTH_USER:
			throw new ApiException(exceptionMessage, e, UserBusinessMessage.USER_AUTH_GET_BALANCE_BUSINESS_MSG,
					methodName);

		case GET_LOGGED_USER:
			throw new ApiException(exceptionMessage, e, UserBusinessMessage.USER_AUTH_GET_LOGGED_USER_BUSINESS_MSG,
					methodName);

		default:
			break;
		}
	}

	/**
	 * Raise input exception.
	 *
	 * @throws ApiException
	 *             the api exception
	 */
	private void handleInputException() throws ApiException {

		String msg = null;

		switch (activityType) {

		case AUTH_USER:
			if (user == null) {
				msg = UserApiMessage.USER_API_AUTH_GET_SECURITY_ERROR_USR_MSG;
			}

			break;
		case GET_LOGGED_USER:
			if (user == null) {
				msg = UserApiMessage.USER_API_AUTH_GET_LOGGED_USER_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = UserApiMessage.USER_API_AUTH_GET_LOGGED_USER_INPUT_ERROR_USR_MSG;
			}
			break;

		default:
			break;
		}
		throw new ApiException(msg, ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), methodName);
	}

	private LoginInput extractUserAuthInput(HttpServletRequest request, String operationName) throws ApiException {
		LoginInput returnValue = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting user auth input");
				returnValue = objectMapper.readValue(request.getInputStream(), LoginInput.class);
				LOGGER.debug("Input user auth data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input body stream is NULL return null user auth object");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract user auth from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting user auth input from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), operationName);
		}
		return returnValue;
	}
}
