package com.rcsoyer.servicosjuridicos.repository;

import static com.rcsoyer.servicosjuridicos.repository.CoordenacaoRestrictions.getRestrictions;

import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CoordenacaoJuridica entity.
 */
@Repository
public interface CoordenacaoJuridicaRepository extends JpaRepository<CoordenacaoJuridica, Long>,
                                                       QuerydslPredicateExecutor<CoordenacaoJuridica> {
    
    default Page<CoordenacaoJuridica> query(final CoordenacaoJuridica coordenacao, final Pageable pageable) {
        return findAll(getRestrictions(coordenacao), pageable);
    }
}
