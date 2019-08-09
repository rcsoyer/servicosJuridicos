package com.rcsoyer.servicosjuridicos.repository.processo;

import static com.rcsoyer.servicosjuridicos.repository.processo.ProcessoRestrictions.getRestrictions;

import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProcessoJudicial entity.
 */
@Repository
public interface ProcessoJudicialRepository
    extends JpaRepository<ProcessoJudicial, Long>, QuerydslPredicateExecutor<ProcessoJudicial> {
    
    default Page<ProcessoJudicial> query(final ProcessoJudicial searchParams, final Pageable pageable) {
        return findAll(getRestrictions(searchParams), pageable);
    }
    
}
