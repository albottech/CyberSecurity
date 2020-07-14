package io.kryptoblocks.msa.user.repository.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.user.repository.key.CustomerDriverLicenseKey;
import io.kryptoblocks.msa.user.repository.name.ConfigName;
import io.kryptoblocks.msa.user.repository.udt.CreditCardAddress;
import io.kryptoblocks.msa.user.repository.udt.DriverLicenseAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ConfigName.USER_DL_TABLE_NAME, schema=ConfigName.USER_PERSISTENCE_SCHEMA_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDriverLicense implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	CustomerDriverLicenseKey key;
	
	@Column
	String nameOnCard;
	
	@Column
	String issueDate;
	
	@Column
	String expiryDate;
	
	@Column
	String createdTime;
	
	@Column
	String updatedTime;
	
	@Column
	boolean verified;
	
	@Column
	boolean purged;
	
	@Column
	boolean active;
	
	@Embedded
    DriverLicenseAddress address;
	
	
}
