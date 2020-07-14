package io.kryptoblocks.msa.siem.stream.service.controller.impl;

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
import io.kryptoblocks.msa.siem.repository.model.SiemDataLandingModel;
import io.kryptoblocks.msa.siem.stream.service.business.SiemDataProcessService;
import io.kryptoblocks.msa.siem.stream.service.controller.SiemDataProcessController;
import io.kryptoblocks.msa.siem.stream.service.message.SiemProcessAPIMessage;
import io.kryptoblocks.msa.siem.stream.service.message.SiemProcessBusinessMessage;
import io.kryptoblocks.msa.siem.stream.service.model.SiemDataInjectInput;
import io.kryptoblocks.msa.siem.stream.service.model.SiemDataInjectOutput;
import io.kryptoblocks.msa.siem.stream.service.model.SiemDataProcessStatus;
import io.kryptoblocks.msa.siem.stream.service.model.SiemProcessInput;
import io.kryptoblocks.msa.siem.stream.service.model.SiemProcessStatusInput;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

// TODO: Auto-generated Javadoc
/**
 * REST API for all siem data process  requests.
 *
 * @author kryptoblocks
 */
@RestController 
public class SiemDataProcessControllerImpl implements SiemDataProcessController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SiemDataProcessControllerImpl.class);

	/** The siem data process service. */
	@Autowired
	private SiemDataProcessService siemDataProcessService;


	/** The jwt token util. */
	@Autowired
	JWTTokenUtil jwtTokenUtil;

	/** The service auth jwt token. */
	@Value("${service.auth.jwt.token}")
	private String serviceAuthJwtToken;

	/** The method name. */
	String methodName = null;

	/** The activity type. */
	SiemProcessInput.ActivityType activityType;

	/** The user. */
	String user = null;

	/** The user. */
	Object input = null;

	/**
	 * Gets the siem process status.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the siem process status
	 * @throws ApiException the api exception
	 */
	@ApiOperation(value = "Get siem data process status for a given client", notes = "Make sure the input is adhering to basic request spec - valid client id", nickname = "getSiemDataProcStatus", tags = {
			"siem process status", "siem process for client user service" }, response = SiemDataProcessStatus.class)
	@ApiResponses({ @ApiResponse(code = 400, message = SiemProcessAPIMessage.SIEM_PROCESS_API_STATUS_GET_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = SiemProcessAPIMessage.SIEM_PROCESS_API_STATUS_GET_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = SiemProcessAPIMessage.SIEM_PROCESS_API_STATUS_GET_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = SiemProcessAPIMessage.SIEM_PROCESS_API_STATUS_GET_INPUT_ERROR_USR_MSG) })
	@RequestMapping(value = "${siem.stream.service.route.process.status}", method = RequestMethod.POST, produces = "application/json")
	@Override
	public ResponseEntity<SiemDataProcessStatus> getSiemProcessStatus(HttpServletRequest request, HttpServletResponse response
			) throws ApiException {
		ResponseEntity<SiemDataProcessStatus> returnValue = null;
		try {

			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			activityType = SiemProcessInput.ActivityType.GET_PROCESS_STATUS;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractProcessStatusInput(request, methodName);
			if (input != null) {
				
				SiemDataProcessStatus siemDataProcessStatus = siemDataProcessService.getSiemDataProcessStatus((SiemProcessInput)input);
				//TODO
				//populate all hears
				HttpHeaders responseHeaders = new HttpHeaders();
				 
				returnValue = new ResponseEntity<SiemDataProcessStatus>(siemDataProcessStatus, responseHeaders, HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e, activityType);
		}
		return returnValue;
	} 
	
	
	/**
	 * Post siem data.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the response entity
	 * @throws ApiException the api exception
	 */
	@ApiOperation(value = "Post siem data from a secured client", notes = "Make sure the input is adhering to basic post spec - string content", nickname = "putSiemData", tags = {
			"siem process status", "siem process for client user service" }, response = SiemDataProcessStatus.class)
	@ApiResponses({ @ApiResponse(code = 400, message = SiemProcessAPIMessage.SIEM_PROCESS_API_STATUS_GET_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = SiemProcessAPIMessage.SIEM_DATA_API_PUT_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = SiemProcessAPIMessage.SIEM_DATA_API_PUT_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = SiemProcessAPIMessage.SIEM_DATA_API_PUT_INPUT_ERROR_USR_MSG) })
	@RequestMapping(value = "${siem.stream.service.route.process.status}", method = RequestMethod.POST, produces = "application/json")
	@Override
	public ResponseEntity<String> postSiemData(HttpServletRequest request, HttpServletResponse response
			) throws ApiException {
		ResponseEntity<String> returnValue = null;
		try {

			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			activityType = SiemProcessInput.ActivityType.PUT_DATA;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractSiemDataInput(request, methodName);
			if (input != null) {
				SiemDataInjectInput siemDataInjectInput = new SiemDataInjectInput();
				siemDataInjectInput.setInputData(input.toString());
				
				SiemDataLandingModel  siemDataLandingModel = siemDataProcessService.putSiemData(siemDataInjectInput);
				//TODO
				//populate all hears
				HttpHeaders responseHeaders = new HttpHeaders();
				 
				returnValue = new ResponseEntity<String>(siemDataLandingModel.toString(), responseHeaders, HttpStatus.OK);
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
	private void handleBusinessException(Exception e, SiemProcessInput.ActivityType activityType) throws ApiException {

		String exceptionMessage = ExceptionUtils.getFullStackTrace(e);
		switch (activityType) {

		case GET_PROCESS_STATUS:
			throw new ApiException(exceptionMessage, e, SiemProcessBusinessMessage.SIEM_PROCESS_GET_STATUS_BUSINESS_MSG,
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
				msg = SiemProcessAPIMessage.SIEM_PROCESS_API_STATUS_GET_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = SiemProcessAPIMessage.SIEM_PROCESS_API_STATUS_GET_INPUT_ERROR_USR_MSG;
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
	 * @return the siem process status input
	 * @throws ApiException the api exception
	 */
	private SiemProcessStatusInput extractProcessStatusInput(HttpServletRequest request, String operationName) throws ApiException {
		
		SiemProcessStatusInput returnValue = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting siem process status input");
				returnValue = objectMapper.readValue(request.getInputStream(), SiemProcessStatusInput.class);
				LOGGER.debug("Input siem status input  data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input body stream is NULL return null siem status input data");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract siem status inpiut data from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting siem status inpiut data  from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), operationName);
		}
		return returnValue;
	}
	
/**
 * Extract siem data input.
 *
 * @param request the request
 * @param operationName the operation name
 * @return the string
 * @throws ApiException the api exception
 */
private String extractSiemDataInput(HttpServletRequest request, String operationName) throws ApiException {
		
		String returnValue = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting siem data input");
				returnValue = objectMapper.readValue(request.getInputStream(), String.class);
				LOGGER.debug("Input siem data  input   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input body stream is NULL return null siem data input");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract siem data inpiut from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting siem data inpiut  from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), operationName);
		}
		return returnValue;
	}


	
}
