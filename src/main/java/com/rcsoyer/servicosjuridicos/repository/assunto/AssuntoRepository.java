package com.rcsoyer.servicosjuridicos.repository.assunto;

import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.rcsoyer.servicosjuridicos.domain.Assunto;

/**
 * Spring Data JPA repository for the Assunto entity.
 */
@Repository
public interface AssuntoRepository extends JpaRepository<Assunto, Long>, QuerydslPredicateExecutor<Assunto> {

  default Function<Assunto, Page<Assunto>> query(Pageable pageable) {
    Function<BooleanExpression, Page<Assunto>> findAll = restrictions -> findAll(restrictions, pageable);
    return AssuntoRestrictions.getRestrictions().andThen(findAll);
  }
}

