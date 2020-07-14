package io.kryptoblocks.msa.user.service.model;

import lombok.Data;

@Data
public class ActivationCodeOutput {
	
	String userName;
	String email;
	String type;
	String msg;
	
}
