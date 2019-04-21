package com.rcsoyer.servicosjuridicos.repository;

import static com.rcsoyer.servicosjuridicos.repository.AdvDgCoordenacaoRestrictions.getRestrictions;

import com.rcsoyer.servicosjuridicos.domain.AdvogadoDgCoordenacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the AdvogadoDgCoordenacao entity.
 */
@Repository
public interface AdvogadoDgCoordenacaoRepository extends JpaRepository<AdvogadoDgCoordenacao, Long>,
                                                         QuerydslPredicateExecutor<AdvogadoDgCoordenacao> {
    
    default Page<AdvogadoDgCoordenacao> query(final AdvogadoDgCoordenacao dgCoordenacao, final Pageable pageable) {
        return findAll(getRestrictions(dgCoordenacao), pageable);
    }
    
}
