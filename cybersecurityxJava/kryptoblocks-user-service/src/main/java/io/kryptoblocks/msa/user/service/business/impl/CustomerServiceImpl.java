package io.kryptoblocks.msa.user.service.business.impl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.kryptoblocks.msa.common.exception.BusinessException;
import io.kryptoblocks.msa.common.util.DateType;
import io.kryptoblocks.msa.common.util.StringEncryptionUtil;
import io.kryptoblocks.msa.user.repository.impl.UserServiceRepository;
import io.kryptoblocks.msa.user.repository.key.CustomerBankAccountKey;
import io.kryptoblocks.msa.user.repository.key.CustomerCreditCardKey;
import io.kryptoblocks.msa.user.repository.key.CustomerDriverLicenseKey;
import io.kryptoblocks.msa.user.repository.key.CustomerVehicleKey;
import io.kryptoblocks.msa.user.repository.model.CustomerBankAccount;
import io.kryptoblocks.msa.user.repository.model.CustomerCreditCard;
import io.kryptoblocks.msa.user.repository.model.CustomerDriverLicense;
import io.kryptoblocks.msa.user.repository.model.CustomerVehicle;
import io.kryptoblocks.msa.user.repository.udt.CreditCardAddress;
import io.kryptoblocks.msa.user.repository.udt.DriverLicenseAddress;

import io.kryptoblocks.msa.user.service.audit.event.CustomerActivityEventSender;
import io.kryptoblocks.msa.user.service.business.CustomerService;
import io.kryptoblocks.msa.user.service.model.CustomerActivity;
import io.kryptoblocks.msa.user.service.model.CustomerBankAccountDeleteInput;
import io.kryptoblocks.msa.user.service.model.CustomerBankAccountUpdateInput;
import io.kryptoblocks.msa.user.service.model.CustomerCreditCardDeleteInput;
import io.kryptoblocks.msa.user.service.model.CustomerCreditCardInput;
import io.kryptoblocks.msa.user.service.model.CustomerDLUpdateInput;
import io.kryptoblocks.msa.user.service.model.CustomerVehicleUpdateInput;;

/**
 
 *
 */

