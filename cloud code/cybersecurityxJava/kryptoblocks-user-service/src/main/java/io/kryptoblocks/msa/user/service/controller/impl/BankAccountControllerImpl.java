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
import io.kryptoblocks.msa.user.repository.model.User;

import io.kryptoblocks.msa.user.service.business.CustomerService;
import io.kryptoblocks.msa.user.service.controller.BankAccountController;
import io.kryptoblocks.msa.user.service.message.CustomerApiMessage;
import io.kryptoblocks.msa.user.service.message.CustomerBusinessMessage;
import io.kryptoblocks.msa.user.service.model.CustomerActivity;
import io.kryptoblocks.msa.user.service.model.CustomerBankAccountDeleteInput;
import io.kryptoblocks.msa.user.service.model.CustomerBankAccountGetInput;
import io.kryptoblocks.msa.user.service.model.CustomerBankAccountUpdateInput;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST API for all user bank account configuration
 * 
 * @author insignia
 *
 */
@RestController 
public class BankAccountControllerImpl implements BankAccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountControllerImpl.class);
	
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
	 
	@ApiOperation(value = "Get customer bank account", notes = "Make sure the input is adhering to customer bank account data requirements", nickname = "addBankAccount", tags = {
			"customer", "customer service get customer bank account" }, response = User.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_GET_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_GET_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_GET_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_GET_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.bank}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<CustomerBankAccount>>  getBankAccount(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<List<CustomerBankAccount>> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.BANK_ACCOUNT_GET_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = new Object();
			if ((user != null)) {
				List<CustomerBankAccount> customerAccounts = customerService.getBankAccounts(user);				
				returnValue = new ResponseEntity<List<CustomerBankAccount>>(customerAccounts, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	@ApiOperation(value = "Add customer bank account", notes = "Make sure the input is adhering to customer bank account data requirements", nickname = "addBankAccount", tags = {
			"customer", "customer service add customer bank account" }, response = User.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_POST_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_POST_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_POST_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_POST_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.bank}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<CustomerBankAccount>  addBankAccount(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerBankAccount> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.BANK_ACCOUNT_ADD_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractBankAccountUpdateEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerBankAccount customerAccounts = customerService.saveOrUpdateBank((CustomerBankAccountUpdateInput)input);				
				returnValue = new ResponseEntity<CustomerBankAccount>(customerAccounts, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	@ApiOperation(value = "Update customer bank account", notes = "Make sure the input is adhering to customer bank account data requirements", nickname = "addBankAccount", tags = {
			"customer", "customer service update customer bank account" }, response = User.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_PATCH_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_PATCH_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_PATCH_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_PATCH_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.bank}", method = RequestMethod.PATCH, produces = "application/json")
	public ResponseEntity<CustomerBankAccount>  updateBankAccount(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerBankAccount> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.BANK_ACCOUNT_UPDATE_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractBankAccountUpdateEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerBankAccount customerAccounts = customerService.saveOrUpdateBank((CustomerBankAccountUpdateInput)input);				
				returnValue = new ResponseEntity<CustomerBankAccount>(customerAccounts, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	@ApiOperation(value = "Delete customer bank account", notes = "Make sure the input is adhering to customer bank account data requirements", nickname = "addBankAccount", tags = {
			"customer", "customer service delete customer bank account" }, response = User.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_DELETE_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_DELETE_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_DELETE_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_DELETE_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.bank}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<CustomerBankAccount>  deleteBankAccount(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerBankAccount> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.BANK_ACCOUNT_UPDATE_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractBankAccountDeleteEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerBankAccount customerAccounts = customerService.removeBankAccount((CustomerBankAccountDeleteInput)input);				
				returnValue = new ResponseEntity<CustomerBankAccount>(customerAccounts, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	private CustomerBankAccountGetInput extractBankAccountGetEvent(HttpServletRequest request, String methodName) throws ApiException {
		CustomerBankAccountGetInput returnValue = null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting bank account get method");
				returnValue = objectMapper.readValue(request.getInputStream(), CustomerBankAccountGetInput.class);
				LOGGER.debug("Input bank account get data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input bank account get stream is NULL return null bank account get event object");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract bank account get event from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting bank account get input from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), methodName);
		}
		return returnValue;
		
	}
	
	private CustomerBankAccountUpdateInput extractBankAccountUpdateEvent(HttpServletRequest request, String methodName) throws ApiException {
		CustomerBankAccountUpdateInput returnValue = null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting bank account update method");
				returnValue = objectMapper.readValue(request.getInputStream(), CustomerBankAccountUpdateInput.class);
				LOGGER.debug("Input bank account update data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input bank account update stream is NULL return null bank account update event object");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract bank account update event from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting bank account update input from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), methodName);
		}
		return returnValue;
		
	}
	
	private CustomerBankAccountDeleteInput extractBankAccountDeleteEvent(HttpServletRequest request, String methodName) throws ApiException {
		CustomerBankAccountDeleteInput returnValue = null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting bank account delete method");
				returnValue = objectMapper.readValue(request.getInputStream(), CustomerBankAccountDeleteInput.class);
				LOGGER.debug("Input bank account delete data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input bank account delete stream is NULL return null bank account delete event object");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract bank account delete event from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting bank account delete input from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), methodName);
		}
		return returnValue;
		
	}
	 
	private void handleInputException() throws ApiException {

		String msg = null;

		switch (customerActivity) {

		case BANK_ACCOUNT_GET_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_GET_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_GET_INPUT_ERROR_USR_MSG;
			}			
			break;
		case BANK_ACCOUNT_ADD_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_POST_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_POST_INPUT_ERROR_USR_MSG;
			}			
			break;
		
		case BANK_ACCOUNT_UPDATE_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_PATCH_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_PATCH_INPUT_ERROR_USR_MSG;
			}			
			break;
		case BANK_ACCOUNT_REMOVE_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_DELETE_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_BANK_ACCOUNT_DELETE_INPUT_ERROR_USR_MSG;
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

		case BANK_ACCOUNT_GET_ACTIVITY:			 
			userMsg = CustomerBusinessMessage.BANK_ACCOUNT_GET_ERROR_USR_MSG;			 		
			break;
		case BANK_ACCOUNT_ADD_ACTIVITY:
			userMsg = CustomerBusinessMessage.BANK_ACCOUNT_POST_ERROR_USR_MSG;			 	
			break;		
		case BANK_ACCOUNT_UPDATE_ACTIVITY:
			userMsg = CustomerBusinessMessage.BANK_ACCOUNT_PATCH_ERROR_USR_MSG;		
			break;
		case BANK_ACCOUNT_REMOVE_ACTIVITY:
			userMsg = CustomerBusinessMessage.BANK_ACCOUNT_DELETE_ERROR_USR_MSG;		
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

}
