package io.kryptoblocks.msa.user.repository.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.kryptoblocks.msa.user.repository.name.ConfigName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = ConfigName.USER_PW_RESET_TABLE_NAME, schema=ConfigName.USER_PERSISTENCE_SCHEMA_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    @Id
    String email;
    String token;
    String createdTime;
    
    
    

}
