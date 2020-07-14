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
public class CustomerBankAccountKey implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column
	String email;
	
	@Column
	String bankName;
	
	@Column
	String accountNumber;
	
	@Column
	String routingNumber;
	
}
