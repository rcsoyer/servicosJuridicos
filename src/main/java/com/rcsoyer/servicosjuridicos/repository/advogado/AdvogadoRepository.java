package com.rcsoyer.servicosjuridicos.repository.advogado;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.rcsoyer.servicosjuridicos.domain.Advogado;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Advogado entity.
 */
@Repository
public interface AdvogadoRepository
    extends JpaRepository<Advogado, Long>, QuerydslPredicateExecutor<Advogado> {
    
    default Page<Advogado> query(final Advogado model, final Pageable pageable) {
        Function<Advogado, BooleanExpression> getRestrictions = AdvogadoRestrictions::getRestrictions;
        Function<BooleanExpression, Page<Advogado>> findAll =
            restrictions -> findAll(restrictions, pageable);
        return getRestrictions.andThen(findAll).apply(model);
    }
}
