package com.rcsoyer.servicosjuridicos.repository.processo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;


/**
 * Spring Data JPA repository for the ProcessoJudicial entity.
 */
@Repository
public interface ProcessoJudicialRepository
    extends JpaRepository<ProcessoJudicial, Long>, QuerydslPredicateExecutor<ProcessoJudicial> {

  default Page<ProcessoJudicial> query(ProcessoJudicial processo, Pageable pageable) {
    BooleanExpression restrictions = ProcessoRestrictions.getRestrictions(processo);
    return findAll(restrictions, pageable);
  }
}
