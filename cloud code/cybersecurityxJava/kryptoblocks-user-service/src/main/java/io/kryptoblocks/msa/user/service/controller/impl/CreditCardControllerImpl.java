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
import io.kryptoblocks.msa.user.repository.model.CustomerCreditCard;

import io.kryptoblocks.msa.user.service.business.CustomerService;
import io.kryptoblocks.msa.user.service.controller.CreditCardController;
import io.kryptoblocks.msa.user.service.message.CustomerApiMessage;
import io.kryptoblocks.msa.user.service.message.CustomerBusinessMessage;
import io.kryptoblocks.msa.user.service.model.CustomerActivity;
import io.kryptoblocks.msa.user.service.model.CustomerCreditCardDeleteInput;
import io.kryptoblocks.msa.user.service.model.CustomerCreditCardInput;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

 
@RestController 
public class CreditCardControllerImpl implements  CreditCardController  {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardControllerImpl.class);

	 

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
	
	
	@ApiOperation(value = "Get customer credit cards", notes = "Make sure the input is adhering to customer credit card get requirements", nickname = "addBankAccount", tags = {
			"customer", "customer service get credit cards" }, response = CustomerCreditCard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_GET_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_GET_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_GET_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_GET_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.cc}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<CustomerCreditCard>>  getCreditCard(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<List<CustomerCreditCard>> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.CREDIT_CARD_GET_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = new Object();
			if ((user != null)) {
				List<CustomerCreditCard> customerCreditCards = customerService.getCC(user);				
				returnValue = new ResponseEntity<List<CustomerCreditCard>>(customerCreditCards, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	
	@ApiOperation(value = "Add customer credit cards", notes = "Make sure the input is adhering to customer credit card get requirements", nickname = "addBankAccount", tags = {
			"customer", "customer service add credit cards" }, response = CustomerCreditCard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_POST_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_POST_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_POST_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_POST_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.cc}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<CustomerCreditCard>  addCreditCard(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerCreditCard> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.CREDIT_CARD_ADD_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractCustomerCreditCardEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerCreditCard customerCreditCards = customerService.saveOrUpdateCC((CustomerCreditCardInput)input);				
				returnValue = new ResponseEntity<CustomerCreditCard>(customerCreditCards, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	
	@ApiOperation(value = "Update customer credit cards", notes = "Make sure the input is adhering to customer credit card get requirements", nickname = "addBankAccount", tags = {
			"customer", "customer service update credit cards" }, response = CustomerCreditCard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_PATCH_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_PATCH_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_PATCH_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_PATCH_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.cc}", method = RequestMethod.PATCH, produces = "application/json")
	public ResponseEntity<CustomerCreditCard>  updateCreditCard(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerCreditCard> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.CREDIT_CARD_UPDATE_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input = extractCustomerCreditCardEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerCreditCard customerCreditCards = customerService.saveOrUpdateCC((CustomerCreditCardInput)input);				
				returnValue = new ResponseEntity<CustomerCreditCard>(customerCreditCards, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}
	
	@ApiOperation(value = "Delete customer credit cards", notes = "Make sure the input is adhering to customer credit card get requirements", nickname = "addBankAccount", tags = {
			"customer", "customer service update credit cards" }, response = CustomerCreditCard.class)
	@ApiResponses({ @ApiResponse(code = 400, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_DELETE_INPUT_ERROR_USR_MSG),
			@ApiResponse(code = 401, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_DELETE_SECURITY_ERROR_USR_MSG),
			@ApiResponse(code = 403, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_DELETE_FORBIDDEN_ERROR_USR_MSG),
			@ApiResponse(code = 404, message = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_DELETE_ERROR_USR_MSG) })
	@RequestMapping(value = "${customer.service.route.cc}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<CustomerCreditCard>  deleteCreditCard(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException {
		
		ResponseEntity<CustomerCreditCard> returnValue = null;
		
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			customerActivity = CustomerActivity.Type.CREDIT_CARD_REMOVE_ACTIVITY;
			user = jwtTokenUtil.extractUserFromRequest(request);
			input  = extractCustomerCreditCardDeleteEvent(request, methodName);
			if ((user != null) && (input != null)) {
				CustomerCreditCard customerCreditCards = customerService.removeCC((CustomerCreditCardDeleteInput)input);				
				returnValue = new ResponseEntity<CustomerCreditCard>(customerCreditCards, getHeaders(request), HttpStatus.OK);
			} else {
				handleInputException();
			}

		} catch (Exception e) {
			handleBusinessException(e);
		}
		return returnValue;		 
	}

	
	private CustomerCreditCardInput extractCustomerCreditCardEvent(HttpServletRequest request, String methodName) throws ApiException {
		CustomerCreditCardInput returnValue = null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting customer credit card event");
				returnValue = objectMapper.readValue(request.getInputStream(), CustomerCreditCardInput.class);
				LOGGER.debug("Input customer credit card event data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input customer credit card stream is NULL return null customer credit card event object");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract customer credit card event from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting customer credit card event input from request",
					ExceptionHandlerType.INPUT_EXCEPTION_HANDLER.getValue(), methodName);
		}
		return returnValue;
		
	}
	
	private CustomerCreditCardDeleteInput extractCustomerCreditCardDeleteEvent(HttpServletRequest request, String methodName) throws ApiException {
		CustomerCreditCardDeleteInput returnValue = null;
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			InputStream requestBody = request.getInputStream();
			if (requestBody != null) {
				LOGGER.debug("Input body stream is not NULL extracting customer credit card delete event");
				returnValue = objectMapper.readValue(request.getInputStream(), CustomerCreditCardDeleteInput.class);
				LOGGER.debug("Input customer credit card delete event data   ### :  {}", objectMapper.writeValueAsString(returnValue));
			} else {
				LOGGER.debug("Input customer credit card stream is NULL return null customer credit card delete event object");
			}

		} catch (Exception e) {
			LOGGER.debug("Exception in extract customer credit card delete event from the request");
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			throw ExceptionUtil.generateApiException("Exception in extracting customer credit card delete event input from request",
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

		case CREDIT_CARD_GET_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_GET_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_GET_INPUT_ERROR_USR_MSG;
			}			
			break;
		case CREDIT_CARD_ADD_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_POST_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_POST_INPUT_ERROR_USR_MSG;
			}			
			break;
		
		case CREDIT_CARD_UPDATE_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_PATCH_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_PATCH_INPUT_ERROR_USR_MSG;
			}			
			break;
		case CREDIT_CARD_REMOVE_ACTIVITY:
			if (user == null) {
				msg = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_DELETE_SECURITY_ERROR_USR_MSG;
			}
			if (input == null) {
				msg = CustomerApiMessage.CUSTOMER_API_CREDIT_CARD_DELETE_INPUT_ERROR_USR_MSG;
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

		case CREDIT_CARD_GET_ACTIVITY:			 
			userMsg = CustomerBusinessMessage.CREDIT_CARD_GET_ERROR_USR_MSG;			 		
			break;
		case CREDIT_CARD_ADD_ACTIVITY:
			userMsg = CustomerBusinessMessage.CREDIT_CARD_POST_ERROR_USR_MSG;			 	
			break;		
		case CREDIT_CARD_UPDATE_ACTIVITY:
			userMsg = CustomerBusinessMessage.CREDIT_CARD_PATCH_ERROR_USR_MSG;		
			break;
		case CREDIT_CARD_REMOVE_ACTIVITY:
			userMsg = CustomerBusinessMessage.CREDIT_CARD_DELETE_ERROR_USR_MSG;		
			break;		 
		default:
			break;
		}
		throw new ApiException(exceptionMessage, e,userMsg, methodName);
	}

}
