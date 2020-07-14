package io.kryptoblocks.msa.user.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.PathVariable;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.user.repository.model.User;

import io.kryptoblocks.msa.user.service.model.ActivationCodeOutput;
import io.kryptoblocks.msa.user.service.model.SignupConfirmation;

public interface SignUpController {
	
	public ResponseEntity<User> signUp(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	public ResponseEntity<SignupConfirmation> confirmSignUp(@PathVariable("conSignupTkn")String conSignupTkn,HttpServletRequest request, HttpServletResponse response, Device device)
			throws ApiException;
	public ResponseEntity<ActivationCodeOutput> sendActivationCode(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	
	public ResponseEntity<String> changeUserPassword(HttpServletRequest request, HttpServletResponse response, Device device)throws ApiException;
	
		
		

}
