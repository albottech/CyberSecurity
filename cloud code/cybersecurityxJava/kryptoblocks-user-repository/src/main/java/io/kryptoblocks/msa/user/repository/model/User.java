package io.kryptoblocks.msa.user.repository.model;

import java.io.Serializable;
 

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kryptoblocks.msa.user.repository.name.ConfigName;
import io.kryptoblocks.msa.user.repository.udt.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ConfigName.USER_TABLE_NAME, schema=ConfigName.USER_PERSISTENCE_SCHEMA_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	String email;
	 
	@Column
	String firstName;
	
	@Column
	String lastName;
	
	@Column
	String userName;
	
	@Column
	String phoneNumber;
	
	@Column
	@JsonIgnore
	String password;
	
	@Column
	boolean locked;
	
	
	@Column
	boolean userVerificationEmailSend;
	
	@Column
	boolean userStatusVerified;
	
	
	@Column
	boolean customerVerificationEmailSend;
	
	@Column
	boolean becameCustomer;
	
	@Column
	boolean providerVerificationEmailSend;
	
	@Column
	boolean becameProvider;
	
	@Column
	boolean providerVeicleAdded;
	
	@Column
	boolean providerBackgroundVerificationCompleted;
	
	@Column
	boolean providerIsaCompany;
	
	@Column
	boolean providerCompanyVerificationCompleted;
	
	 
	
	@Column
	@JsonIgnore
	String authProviderInfo;
	
	 
	
	/**
	 * 
	 * consumer or provider or both
	 * 
	 * **/
	@Column
	@JsonIgnore
	String serviceRoles;
	
	@Column
	String primaryLocationCity;
	
	@Column
	String currentLocationCity;
		
	@Column
	String createdTime;
	
	@Column
	String updatedTime;
	
	 
	
	String sessionToken;
	
	@Embedded
	Address contactAddress;

}
