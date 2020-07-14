package io.kryptoblocks.msa.user.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.user.repository.model.User;

public interface AuthController {

	
	public ResponseEntity<User> signinLocal(HttpServletRequest request, HttpServletResponse response, Device device)
			throws ApiException;
	public ResponseEntity<User> signinSocial(HttpServletRequest request, HttpServletResponse response,
			Device device) throws ApiException;
	
}
