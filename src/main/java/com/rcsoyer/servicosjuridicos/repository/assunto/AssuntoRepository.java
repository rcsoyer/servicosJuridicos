package com.rcsoyer.servicosjuridicos.repository.assunto;

import com.querydsl.core.types.Predicate;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Spring Data JPA repository for the Assunto entity.
 */
public interface AssuntoRepository extends JpaRepository<Assunto, Long>, QuerydslPredicateExecutor<Assunto> {
    
    default Page<Assunto> findByAssunto(final Assunto assunto, final Pageable pageable) {
        Function<Assunto, Predicate> expressions = AssuntoRestrictions::getRestrictions;
        return expressions.andThen(predicate -> findAll(predicate, pageable))
                          .apply(assunto);
    }
}
