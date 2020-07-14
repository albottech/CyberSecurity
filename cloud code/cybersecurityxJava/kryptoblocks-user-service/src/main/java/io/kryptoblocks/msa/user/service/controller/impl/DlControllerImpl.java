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
import io.kryptoblocks.msa.user.repository.model.CustomerBankAccount;
import io.kryptoblocks.msa.user.repository.model.CustomerCreditCard;
import io.kryptoblocks.msa.user.repository.model.CustomerDriverLicense;
import io.kryptoblocks.msa.user.repository.model.User;

import io.kryptoblocks.msa.user.service.business.CustomerService;
import io.kryptoblocks.msa.user.service.controller.DlController;
import io.kryptoblocks.msa.user.service.message.CustomerApiMessage;
import io.kryptoblocks.msa.user.service.message.CustomerBusinessMessage;
import io.kryptoblocks.msa.user.service.model.CustomerActivity;
import io.kryptoblocks.msa.user.service.model.CustomerCreditCardDeleteInput;
import io.kryptoblocks.msa.user.service.model.CustomerCreditCardInput;
import io.kryptoblocks.msa.user.service.model.CustomerDLUpdateInput;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

 
@RestController 
public class DlControllerImpl implements DlController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DlControllerImpl.class);

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
	
	
	@ApiOperation(value = "Get customer driver license", notes = "Make sure the input is adhering to customer driver license data requirements", nickname = "addBankAccount", tags = {
			"customer", "customer service get customer driver license" }, response = CustomerDriverLicense.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_GET_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_GET_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_GET_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_GET_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.dl}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<CustomerDriverLicense>>  getDriverLicense(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<List<CustomerDriverLicense>> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.DRIVER_LICENSE_GET_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = new Object();
			if ((user != null)) {
				List<CustomerDriverLicense> customerDLs = customerService.getDL(user);			
				returnValue = new ResponseEntity<List<CustomerDriverLicense>>(customerDLs, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	
	@ApiOperation(value = "Add customer driver license", notes = "Make sure the input is adhering to customer driven license add requirements", nickname = "addDrivenLicence", tags = {
			"customer", "customer service add driven license" }, response = CustomerDriverLicense.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_POST_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_POST_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_POST_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_POST_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.dl}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<CustomerDriverLicense>  addDriverLicense(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerDriverLicense> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.CREDIT_CARD_ADD_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractCustomerDriverLicenseEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerDriverLicense customerDriverLicense = customerService.saveUpdateDL((CustomerDLUpdateInput)input);				
				returnValue = new ResponseEntity<CustomerDriverLicense>(customerDriverLicense, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	@ApiOperation(value = "Update customer driver license", notes = "Make sure the input is adhering to customer driven license add requirements", nickname = "addDrivenLicence", tags = {
			"customer", "customer service update driven license" }, response = CustomerDriverLicense.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_PATCH_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_PATCH_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_PATCH_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_PATCH_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.dl}", method = RequestMethod.PATCH, produces = "application/json")
	public ResponseEntity<CustomerDriverLicense>  updateDriverLicense(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerDriverLicense> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.CREDIT_CARD_ADD_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractCustomerDriverLicenseEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerDriverLicense customerDriverLicense = customerService.saveUpdateDL((CustomerDLUpdateInput)input);				
				returnValue = new ResponseEntity<CustomerDriverLicense>(customerDriverLicense, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	@ApiOperation(value = "Delete customer driver license", notes = "Make sure the input is adhering to customer driven license add requirements", nickname = "addDrivenLicence", tags = {
			"customer", "customer service delete driven license" }, response = CustomerDriverLicense.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_DELETE_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_DELETE_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_DELETE_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_DELETE_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.dl}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<CustomerDriverLicense>  deleteDriverLicense(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerDriverLicense> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.CREDIT_CARD_ADD_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractCustomerDriverLicenseEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerDriverLicense customerDriverLicense = customerService.removeDL((CustomerDLUpdateInput)input);				
				returnValue = new ResponseEntity<CustomerDriverLicense>(customerDriverLicense, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	private void handleInputException() throws ApiException {

		String msg = null;

		switch (customerActivity) {

		case DRIVER_LICENSE_GET_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_GET_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_GET_INPUT_ERROR_USR_MSG;
			}			
			break;
		case DRIVER_LICENSE_ADD_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_POST_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_POST_INPUT_ERROR_USR_MSG;
			}			
			break;
		
		case DRIVER_LICENSE_UPDATE_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_PATCH_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_PATCH_INPUT_ERROR_USR_MSG;
			}			
			break;
		case DRIVER_LICENSE_REMOVE_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_DELETE_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_DRIVER_LICENSE_DELETE_INPUT_ERROR_USR_MSG;
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

		case DRIVER_LICENSE_GET_ACTIVITY:			 
			userMsg = CustomerBusinessMessage.DRIVER_LICENSE_GET_ERROR_USR_MSG;			 		
			break;
		case DRIVER_LICENSE_ADD_ACTIVITY:
			userMsg = CustomerBusinessMessage.DRIVER_LICENSE_POST_ERROR_USR_MSG;			 	
			break;		
		case DRIVER_LICENSE_UPDATE_ACTIVITY:
			userMsg = CustomerBusinessMessage.DRIVER_LICENSE_PATCH_ERROR_USR_MSG;		
			break;
		case DRIVER_LICENSE_REMOVE_ACTIVITY:
			userMsg = CustomerBusinessMessage.DRIVER_LICENSE_DELETE_ERROR_USR_MSG;		
			break;		 
		default:
			break;
		}
		throw new ApiException(exceptionMessage, e,userMsg, methodName);
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
	
	
	private CustomerDLUpdateInput extractCustomerDriverLicenseEvent(HttpServletRequest request, String methodName) throws ApiException {
		CustomerDLUpdateInput returnValue = null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting customer dl event");
				returnValue = objectMapper.readValue(request.getInputStream(), CustomerDLUpdateInput.class);
				LOGGER.debug("Input customer dl data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input customer dl stream is NULL return null customer dl delete event object");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract customer dl event from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting customer dl input from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), methodName);
		}
		return returnValue;
		
	}

	 
}
