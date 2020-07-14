package io.kryptoblocks.msa.network.stream.service.controller.impl;

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
import io.kryptoblocks.msa.network.stream.service.business.NetworkControllerService;
import io.kryptoblocks.msa.network.stream.service.controller.NetworkDataProcessController;
import io.kryptoblocks.msa.network.stream.service.message.NetworkProcessAPIMessage;
import io.kryptoblocks.msa.network.stream.service.message.NetworkProcessBusinessMessage;
import io.kryptoblocks.msa.network.stream.service.model.NetworkProcessInput;
import io.kryptoblocks.msa.network.stream.service.model.NetworkProcessStatusInput;
import io.kryptoblocks.msa.network.stream.service.model.NetworkDataProcessStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

// TODO: Auto-generated Javadoc
/**
 * REST API for all network data process  requests.
 *
 * @author kryptoblocks
 */
@RestController 
public class NetworkDataProcessControllerImpl implements NetworkDataProcessController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkDataProcessControllerImpl.class);

	/** The network data process service. */
	@Autowired
	NetworkControllerService networkControllerService;
	//private NetworkDataProcessService networkDataProcessService;


	/** The jwt token util. */
	@Autowired
	JWTTokenUtil jwtTokenUtil;

	/** The service auth jwt token. */
	@Value("${service.auth.jwt.token}")
	private String serviceAuthJwtToken;

	/** The method name. */
	String methodName = null;

	/** The activity type. */
	NetworkProcessInput.ActivityType activityType;

	/** The user. */
	String user = null;

	/** The user. */
	Object input = null;

	/**
	 * Gets the ntwork process status.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the ntwork process status
	 * @throws ApiException the api exception
	 */
	@ApiOperation(value = "Get network data process status for a given client", notes = "Make sure the input is adhering to basic request spec - valid client id", nickname = "getNetDataProcStatus", tags = {
			"network process status", "network process for client user service" }, response = NetworkDataProcessStatus.class)
	@ApiResponses({ @ApiResponse(code = 400, message = NetworkProcessAPIMessage.NETWORK_PROCESS_API_STATUS_GET_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = NetworkProcessAPIMessage.NETWORK_PROCESS_API_STATUS_GET_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = NetworkProcessAPIMessage.NETWORK_PROCESS_API_STATUS_GET_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = NetworkProcessAPIMessage.NETWORK_PROCESS_API_STATUS_GET_INPUT_ERROR_USR_MSG) })
	@RequestMapping(value = "${network.stream.service.route.process.status}", method = RequestMethod.POST, produces = "application/json")
	@Override
	public ResponseEntity<NetworkDataProcessStatus> getNtworkProcessStatus(HttpServletRequest request, HttpServletResponse response
			) throws ApiException {
		ResponseEntity<NetworkDataProcessStatus> returnValue = null;
		try {

			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			activityType = NetworkProcessInput.ActivityType.GET_PROCESS_STATUS;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractProcessStatusInput(request, methodName);
			if (input != null) {
				
				NetworkDataProcessStatus networkDataProcessStatus = networkControllerService.getNetworkDataProcessStatus((NetworkProcessInput)input);
				//TODO
				//populate all hears
				HttpHeaders responseHeaders = new HttpHeaders();
				 
				returnValue = new ResponseEntity<NetworkDataProcessStatus>(networkDataProcessStatus, responseHeaders, HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e, activityType);
		}
		return returnValue;
	} 

	
	
	/**
	 * Handle business exception.
	 *
	 * @param e the e
	 * @param activityType the activity type
	 * @throws ApiException the api exception
	 */
	private void handleBusinessException(Exception e, NetworkProcessInput.ActivityType activityType) throws ApiException {

		String exceptionMessage = ExceptionUtils.getFullStackTrace(e);
		switch (activityType) {

		case GET_PROCESS_STATUS:
			throw new ApiException(exceptionMessage, e, NetworkProcessBusinessMessage.NETWORK_PROCESS_GET_STATUS_BUSINESS_MSG,
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

		 
		case GET_PROCESS_STATUS:
			if (user == null) {
				msg = NetworkProcessAPIMessage.NETWORK_PROCESS_API_STATUS_GET_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = NetworkProcessAPIMessage.NETWORK_PROCESS_API_STATUS_GET_INPUT_ERROR_USR_MSG;
			}
			break;

		default:
			break;
		}
		throw new ApiException(msg, ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), methodName);
	}

	/**
	 * Extract process status input.
	 *
	 * @param request the request
	 * @param operationName the operation name
	 * @return the network process status input
	 * @throws ApiException the api exception
	 */
	private NetworkProcessStatusInput extractProcessStatusInput(HttpServletRequest request, String operationName) throws ApiException {
		
		NetworkProcessStatusInput returnValue = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting network process status input");
				returnValue = objectMapper.readValue(request.getInputStream(), NetworkProcessStatusInput.class);
				LOGGER.debug("Input network status input  data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input body stream is NULL return null network status input data");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract network status inpiut data from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting network status inpiut data  from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), operationName);
		}
		return returnValue;
	}

	
}
