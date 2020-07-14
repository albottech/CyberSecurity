package io.kryptoblocks.msa.security.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.kryptoblocks.msa.security.service.model.User;


 
// TODO: Auto-generated Javadoc
/**
 * The Interface UserRepository.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find by username.
     *
     * @param username the username
     * @return the user
     */
    User findByUsername(String username);
}
