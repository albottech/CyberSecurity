package io.kryptoblocks.msa.user.service.ui.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import io.kryptoblocks.msa.user.service.model.CustomerVehicleUpdateInput;

 
public class UserVehicleValidator implements Validator {
	
	@Autowired(required=false)
	private Map<String, String[]> carList;
	
	@Autowired(required=false)
	private Map<String, String> carCategories;
	
	@Autowired(required=false)
	private List<String> carTransmissionTypes;
	
	int minSeatingCapacity = 1;
	int minYearManufacturer = 2005;

	@Override
	public boolean supports(Class<?> clazz) {
		return CustomerVehicleUpdateInput.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors err) {
		CustomerVehicleUpdateInput uve = (CustomerVehicleUpdateInput) target;
		ValidationUtils.rejectIfEmpty(err, "email", "login.email.empty");
		ValidationUtils.rejectIfEmpty(err, "registrationCountry", "vehicle.registrationCountry.empty");
		ValidationUtils.rejectIfEmpty(err, "registrationState", "vehicle.registrationState.empty");
		ValidationUtils.rejectIfEmpty(err, "registrationNumber", "vehicle.registrationNumber.empty");
		ValidationUtils.rejectIfEmpty(err, "manufacturer", "vehicle.manufacturer.empty");
		ValidationUtils.rejectIfEmpty(err, "model", "vehicle.model.empty");
		ValidationUtils.rejectIfEmpty(err, "color", "vehicle.color.empty");
		ValidationUtils.rejectIfEmpty(err, "vehicleCategory", "vehicle.vehicleCategory.empty");
		ValidationUtils.rejectIfEmpty(err, "transmissionType", "vehicle.transmissionType.empty");
		
		if(uve.getManufacturer()!=null && carList.get(uve.getManufacturer())!=null){
			err.rejectValue("manufacturer", "vehicle.manufacturer.unknown");
		}
		
		List<String> carModels = Arrays.asList(carList.get(uve.getManufacturer()));
		
		
		if(uve.getModel()!=null && !carModels.contains(uve.getModel())){
			err.rejectValue("model", "vehicle.model.unknown");
		}
		
		if(uve.getYearOfManaufacture()<minYearManufacturer){
			err.rejectValue("yearOfManaufacture", "vehicle.yearOfManaufacture.min", new Object[]{minYearManufacturer}, "Cars should not older than year "+minYearManufacturer);
		}
		
		if(uve.getSeatingCapacity()<minSeatingCapacity){
			err.rejectValue("seatingCapacity", "vehicle.seatingCapacity.min", new Object[]{minSeatingCapacity}, "Vehicle is so tiny! Minimum seating of "+minSeatingCapacity +" required.");
		}
		
		if(uve.getVehicleCategory()!=null && carCategories.get(uve.getVehicleCategory())!=null){
			err.rejectValue("vehicleCategory", "vehicle.vehicleCategory.unknown");
		}
		
		if(uve.getTransmissionType()!=null && !carTransmissionTypes.contains(uve.getTransmissionType())){
			err.rejectValue("transmissionType", "vehicle.transmissionType.unknown");
		}
	}

}
