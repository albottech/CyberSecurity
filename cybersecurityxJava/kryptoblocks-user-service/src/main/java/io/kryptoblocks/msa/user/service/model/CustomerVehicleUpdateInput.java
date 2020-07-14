/**
 * 
 */
package io.kryptoblocks.msa.user.service.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Anoop Manghat
 * @since May 20, 2018
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVehicleUpdateInput {
	String email;
	String registrationCountry;
	String registrationState;
	String registationCounty;
	String registrationTagNumber;
	String manufacturer;
	String model;
	int yearOfManaufacture;
	String color;
	int seatingCapacity;
	String vehicleCategory;
	String transmissionType;// automatic/manual
	List<String> vehicleImages;
	int mileage;
	String inspectionDetails;
	String registrationDetails;
	String verificationDetails;
	boolean active;
	boolean verified;
	String activeUserId;
}
