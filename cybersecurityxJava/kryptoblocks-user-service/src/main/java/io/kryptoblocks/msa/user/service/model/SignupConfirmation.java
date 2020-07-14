package io.kryptoblocks.msa.user.service.model;

import io.kryptoblocks.msa.user.repository.model.User;

import lombok.Data;

@Data
public class SignupConfirmation {
	
	boolean  confirmed;
	String message;
	User user;

}
