package io.kryptoblocks.msa.udp.listner.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.udp.listner.service.model.UDPListnerStatus;
 
public interface UDPListnerController {

	
	public ResponseEntity<UDPListnerStatus> getNtworkProcessStatus(HttpServletRequest request, HttpServletResponse response)
			throws ApiException;
	 
}
