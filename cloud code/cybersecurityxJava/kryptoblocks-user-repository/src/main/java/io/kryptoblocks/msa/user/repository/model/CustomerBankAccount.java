package io.kryptoblocks.msa.user.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.user.repository.key.CustomerBankAccountKey;
import io.kryptoblocks.msa.user.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ConfigName.USER_BABK_ACCOUNT_TABLE_NAME, schema=ConfigName.USER_PERSISTENCE_SCHEMA_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBankAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	CustomerBankAccountKey key;
	
	@Column	
	boolean ibanEnabled;
	
	@Column
	String bicOrSwift;
	
	@Column
	String ifscCode;
	
	@Column
	String micrCode;
	
	@Column
	String country;
	
	@Column
	String state;
		
	@Column
	String addressLine1;
	
	@Column
	String addressLine2;
			
	@Column
	String zipCode;
	
	@Column
	boolean active;
	
	@Column
	boolean purged;
	
	
	
}
