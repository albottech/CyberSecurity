package io.kryptoblocks.msa.user.repository.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.user.repository.key.CustomerCreditCardKey;
import io.kryptoblocks.msa.user.repository.key.UserAddressKey;
import io.kryptoblocks.msa.user.repository.name.ConfigName;
import io.kryptoblocks.msa.user.repository.udt.CreditCardAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ConfigName.USER_CREDIT_CARD_TABLE_NAME, schema=ConfigName.USER_PERSISTENCE_SCHEMA_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCreditCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	CustomerCreditCardKey key;
	
	@Column
	String nameOnCard;
	
	@Column
	String expDate;
	
	@Column
	String verificationCode;
	
	@Column
	boolean active;
	
	@Column
	String createdTime;
	
	@Column
	String updatedTime;
	
	@Embedded
    CreditCardAddress address;
	
	@Column
	boolean purged;
		
}
