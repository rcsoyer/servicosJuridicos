package com.rcsoyer.servicosjuridicos.repository;

import static com.rcsoyer.servicosjuridicos.repository.ModalidadeRestrictions.getRestrictions;

import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Modalidade entity.
 */
@Repository
public interface ModalidadeRepository extends JpaRepository<Modalidade, Long>, QuerydslPredicateExecutor<Modalidade> {
    
    /**
     * Query the database about any modalidades that matches the given search params
     */
    default Page<Modalidade> query(final Modalidade modalidade, final Pageable pageable) {
        return findAll(getRestrictions(modalidade), pageable);
    }
}
