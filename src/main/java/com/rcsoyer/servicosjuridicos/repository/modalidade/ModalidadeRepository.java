package com.rcsoyer.servicosjuridicos.repository.modalidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.rcsoyer.servicosjuridicos.domain.Modalidade;


/**
 * Spring Data JPA repository for the Modalidade entity.
 */
@Repository
public interface ModalidadeRepository
    extends JpaRepository<Modalidade, Long>, QuerydslPredicateExecutor<Modalidade> {

  default Page<Modalidade> query(Modalidade modalidade, Pageable pageable) {
    BooleanExpression restrictions = ModalidadeRestrictions.getRestrictions(modalidade);
    return findAll(restrictions, pageable);
  }
}
