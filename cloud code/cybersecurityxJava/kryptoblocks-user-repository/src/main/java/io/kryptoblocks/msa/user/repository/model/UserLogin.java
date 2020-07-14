/**
 * 
 */
package io.kryptoblocks.msa.user.repository.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.user.repository.name.ConfigName;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Anoop Manghat
 * @since Mar 31, 2018
 *
 */
@Entity
@Table(name = ConfigName.USER_LOGIN_TABLE_NAME, schema=ConfigName.USER_PERSISTENCE_SCHEMA_NAME)
@Data
@NoArgsConstructor
public class UserLogin implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	String email;	
	 
	
	@Column
	String time;
	
	@Column
	String socialToken;
	
	@Column
	String sessionToken;
		 
	
	/** FB, LI, GO, SY, TW etc **/
	@Column
	String providerType;
	
	 
	
}
