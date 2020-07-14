package io.kryptoblocks.msa.user.service.business;

import java.util.List;

import org.springframework.mobile.device.Device;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.user.repository.model.PasswordResetToken;
import io.kryptoblocks.msa.user.repository.model.User;
import io.kryptoblocks.msa.user.repository.model.UserLogin;
import io.kryptoblocks.msa.user.repository.model.UserToken;

import io.kryptoblocks.msa.user.service.model.LoginInput;
import io.kryptoblocks.msa.user.service.model.SignUpInput;
import io.kryptoblocks.msa.user.service.model.TokenInput;

public interface UserService {

	User  registerNewUser(SignUpInput event) throws BusinessException;
	
	User  confirmSignup(String signupToken) throws BusinessException;

	User  saveUser(User user) throws BusinessException;;

	User  findUserByEmail(String email) throws BusinessException;;

	void saveUserLogin(UserLogin userLogin) throws BusinessException;;

	User  verifyUser(LoginInput signInEvent) throws BusinessException;;

	UserToken createUserToken(TokenInput tokenInput) throws BusinessException;
	
	UserToken createUserVerificationToken(TokenInput tokenInput) throws BusinessException;

	User  validateVerificationToken(String token) throws BusinessException;;

	PasswordResetToken createPasswordResetToken(String email, Device device) throws BusinessException;;

	User  validatePasswordResetToken(String token) throws BusinessException;;

	User changeUserPassword(String sessionToken, String oldPassword, String newPassword) throws BusinessException;;

	 

}