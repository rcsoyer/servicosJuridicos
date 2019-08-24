package com.rcsoyer.servicosjuridicos.repository;

import com.rcsoyer.servicosjuridicos.domain.Authority;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    
    Set<Authority> findByNameIn(Set<String> names);
    
}
