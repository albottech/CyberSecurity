package io.kryptoblocks.msa.security.service.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Authority.
 */
@Entity
@Table(name = "AUTHORITY")
public class Authority {

    /** The id. */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1)
    
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

    /** The authorityname. */
    @Column(name = "NAME", length = 50)
    @NotNull     
    
    /**
     * Gets the authorityname.
     *
     * @return the authorityname
     */
    @Getter 
 /**
  * Sets the authorityname.
  *
  * @param authorityname the new authorityname
  */
 @Setter(AccessLevel.PUBLIC)
    private String authorityname;

    /** The users. */
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    
    /**
     * Gets the users.
     *
     * @return the users
     */
    @Getter 
 /**
  * Sets the users.
  *
  * @param users the new users
  */
 @Setter(AccessLevel.PUBLIC)
    private List<User> users;

    
}