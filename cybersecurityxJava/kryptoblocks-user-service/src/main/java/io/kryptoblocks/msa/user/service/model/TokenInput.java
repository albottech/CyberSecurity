package io.kryptoblocks.msa.user.service.model;

import org.springframework.mobile.device.Device;

import lombok.Data;

@Data
public class TokenInput {
	
	String email;
	String token;
	String type;
	String createdTime;
	Device device;

}
