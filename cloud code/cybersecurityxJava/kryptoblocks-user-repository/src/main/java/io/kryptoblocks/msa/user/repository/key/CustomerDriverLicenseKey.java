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
public class CustomerDriverLicenseKey implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column
	String email;
	
	String number;
	
	String country;
	
	String state;

}
