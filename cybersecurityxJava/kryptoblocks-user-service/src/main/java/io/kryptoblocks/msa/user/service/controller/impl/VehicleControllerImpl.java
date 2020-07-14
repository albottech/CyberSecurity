/**
 * 
 */
package io.kryptoblocks.msa.user.service.controller.impl;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

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
import io.kryptoblocks.msa.user.repository.model.CustomerVehicle;

import io.kryptoblocks.msa.user.service.business.CustomerService;
import io.kryptoblocks.msa.user.service.controller.VehicleController;
import io.kryptoblocks.msa.user.service.message.CustomerApiMessage;
import io.kryptoblocks.msa.user.service.message.CustomerBusinessMessage;
import io.kryptoblocks.msa.user.service.model.CustomerActivity;
import io.kryptoblocks.msa.user.service.model.CustomerVehicleUpdateInput;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 *
 */
@RestController
public class VehicleControllerImpl implements VehicleController{

	private static final Logger LOGGER = LoggerFactory.getLogger(VehicleControllerImpl.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	JWTTokenUtil jwtTokenUtil;

	@Value("${service.auth.jwt.token}")
	private String serviceAuthJwtToken;

	/** The method name. */
	String methodName = null;
    
	CustomerActivity.Type customerActivity;

	/** The user. */
	String user = null;

	/** The user. */
	Object input = null;
	
	
	@ApiOperation(value = "Get customer vehicles", notes = "Make sure the input is adhering to customer vehicle get requirements", nickname = "addBankAccount", tags = {
			"customer", "customer service get vehicles" }, response = CustomerVehicle.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_GET_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_GET_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_GET_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_GET_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.vh}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<CustomerVehicle>>  getVehicle(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<List<CustomerVehicle>> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.VEHICLE_GET_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = new Object();
			if ((user != null)) {
				List<CustomerVehicle> customerVehicles = customerService.getVehicles(user);			
				returnValue = new ResponseEntity<List<CustomerVehicle>>(customerVehicles, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	
	@ApiOperation(value = "Add customer vehicle", notes = "Make sure the input is adhering to customer vehicle add requirements", nickname = "addDrivenLicence", tags = {
			"customer", "customer service add vehicle" }, response = CustomerVehicle.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_POST_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_POST_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_POST_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_POST_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.vh}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<CustomerVehicle>  addVehicle(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerVehicle> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.VEHICLE_ADD_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractCustomerVehicleEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerVehicle customerVehicle = customerService.saveUpdateVehicle((CustomerVehicleUpdateInput)input);				
				returnValue = new ResponseEntity<CustomerVehicle>(customerVehicle, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	
	@ApiOperation(value = "Update customer vehicle", notes = "Make sure the input is adhering to customer vehicle update requirements", nickname = "addDrivenLicence", tags = {
			"customer", "customer service update vehicle" }, response = CustomerVehicle.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_PATCH_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_PATCH_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_PATCH_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_PATCH_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.vh}", method = RequestMethod.PATCH, produces = "application/json")
	public ResponseEntity<CustomerVehicle>  updateVehicle(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerVehicle> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.VEHICLE_UPDATE_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractCustomerVehicleEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerVehicle customerVehicle = customerService.saveUpdateVehicle((CustomerVehicleUpdateInput)input);				
				returnValue = new ResponseEntity<CustomerVehicle>(customerVehicle, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	@ApiOperation(value = "Delete customer vehicle", notes = "Make sure the input is adhering to customer vehicle delete requirements", nickname = "addDrivenLicence", tags = {
			"customer", "customer service delete vehicle" }, response = CustomerVehicle.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_DELETE_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_DELETE_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_DELETE_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_VEHICLE_DELETE_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.vh}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<CustomerVehicle>  deleteVehicle(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerVehicle> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.VEHICLE_REMOVE_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractCustomerVehicleEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerVehicle customerVehicle = customerService.removeVehicle((CustomerVehicleUpdateInput)input);				
				returnValue = new ResponseEntity<CustomerVehicle>(customerVehicle, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	private CustomerVehicleUpdateInput extractCustomerVehicleEvent(HttpServletRequest request, String methodName) throws ApiException {
		CustomerVehicleUpdateInput returnValue = null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting customer vehicle event");
				returnValue = objectMapper.readValue(request.getInputStream(), CustomerVehicleUpdateInput.class);
				LOGGER.debug("Input customer vehicle event data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input customer credit card stream is NULL return null customer credit card event object");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract customer vehicleevent from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting customer vehicle event input from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), methodName);
		}
		return returnValue;
		
	}
	
	private HttpHeaders getHeaders(HttpServletRequest request) {
		HttpHeaders  returnValue = new HttpHeaders();
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {
			String curHeaderName = headerNames.nextElement();
			returnValue.add(curHeaderName, request.getHeader(curHeaderName));
		}
		return returnValue;
		
	}
	
	private void handleInputException() throws ApiException {

		String msg = null;

		switch (customerActivity) {

		case VEHICLE_GET_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_VEHICLE_GET_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_VEHICLE_GET_INPUT_ERROR_USR_MSG;
			}			
			break;
		case VEHICLE_ADD_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_VEHICLE_POST_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_VEHICLE_POST_INPUT_ERROR_USR_MSG;
			}			
			break;
		
		case VEHICLE_UPDATE_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_VEHICLE_PATCH_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_VEHICLE_PATCH_INPUT_ERROR_USR_MSG;
			}			
			break;
		case VEHICLE_REMOVE_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_VEHICLE_DELETE_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_VEHICLE_DELETE_INPUT_ERROR_USR_MSG;
			}			
			break;
		 
		default:
			break;
		}
		throw new ApiException(msg, ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), methodName);
	}
	
	private void handleBusinessException(Exception e) throws ApiException {

		String userMsg = null;
		String exceptionMessage = ExceptionUtils.getFullStackTrace(e);

		switch (customerActivity) {

		case VEHICLE_GET_ACTIVITY:			 
			userMsg = CustomerBusinessMessage.VEHICLE_GET_ERROR_USR_MSG;			 		
			break;
		case VEHICLE_ADD_ACTIVITY:
			userMsg = CustomerBusinessMessage.VEHICLE_POST_ERROR_USR_MSG;			 	
			break;		
		case VEHICLE_UPDATE_ACTIVITY:
			userMsg = CustomerBusinessMessage.VEHICLE_PATCH_ERROR_USR_MSG;		
			break;
		case VEHICLE_REMOVE_ACTIVITY:
			userMsg = CustomerBusinessMessage.VEHICLE_DELETE_ERROR_USR_MSG;		
			break;		 
		default:
			break;
		}
		throw new ApiException(exceptionMessage, e,userMsg, methodName);
	}
}
