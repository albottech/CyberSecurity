package io.kryptoblocks.msa.user.service.business;

 
import io.kryptoblocks.msa.user.repository.model.CustomerBankAccount;
import io.kryptoblocks.msa.user.repository.model.CustomerCreditCard;
import io.kryptoblocks.msa.user.repository.model.CustomerDriverLicense;
import io.kryptoblocks.msa.user.repository.model.CustomerVehicle;

import io.kryptoblocks.msa.user.service.model.CustomerBankAccountDeleteInput;
import io.kryptoblocks.msa.user.service.model.CustomerBankAccountUpdateInput;
import io.kryptoblocks.msa.user.service.model.CustomerCreditCardDeleteInput;
import io.kryptoblocks.msa.user.service.model.CustomerCreditCardInput;
import io.kryptoblocks.msa.user.service.model.CustomerDLUpdateInput;
import io.kryptoblocks.msa.user.service.model.CustomerVehicleUpdateInput;

import java.util.List;

import io.kryptoblocks.msa.common.exception.BusinessException;

public interface CustomerService {
	
	public CustomerCreditCard saveOrUpdateCC(CustomerCreditCardInput cce) throws BusinessException;
	
	public List<CustomerCreditCard> getCC(String email) throws BusinessException;
	
	public CustomerCreditCard removeCC(CustomerCreditCardDeleteInput ccde) throws BusinessException;
	
	public CustomerDriverLicense saveUpdateDL(CustomerDLUpdateInput event) throws BusinessException;
	
	public List<CustomerDriverLicense> getDL(String email) throws BusinessException;
	
	public CustomerDriverLicense removeDL(CustomerDLUpdateInput event) throws BusinessException ;
	
	public CustomerVehicle saveUpdateVehicle(CustomerVehicleUpdateInput event) throws BusinessException;
	
	public List<CustomerVehicle> getVehicles(String email) throws BusinessException;
	
	public CustomerVehicle removeVehicle(CustomerVehicleUpdateInput event) throws BusinessException;
		
	public CustomerBankAccount saveOrUpdateBank(CustomerBankAccountUpdateInput ube) throws BusinessException ;
	
	public List<CustomerBankAccount> getBankAccounts(String email) throws BusinessException;
	
	public CustomerBankAccount removeBankAccount(CustomerBankAccountDeleteInput cbde) throws BusinessException;
}
