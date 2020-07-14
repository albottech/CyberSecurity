package io.kryptoblocks.msa.security.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.kryptoblocks.msa.security.service.model.Authority;


 
// TODO: Auto-generated Javadoc
/**
 * The Interface AuthorityRepository.
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long>  {
	
	/**
	 * Find by authorityname.
	 *
	 * @param name the name
	 * @return the authority
	 */
	Authority findByAuthorityname(String name);
}
