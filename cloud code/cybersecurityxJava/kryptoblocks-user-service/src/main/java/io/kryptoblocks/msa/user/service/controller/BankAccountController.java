package io.kryptoblocks.msa.user.service.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.user.repository.model.CustomerBankAccount;

public interface BankAccountController {
	
	public ResponseEntity<List<CustomerBankAccount>>  getBankAccount(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	
	public ResponseEntity<CustomerBankAccount>  addBankAccount(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
		
	public ResponseEntity<CustomerBankAccount>  updateBankAccount(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	
	public ResponseEntity<CustomerBankAccount>  deleteBankAccount(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
		
		

}
