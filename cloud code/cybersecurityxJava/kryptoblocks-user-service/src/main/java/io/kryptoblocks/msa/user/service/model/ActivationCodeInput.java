package io.kryptoblocks.msa.user.service.model;

import lombok.Data;

@Data
public class ActivationCodeInput {
	
	String userName;
	String email;
	String type;
	
}
