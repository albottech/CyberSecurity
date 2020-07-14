package io.kryptoblocks.msa.udp.listner.service.controller.impl;

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
import io.kryptoblocks.msa.udp.listner.service.business.UDPListnerService;
import io.kryptoblocks.msa.udp.listner.service.controller.UDPListnerController;
import io.kryptoblocks.msa.udp.listner.service.message.UDPListnerAPIMessage;
import io.kryptoblocks.msa.udp.listner.service.message.UDPListnerBusinessMessage;
import io.kryptoblocks.msa.udp.listner.service.model.UDPListnerStatus;
import io.kryptoblocks.msa.udp.listner.service.model.UDPListnerInput;
import io.kryptoblocks.msa.udp.listner.service.model.UDPListnerStatusInput;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST API for all network data process  requests
 * 
 * @author kryptoblocks
 *
 */
@RestController 
public class UDPListnerControllerImpl implements UDPListnerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UDPListnerControllerImpl.class);

	 


	@Autowired
	JWTTokenUtil jwtTokenUtil;

	@Value("${service.auth.jwt.token}")
	private String serviceAuthJwtToken;

	/** The method name. */
	String methodName = null;

	UDPListnerInput.ActivityType activityType;

	/** The user. */
	String user = null;

	/** The user. */
	Object input = null;

	@ApiOperation(value = "Get network data process status for a given client", notes = "Make sure the input is adhering to basic request spec - valid client id", nickname = "getNetDataProcStatus", tags = {
			"network process status", "network process for client user service" }, response = UDPListnerStatus.class)
	@ApiResponses({ @ApiResponse(code = 400, message = UDPListnerAPIMessage.NETWORK_PROCESS_API_STATUS_GET_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = UDPListnerAPIMessage.NETWORK_PROCESS_API_STATUS_GET_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = UDPListnerAPIMessage.NETWORK_PROCESS_API_STATUS_GET_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = UDPListnerAPIMessage.NETWORK_PROCESS_API_STATUS_GET_INPUT_ERROR_USR_MSG) })
	@RequestMapping(value = "${udp.listner.service.route.process.status}", method = RequestMethod.POST, produces = "application/json")
	@Override
	public ResponseEntity<UDPListnerStatus> getNtworkProcessStatus(HttpServletRequest request, HttpServletResponse response
			) throws ApiException {
		ResponseEntity<UDPListnerStatus> returnValue = null;
		try {

			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			activityType = UDPListnerInput.ActivityType.GET_PROCESS_STATUS;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractProcessStatusInput(request, methodName);
			if (input != null) {
				
				  
				//populate all hears
				HttpHeaders responseHeaders = new HttpHeaders();
				 
				 
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e, activityType);
		}
		return returnValue;
	} 

	
	
	private void handleBusinessException(Exception e, UDPListnerInput.ActivityType activityType) throws ApiException {

		String exceptionMessage = ExceptionUtils.getFullStackTrace(e);
		switch (activityType) {

		case GET_PROCESS_STATUS:
			throw new ApiException(exceptionMessage, e, UDPListnerBusinessMessage.NETWORK_PROCESS_GET_STATUS_BUSINESS_MSG,
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
				msg = UDPListnerAPIMessage.NETWORK_PROCESS_API_STATUS_GET_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = UDPListnerAPIMessage.NETWORK_PROCESS_API_STATUS_GET_INPUT_ERROR_USR_MSG;
			}
			break;

		default:
			break;
		}
		throw new ApiException(msg, ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), methodName);
	}

	private UDPListnerStatusInput extractProcessStatusInput(HttpServletRequest request, String operationName) throws ApiException {
		
		UDPListnerStatusInput returnValue = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting network process status input");
				returnValue = objectMapper.readValue(request.getInputStream(), UDPListnerStatusInput.class);
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