public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private UserServiceRepository userRepository;

	@Autowired
	StringEncryptionUtil stringEncryptionUtil;

	@Autowired
	CustomerActivityEventSender customerAEventSender;

	private String methodName;

	public CustomerCreditCard saveOrUpdateCC(CustomerCreditCardInput cce) throws BusinessException {
		CustomerCreditCard returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			CustomerActivity customerActivity = new CustomerActivity();
			CustomerCreditCard updatedOrNewCreditCard = null;
			List<CustomerCreditCard> userCreditCards = userRepository.getUserCCards(cce.getEmail());
			if ((userCreditCards != null) && (!userCreditCards.isEmpty())) {
				for (CustomerCreditCard userCreditCard : userCreditCards) {
					String encryptedCreditCardNumber = userCreditCard.getKey().getCreditCardNumber();
					String decryptedCreditCardNumber = stringEncryptionUtil.decrypt(encryptedCreditCardNumber);
					if ((decryptedCreditCardNumber.equalsIgnoreCase(cce.getCreditCardNumber()))
							&& (!userCreditCard.isPurged())) {
						updatedOrNewCreditCard = getUpdatedCreditCard(userCreditCard, cce);
						String oldValue = getActivityDataAsString(userCreditCard);
						String newValue = getActivityDataAsString(updatedOrNewCreditCard);
						customerActivity.setActivity(CustomerActivity.Type.CREDIT_CARD_UPDATE_ACTIVITY.getValue());
						customerActivity.setEmailId(cce.getEmail());
						customerActivity.setNewData(newValue);
						customerActivity.setOldData(oldValue);
						customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
					}
				}
			} else {
				updatedOrNewCreditCard = getUpdatedCreditCard(null, cce);
				String newValue = getActivityDataAsString(updatedOrNewCreditCard);
				customerActivity.setActivity(CustomerActivity.Type.CREDIT_CARD_ADD_ACTIVITY.getValue());
				customerActivity.setEmailId(cce.getEmail());
				customerActivity.setNewData(newValue);
				customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			}
			userRepository.saveCC(updatedOrNewCreditCard);
			customerAEventSender.sendCustomerActivity(customerActivity);
			returnValue = updatedOrNewCreditCard;

		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	@Override
	public List<CustomerCreditCard> getCC(String email) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	public CustomerCreditCard removeCC(CustomerCreditCardDeleteInput ccde) throws BusinessException {
		CustomerCreditCard returnValue = null;

		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			CustomerActivity customerActivity = new CustomerActivity();
			customerActivity.setActivity(CustomerActivity.Type.CREDIT_CARD_REMOVE_ACTIVITY.getValue());
			List<CustomerCreditCard> userCreditCards = userRepository.getUserCCards(ccde.getEmail());
			CustomerCreditCard removableCard = null;
			if ((userCreditCards != null) && (!userCreditCards.isEmpty())) {
				for (CustomerCreditCard userCreditCard : userCreditCards) {
					String encryptedCreditCardNumber = userCreditCard.getKey().getCreditCardNumber();
					String decryptedCreditCardNumber = stringEncryptionUtil.decrypt(encryptedCreditCardNumber);
					if (decryptedCreditCardNumber.equalsIgnoreCase(ccde.getCreditCardNumber())) {
						removableCard = userCreditCard;
						removableCard.setPurged(true);
						removableCard.setActive(false);

						String oldValue = getActivityDataAsString(userCreditCard);
						customerActivity.setActivity(CustomerActivity.Type.CREDIT_CARD_UPDATE_ACTIVITY.getValue());
						customerActivity.setEmailId(ccde.getEmail());
						customerActivity.setOldData(oldValue);
						customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
					}
				}
			}

			if (removableCard == null) {
				String notFoundExceptionMsg = "Unable to find creditcard number: " + ccde.getCreditCardNumber()
						+ " associated with email id:" + ccde.getEmail();
				handleMethodException(null, methodName, notFoundExceptionMsg);
			} else {
				userRepository.saveCC(removableCard);
				customerAEventSender.sendCustomerActivity(customerActivity);
				returnValue = removableCard;
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	public CustomerDriverLicense saveUpdateDL(CustomerDLUpdateInput event) throws BusinessException {

		CustomerDriverLicense returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			CustomerActivity customerActivity = new CustomerActivity();
			CustomerDriverLicense updatedOrNewDL = null;
			List<CustomerDriverLicense> customerDLs = userRepository.getDL(event.getEmail());
			if ((customerDLs != null) && (!customerDLs.isEmpty())) {
				for (CustomerDriverLicense customerDriverLicense : customerDLs) {
					String encryptedDLNumber = customerDriverLicense.getKey().getNumber();
					String decryptedDLNumber = stringEncryptionUtil.decrypt(encryptedDLNumber);
					if ((decryptedDLNumber.equalsIgnoreCase(event.getNumber()))
							&& (!customerDriverLicense.isPurged())) {
						updatedOrNewDL = getUpdatedDL(customerDriverLicense, event);
						String oldValue = getActivityDataAsString(customerDriverLicense);
						String newValue = getActivityDataAsString(updatedOrNewDL);
						customerActivity.setActivity(CustomerActivity.Type.DRIVER_LICENSE_UPDATE_ACTIVITY.getValue());
						customerActivity.setEmailId(event.getEmail());
						customerActivity.setNewData(newValue);
						customerActivity.setOldData(oldValue);
						customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
					}
				}
			} else {
				updatedOrNewDL = getUpdatedDL(null, event);
				String newValue = getActivityDataAsString(updatedOrNewDL);
				customerActivity.setActivity(CustomerActivity.Type.DRIVER_LICENSE_ADD_ACTIVITY.getValue());
				customerActivity.setEmailId(event.getEmail());
				customerActivity.setNewData(newValue);
				customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			}
			userRepository.saveOrUpdateDL(updatedOrNewDL);
			customerAEventSender.sendCustomerActivity(customerActivity);
			returnValue = updatedOrNewDL;

		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	public List<CustomerDriverLicense> getDL(String email) throws BusinessException {
		List<CustomerDriverLicense> returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			CustomerActivity customerActivity = new CustomerActivity();
			customerActivity.setActivity(CustomerActivity.Type.DRIVER_LICENSE_GET_ACTIVITY.getValue());
			customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			customerActivity.setEmailId(email);
			returnValue = userRepository.getDL(email);
			for (CustomerDriverLicense cdl : returnValue) {
				if (cdl.isActive() && cdl.isVerified()) {					 
					String oldValue = getActivityDataAsString(returnValue);
					customerActivity.setOldData(oldValue);
				}
				customerAEventSender.sendCustomerActivity(customerActivity);
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	public CustomerDriverLicense removeDL(CustomerDLUpdateInput event) throws BusinessException {
		CustomerDriverLicense returnValue = null;

		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			CustomerActivity customerActivity = new CustomerActivity();
			customerActivity.setActivity(CustomerActivity.Type.DRIVER_LICENSE_REMOVE_ACTIVITY.getValue());
			List<CustomerDriverLicense> customerDLs = userRepository.getDL(event.getEmail());
			CustomerDriverLicense removableDL = null;
			if ((customerDLs != null) && (!customerDLs.isEmpty())) {
				for (CustomerDriverLicense customerDL : customerDLs) {
					String encryptedDLNumber = customerDL.getKey().getNumber();
					String decryptedCreditCardNumber = stringEncryptionUtil.decrypt(encryptedDLNumber);
					if (decryptedCreditCardNumber.equalsIgnoreCase(event.getNumber())) {
						removableDL = customerDL;
						removableDL.setPurged(true);
						removableDL.setActive(false);
						String oldValue = getActivityDataAsString(customerDL);
						customerActivity.setEmailId(event.getEmail());
						customerActivity.setOldData(oldValue);
						customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
					}
				}
			}

			if (removableDL == null) {
				String notFoundExceptionMsg = "Unable to find driver license number: " + event.getNumber()
						+ " associated with email id:" + event.getEmail();
				handleMethodException(null, methodName, notFoundExceptionMsg);
			} else {
				userRepository.saveOrUpdateDL(removableDL);
				customerAEventSender.sendCustomerActivity(customerActivity);
				returnValue = removableDL;
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}

		return returnValue;
	}

	public CustomerVehicle saveUpdateVehicle(CustomerVehicleUpdateInput event) throws BusinessException {
		CustomerVehicle returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			CustomerActivity customerActivity = new CustomerActivity();
			CustomerVehicle updatedOrNewVehicle = null;
			List<CustomerVehicle> customerVehicles = userRepository.getVehicles(event.getEmail());
			if ((customerVehicles != null) && (!customerVehicles.isEmpty())) {
				for (CustomerVehicle customerVehicle : customerVehicles) {
					String regCountry = customerVehicle.getKey().getRegistrationCountry();
					String regState = customerVehicle.getKey().getRegistrationState();
					String regCounty = customerVehicle.getKey().getRegistrationCounty();
					String regTagNumber = customerVehicle.getKey().getRegistarationTagNumber();
					if ((regCountry.equalsIgnoreCase(event.getRegistrationCountry()))
							&& (regState.equalsIgnoreCase(event.getRegistrationState()))
							&& (regCounty.equalsIgnoreCase(event.getRegistationCounty()))
							&& (regTagNumber.equalsIgnoreCase(event.getRegistrationTagNumber()))) {
						updatedOrNewVehicle = getUpdatedVehicle(customerVehicle, event);
						String oldValue = getActivityDataAsString(customerVehicle);
						String newValue = getActivityDataAsString(updatedOrNewVehicle);
						customerActivity.setActivity(CustomerActivity.Type.VEHICLE_UPDATE_ACTIVITY.getValue());
						customerActivity.setEmailId(event.getEmail());
						customerActivity.setNewData(newValue);
						customerActivity.setOldData(oldValue);
						customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
					}
				}
			} else {
				updatedOrNewVehicle = getUpdatedVehicle(null, event);
				String newValue = getActivityDataAsString(updatedOrNewVehicle);
				customerActivity.setActivity(CustomerActivity.Type.VEHICLE_ADD_ACTIVITY.getValue());
				customerActivity.setEmailId(event.getEmail());
				customerActivity.setNewData(newValue);
				customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			}
			userRepository.saveOrUpdateVehicle(updatedOrNewVehicle);
			customerAEventSender.sendCustomerActivity(customerActivity);
			returnValue = updatedOrNewVehicle;

		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	public List<CustomerVehicle> getVehicles(String email) throws BusinessException {
		List<CustomerVehicle> returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			CustomerActivity customerActivity = new CustomerActivity();
			customerActivity.setActivity(CustomerActivity.Type.VEHICLE_GET_ACTIVITY.getValue());
			customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			customerActivity.setEmailId(email);
			returnValue = userRepository.getVehicles(email);
			customerAEventSender.sendCustomerActivity(customerActivity);
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	public CustomerVehicle removeVehicle(CustomerVehicleUpdateInput event) throws BusinessException {
		CustomerVehicle returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			CustomerActivity customerActivity = new CustomerActivity();
			customerActivity.setActivity(CustomerActivity.Type.VEHICLE_REMOVE_ACTIVITY.getValue());
			List<CustomerVehicle> customerVehicles = userRepository.getVehicles(event.getEmail());
			CustomerVehicle removableVehicle = null;
			if ((customerVehicles != null) && (!customerVehicles.isEmpty())) {
				for (CustomerVehicle customerVehicle : customerVehicles) {
					String encryptedVehicleID = customerVehicle.getKey().getRegistrationID();
					String decryptedVehicleID = stringEncryptionUtil.decrypt(encryptedVehicleID);
					String[] strings = decryptedVehicleID.split("\\|");
					String decryptedRegCountry = strings[0];
					String decryptedRegState = strings[1];
					String decryptedRegCounty = strings[2];
					String decryptedRegEmail = strings[3];
					String decryptedRegTagNumber = strings[4];

					if ((decryptedRegCountry.equalsIgnoreCase(event.getRegistrationCountry())) && (decryptedRegState.equalsIgnoreCase(event.getRegistrationState()))
							&& (decryptedRegCounty.equalsIgnoreCase(event.getRegistationCounty()))
							&& (decryptedRegTagNumber.equalsIgnoreCase(event.getRegistrationTagNumber()))
							&& (decryptedRegEmail.equalsIgnoreCase(event.getEmail()))) {

						removableVehicle = customerVehicle;
						removableVehicle.setPurged(true);
						removableVehicle.setActive(false);
						String oldValue = getActivityDataAsString(customerVehicle);
						customerActivity.setEmailId(event.getEmail());
						customerActivity.setOldData(oldValue);
						customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
					}
				}
			}

			if (removableVehicle == null) {
				String notFoundExceptionMsg = "Unable to find vehicle for  : " + "user: " + event.getEmail() + "with details "
						+ "registration details" + "registered country :" + event.getRegistrationCountry() + "registered state : " + event.getRegistrationState()
						+ "registered county: " + event.getRegistationCounty() + "registered tag number: " + event.getRegistrationTagNumber();
				handleMethodException(null, methodName, notFoundExceptionMsg);
			} else {
				userRepository.saveOrUpdateVehicle(removableVehicle);
				customerAEventSender.sendCustomerActivity(customerActivity);
				returnValue = removableVehicle;
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}

		return returnValue;
	}

	public CustomerBankAccount saveOrUpdateBank(CustomerBankAccountUpdateInput cbe) throws BusinessException {
		CustomerBankAccount returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			CustomerActivity customerActivity = new CustomerActivity();
			CustomerBankAccount updatedOrNewBankAccount = null;
			List<CustomerBankAccount> customerBankAccounts = userRepository.getBankAccounts(cbe.getEmail());
			if ((customerBankAccounts != null) && (!customerBankAccounts.isEmpty())) {
				for (CustomerBankAccount customerBankAccount : customerBankAccounts) {
					String bankName = stringEncryptionUtil.decrypt(customerBankAccount.getKey().getBankName());
					String rountingNumber = stringEncryptionUtil
							.decrypt(customerBankAccount.getKey().getRoutingNumber());
					String accountNumber = stringEncryptionUtil
							.decrypt(customerBankAccount.getKey().getAccountNumber());
					if ((bankName.equalsIgnoreCase(cbe.getBankName()))
							&& (rountingNumber.equalsIgnoreCase(cbe.getRoutingNumber()))
							&& (accountNumber.equalsIgnoreCase(cbe.getAccountNumber()))) {
						updatedOrNewBankAccount = getUpdatedBankAccount(customerBankAccount, cbe);
						String oldValue = getActivityDataAsString(customerBankAccount);
						String newValue = getActivityDataAsString(updatedOrNewBankAccount);
						customerActivity.setActivity(CustomerActivity.Type.BANK_ACCOUNT_UPDATE_ACTIVITY.getValue());
						customerActivity.setEmailId(cbe.getEmail());
						customerActivity.setNewData(newValue);
						customerActivity.setOldData(oldValue);
						customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
					}
				}
			} else {
				updatedOrNewBankAccount = getUpdatedBankAccount(null, cbe);
				String newValue = getActivityDataAsString(updatedOrNewBankAccount);
				customerActivity.setActivity(CustomerActivity.Type.BANK_ACCOUNT_ADD_ACTIVITY.getValue());
				customerActivity.setEmailId(cbe.getEmail());
				customerActivity.setNewData(newValue);
				customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			}
			userRepository.saveBankAccount(updatedOrNewBankAccount);
			customerAEventSender.sendCustomerActivity(customerActivity);
			returnValue = updatedOrNewBankAccount;

		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;

	}

	public List<CustomerBankAccount> getBankAccounts(String email) throws BusinessException {
		List<CustomerBankAccount> returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();

			CustomerActivity customerActivity = new CustomerActivity();
			customerActivity.setActivity(CustomerActivity.Type.BANK_ACCOUNT_GET_ACTIVITY.getValue());
			customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			customerActivity.setEmailId(email);
			returnValue = userRepository.getBankAccounts(email);
			customerAEventSender.sendCustomerActivity(customerActivity);
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}
		return returnValue;
	}

	public CustomerBankAccount removeBankAccount(CustomerBankAccountDeleteInput cbde) throws BusinessException {

		CustomerBankAccount returnValue = null;
		try {
			methodName = new Object() {
			}.getClass().getEnclosingMethod().getName();
			CustomerActivity customerActivity = new CustomerActivity();
			customerActivity.setActivity(CustomerActivity.Type.BANK_ACCOUNT_REMOVE_ACTIVITY.getValue());
			List<CustomerBankAccount> customerBankAccounts = userRepository.getBankAccounts(cbde.getEmail());
			CustomerBankAccount removableBankAccount = null;
			if ((customerBankAccounts != null) && (!customerBankAccounts.isEmpty())) {
				for (CustomerBankAccount customerBankAccount : customerBankAccounts) {
					String dbankName = stringEncryptionUtil.decrypt(customerBankAccount.getKey().getBankName());
					String bankAccountNumber = stringEncryptionUtil
							.decrypt(customerBankAccount.getKey().getAccountNumber());
					String bankRoutingNumber = stringEncryptionUtil
							.decrypt(customerBankAccount.getKey().getRoutingNumber());

					if ((dbankName.equalsIgnoreCase(cbde.getBankName())) && (bankAccountNumber.equalsIgnoreCase(cbde.getAccountNumber()))
							&& (bankRoutingNumber.equalsIgnoreCase(cbde.getRoutingNumber()))) {

						removableBankAccount = customerBankAccount;
						removableBankAccount.setPurged(true);
						removableBankAccount.setActive(false);
						String oldValue = getActivityDataAsString(customerBankAccount);
						customerActivity.setEmailId(cbde.getEmail());
						customerActivity.setOldData(oldValue);
						customerActivity.setTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
					}
				}
			}

			if (removableBankAccount == null) {
				String notFoundExceptionMsg = "Unable to find bank account for  : " + "user: " + cbde.getEmail() + "with details "
						+ "bank account  details" + "bank name :" + cbde.getBankName() + "bank rounting number : "
						+ cbde.getRoutingNumber() + "bank account number: " + cbde.getAccountNumber();
				handleMethodException(null, methodName, notFoundExceptionMsg);
			} else {
				userRepository.saveBankAccount(removableBankAccount);
				customerAEventSender.sendCustomerActivity(customerActivity);
				returnValue = removableBankAccount;
			}
		} catch (Exception e) {
			handleMethodException(e, methodName, null);
		}

		return returnValue;
	}

	private CustomerCreditCard getUpdatedCreditCard(CustomerCreditCard userCreditCard, CustomerCreditCardInput cce)
			throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException,
			IllegalBlockSizeException {
		CustomerCreditCard returnValue = null;
		CreditCardAddress creditCardAddress = new CreditCardAddress();
		creditCardAddress.setAddressLine1(cce.getAddressLine1());
		creditCardAddress.setAddressLine2(cce.getAddressLine2());
		creditCardAddress.setCity(cce.getCity());
		creditCardAddress.setCountry(cce.getCountry());
		creditCardAddress.setState(cce.getState());
		creditCardAddress.setZipCode(cce.getZipCode());
		creditCardAddress.setVerified(cce.isAddressVerified());
		if (userCreditCard != null) {
			returnValue = userCreditCard;
			if (cce.isActive()) {
				returnValue.setActive(true);
			}
			returnValue.setNameOnCard(cce.getNameOnCard());
			returnValue.setExpDate(cce.getExpiryDate());
			returnValue.setVerificationCode(cce.getVerificationCode());
			returnValue.setUpdatedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());

			returnValue.setAddress(creditCardAddress);
		} else {
			returnValue = new CustomerCreditCard();
			CustomerCreditCardKey userCreditCardKey = new CustomerCreditCardKey();
			userCreditCardKey.setCreditCardNumber(stringEncryptionUtil.encrypt(cce.getCreditCardNumber()));
			userCreditCardKey.setCreditCardType(cce.getCreditCardType());
			userCreditCardKey.setEmail(cce.getEmail());
			returnValue.setKey(userCreditCardKey);
			if (cce.isActive()) {
				returnValue.setActive(true);
			}

			returnValue.setNameOnCard(cce.getNameOnCard());
			returnValue.setExpDate(cce.getExpiryDate());
			returnValue.setVerificationCode(cce.getVerificationCode());
			returnValue.setUpdatedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			returnValue.setUpdatedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
		}
		return returnValue;
	}

	private CustomerVehicle getUpdatedVehicle(CustomerVehicle customerVehicle, CustomerVehicleUpdateInput event)
			throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException,
			IllegalBlockSizeException {
		CustomerVehicle returnValue = null;

		if (customerVehicle != null) {
			returnValue = customerVehicle;
		} else {
			returnValue = new CustomerVehicle();
			CustomerVehicleKey customerVehicleKey = new CustomerVehicleKey();
			customerVehicleKey.setRegistrationCountry(event.getRegistrationCountry());
			customerVehicleKey.setRegistrationCounty(event.getRegistationCounty());
			customerVehicleKey.setRegistrationState(event.getRegistrationState());
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(event.getRegistrationCountry());
			stringBuilder.append("|");
			stringBuilder.append(event.getRegistrationState());
			stringBuilder.append("|");
			stringBuilder.append(event.getRegistationCounty());
			stringBuilder.append("|");
			stringBuilder.append(event.getEmail());
			stringBuilder.append("|");
			stringBuilder.append(event.getRegistrationTagNumber());
			customerVehicleKey.setRegistrationID(stringEncryptionUtil.encrypt(stringBuilder.toString()));
		}
		if (event.isActive()) {
			returnValue.setActive(true);
			returnValue.setActiveUser(event.getActiveUserId());
		}
		returnValue.setSeatingCapacity(event.getSeatingCapacity());
		returnValue.setVehicleCategory(event.getVehicleCategory());
		returnValue.setTransmissionType(event.getTransmissionType());
		returnValue.setVehicleImages(event.getVehicleImages());
		returnValue.setMileage(event.getMileage());
		returnValue.setInspectionDetails(event.getInspectionDetails());
		returnValue.setRegistrationDetails(event.getRegistrationDetails());
		returnValue.setVerificationDetails(event.getVerificationDetails());
		returnValue.setVerified(event.isVerified());
		return returnValue;
	}

	private CustomerBankAccount getUpdatedBankAccount(CustomerBankAccount customerBankAccount,
			CustomerBankAccountUpdateInput event) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
			BadPaddingException, IllegalBlockSizeException {
		CustomerBankAccount returnValue = null;

		if (customerBankAccount != null) {
			returnValue = customerBankAccount;
		} else {
			returnValue = new CustomerBankAccount();
			CustomerBankAccountKey bankAccountKey = new CustomerBankAccountKey();
			bankAccountKey.setEmail(event.getEmail());
			bankAccountKey.setBankName(stringEncryptionUtil.encrypt(event.getBankName()));
			bankAccountKey.setRoutingNumber(stringEncryptionUtil.encrypt(event.getRoutingNumber()));
			bankAccountKey.setAccountNumber(stringEncryptionUtil.encrypt(event.getAccountNumber()));
			returnValue.setKey(bankAccountKey);
		}
		if (event.isActive()) {
			returnValue.setActive(true);
		}
		returnValue.setAddressLine1(event.getAddressLine1());
		returnValue.setAddressLine2(event.getAddressLine2());
		returnValue.setBicOrSwift(event.getBicOrSwift());
		returnValue.setCountry(event.getCountry());
		returnValue.setIbanEnabled(event.isIbanEnabled());
		returnValue.setIfscCode(event.getIfscCode());
		returnValue.setMicrCode(event.getMicrCode());
		returnValue.setState(event.getState());
		returnValue.setZipCode(event.getZipCode());
		return returnValue;
	}

	private CustomerDriverLicense getUpdatedDL(CustomerDriverLicense customerDL, CustomerDLUpdateInput event)
			throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException,
			IllegalBlockSizeException {
		CustomerDriverLicense returnValue = null;
		DriverLicenseAddress dlAddress = new DriverLicenseAddress();
		dlAddress.setAddressLine1(event.getAddressLine1());
		dlAddress.setAddressLine2(event.getAddressLine2());
		dlAddress.setCity(event.getCity());
		dlAddress.setCountry(event.getCountry());
		dlAddress.setState(event.getState());
		dlAddress.setZipCode(event.getZipCode());
		dlAddress.setVerified(event.isAddressVerified());
		if (customerDL != null) {
			returnValue = customerDL;
			if (event.isActive()) {
				returnValue.setActive(true);
			}
			returnValue.setNameOnCard(event.getNameOnCard());
			returnValue.setIssueDate(event.getIssueDate());
			returnValue.setExpiryDate(event.getExpiryDate());
			returnValue.setUpdatedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());

			returnValue.setAddress(dlAddress);
		} else {
			returnValue = new CustomerDriverLicense();
			CustomerDriverLicenseKey customerDLKey = new CustomerDriverLicenseKey();
			customerDLKey.setCountry(event.getCountry());
			customerDLKey.setEmail(event.getEmail());
			customerDLKey.setNumber(stringEncryptionUtil.encrypt(event.getNumber()));
			customerDLKey.setState(event.getState());
			returnValue.setNameOnCard(event.getNameOnCard());
			returnValue.setIssueDate(event.getIssueDate());
			returnValue.setExpiryDate(event.getExpiryDate());
			returnValue.setCreatedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			returnValue.setUpdatedTime(DateType.DATE_FORMAT_WITH_NANO_SECONDS.getDateAsString());
			if (event.isActive()) {
				returnValue.setActive(true);
			}
			returnValue.setAddress(dlAddress);
			returnValue.setKey(customerDLKey);
		}
		return returnValue;
	}

	private void handleMethodException(Exception e, String methodName, String message) throws BusinessException {
		String exceptionMsg = ExceptionUtils.getFullStackTrace(e);
		LOGGER.debug("exception in {} business method: exception details are: {}", methodName, exceptionMsg);
		throw new BusinessException(exceptionMsg);
	}

	private String getActivityDataAsString(Object value)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		return objectMapper.writeValueAsString(value);

	}
}
