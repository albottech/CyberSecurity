package io.kryptoblocks.msa.network.stream.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.network.stream.service.model.NetworkDataProcessStatus;
 
// TODO: Auto-generated Javadoc
/**
 * The Interface NetworkDataProcessController.
 */
public interface NetworkDataProcessController {

	
	/**
	 * Gets the ntwork process status.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the ntwork process status
	 * @throws ApiException the api exception
	 */
	public ResponseEntity<NetworkDataProcessStatus> getNtworkProcessStatus(HttpServletRequest request, HttpServletResponse response)
			throws ApiException;
	 
}
