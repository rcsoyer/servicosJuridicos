package com.rcsoyer.servicosjuridicos.repository;

import com.querydsl.core.types.Predicate;
import com.rcsoyer.servicosjuridicos.domain.FeriasLicenca;
import java.util.function.Function;
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
        Function<FeriasLicenca, Predicate> getPredicate = FeriasLicencaRestrictions::getRestrictions;
        Function<Predicate, Page<FeriasLicenca>> findByPredicate = predicate -> findAll(predicate, pageable);
        return getPredicate.andThen(findByPredicate)
                           .apply(feriasLicenca);
    }
}
