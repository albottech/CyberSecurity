/**
 * 
 */
package io.kryptoblocks.msa.user.repository.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.kryptoblocks.msa.user.repository.key.UserAddressKey;
import io.kryptoblocks.msa.user.repository.key.UserTokenKey;
import io.kryptoblocks.msa.user.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author insignia
 *
 */
@Entity
@Table(name = ConfigName.USER_VER_TOKEN_TABLE_NAME, schema=ConfigName.USER_PERSISTENCE_SCHEMA_NAME)
@Data
@NoArgsConstructor
public class UserToken implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	UserTokenKey key;
	
	@Column
	String token;
	
	@Column
	String createdTime;
	
	@Column
	String verifiedTime;
	
}
