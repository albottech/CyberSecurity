package io.kryptoblocks.msa.user.service.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;

import io.kryptoblocks.msa.common.exception.ApiException;
import io.kryptoblocks.msa.user.repository.model.CustomerVehicle;

public interface VehicleController {
	
	public ResponseEntity<List<CustomerVehicle>>  getVehicle(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	public ResponseEntity<CustomerVehicle>  addVehicle(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	public ResponseEntity<CustomerVehicle>  updateVehicle(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
	public ResponseEntity<CustomerVehicle>  deleteVehicle(HttpServletRequest request, HttpServletResponse response, Device device) throws ApiException;
		
		
		
		

}
