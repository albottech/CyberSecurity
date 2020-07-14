package io.kryptoblocks.msa.user.service.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.user.repository.model.CustomerDriverLicense;

public interface DlController {
	
	public ResponseEntity<List<CustomerDriverLicense>>  getDriverLicense(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	public ResponseEntity<CustomerDriverLicense>  addDriverLicense(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	public ResponseEntity<CustomerDriverLicense>  updateDriverLicense(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	public ResponseEntity<CustomerDriverLicense>  deleteDriverLicense(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
		
		
		
		

}
