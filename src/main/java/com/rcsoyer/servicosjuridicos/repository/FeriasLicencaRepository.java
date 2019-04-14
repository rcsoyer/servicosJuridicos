package com.rcsoyer.servicosjuridicos.repository;

import static com.rcsoyer.servicosjuridicos.repository.FeriasLicencaRestrictions.getRestrictions;

import com.rcsoyer.servicosjuridicos.domain.FeriasLicenca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FeriasLicenca entity.
 */
@Repository
public interface FeriasLicencaRepository extends JpaRepository<FeriasLicenca, Long>,
                                                 QuerydslPredicateExecutor<FeriasLicenca> {
    
    default Page<FeriasLicenca> query(final FeriasLicenca feriasLicenca, final Pageable pageable) {
        return findAll(getRestrictions(feriasLicenca), pageable);
    }
}
