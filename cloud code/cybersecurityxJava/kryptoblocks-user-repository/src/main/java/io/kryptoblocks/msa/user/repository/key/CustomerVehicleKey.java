package io.kryptoblocks.msa.user.repository.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Embeddable
public class CustomerVehicleKey implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column
	String email;
	
	@Column
	String registrationCountry;
	
	@Column
	String registrationState;
	
	@Column
	String registrationCounty;
	
	@Column
	String registarationTagNumber;
	
	@Column
	String registrationID;
	
	@Column
	String id;
	
}
