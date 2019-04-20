package com.rcsoyer.servicosjuridicos.repository;

import static com.rcsoyer.servicosjuridicos.repository.AdvogadoRestrictions.getRestrictions;

import com.rcsoyer.servicosjuridicos.domain.Advogado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Advogado entity.
 */
@Repository
public interface AdvogadoRepository extends JpaRepository<Advogado, Long>, QuerydslPredicateExecutor<Advogado> {
    
    default Page<Advogado> query(final Advogado advogado, final Pageable pageable) {
        return findAll(getRestrictions(advogado), pageable);
    }
}
