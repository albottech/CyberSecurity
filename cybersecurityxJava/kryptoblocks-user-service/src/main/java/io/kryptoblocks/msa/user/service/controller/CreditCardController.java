package io.kryptoblocks.msa.user.service.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.user.repository.model.CustomerCreditCard;

public interface CreditCardController {
	
	public ResponseEntity<List<CustomerCreditCard>>  getCreditCard(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	
	public ResponseEntity<CustomerCreditCard>  addCreditCard(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	
	public ResponseEntity<CustomerCreditCard>  updateCreditCard(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	
	public ResponseEntity<CustomerCreditCard>  deleteCreditCard(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
		
		
		
		

}
