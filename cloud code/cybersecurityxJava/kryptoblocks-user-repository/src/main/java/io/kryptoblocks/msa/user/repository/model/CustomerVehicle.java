package io.kryptoblocks.msa.user.repository.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
 
import javax.persistence.Table;

import io.kryptoblocks.msa.user.repository.key.CustomerVehicleKey;
import io.kryptoblocks.msa.user.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ConfigName.USER_VEHICLE_TABLE_NAME, schema=ConfigName.USER_PERSISTENCE_SCHEMA_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVehicle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	CustomerVehicleKey key;
	
	@Column
	String manufacturer;
	
	@Column
	String model;
	
	@Column
	int yearOfManaufacture;
	@Column
	String color;
	
	@Column
	int seatingCapacity;
	
	@Column
	String vehicleCategory;
	
	@Column
	String transmissionType;// automatic/manual
	
	@Column
	List<String> vehicleImages;
	
	@Column
	int mileage;
	
	@Column
	String inspectionDetails;
	
	@Column
	String registrationDetails;
	
	@Column
	String verificationDetails;
	
	@Column
	boolean verified;
	
	@Column
	boolean active;
	
	@Column
	String createdTime;
	
	@Column
	String updatedTime;
	
	@Column
	boolean purged;
	
		
	@Column
	String activeUser;
}
