package io.kryptoblocks.msa.security.service.model;

import java.util.Date;
import java.util.List;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

 
// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
@Entity
@Table(name = "USER")
public class User {

    /** The id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    @Getter 
 /**
  * Sets the id.
  *
  * @param id the new id
  */
 @Setter(AccessLevel.PUBLIC)
    private Long id;

    /** The username. */
    @Column(name = "USERNAME", length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    
    /**
     * Gets the username.
     *
     * @return the username
     */
    @Getter 
 /**
  * Sets the username.
  *
  * @param username the new username
  */
 @Setter(AccessLevel.PUBLIC)
    private String username;

    /** The authorities. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_AUTHORITY",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")})
    
    /**
     * Gets the authorities.
     *
     * @return the authorities
     */
    @Getter 
 /**
  * Sets the authorities.
  *
  * @param authorities the new authorities
  */
 @Setter(AccessLevel.PUBLIC)
    private List<Authority> authorities;
    
    /** The account enabled. */
    @Transient
    
    /**
     * Checks if is account enabled.
     *
     * @return true, if is account enabled
     */
    @Getter 
 /**
  * Sets the account enabled.
  *
  * @param accountEnabled the new account enabled
  */
 @Setter(AccessLevel.PUBLIC)
    public boolean accountEnabled;
    
    /** The account non expired. */
    @Transient
    
    /**
     * Checks if is account non expired.
     *
     * @return true, if is account non expired
     */
    @Getter 
 /**
  * Sets the account non expired.
  *
  * @param accountNonExpired the new account non expired
  */
 @Setter(AccessLevel.PUBLIC)
    public boolean accountNonExpired;
    
    /** The account non locked. */
    @Transient
    
    /**
     * Checks if is account non locked.
     *
     * @return true, if is account non locked
     */
    @Getter 
 /**
  * Sets the account non locked.
  *
  * @param accountNonLocked the new account non locked
  */
 @Setter(AccessLevel.PUBLIC)
    public boolean accountNonLocked;
    
    /** The credential non expired. */
    @Transient
    
    /**
     * Checks if is credential non expired.
     *
     * @return true, if is credential non expired
     */
    @Getter 
 /**
  * Sets the credential non expired.
  *
  * @param credentialNonExpired the new credential non expired
  */
 @Setter(AccessLevel.PUBLIC)
    public boolean credentialNonExpired;
    
    
    /*

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
    */


   
}