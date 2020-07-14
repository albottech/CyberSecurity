package io.kryptoblocks.msa.siem.stream.service.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.siem.stream.service.model.SiemDataProcessStatus;
 
// TODO: Auto-generated Javadoc
/**
 * The Interface SiemDataProcessController.
 */
public interface SiemDataProcessController {

	
	/**
	 * Gets the siem process status.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the siem process status
	 * @throws ApiException the api exception
	 */
	public ResponseEntity<SiemDataProcessStatus> getSiemProcessStatus(HttpServletRequest request, HttpServletResponse response)
			throws ApiException;
	
	/**
	 * Post siem data.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the response entity
	 * @throws ApiException the api exception
	 */
	public ResponseEntity<String> postSiemData(HttpServletRequest request, HttpServletResponse response)
			throws ApiException;
	 
}
