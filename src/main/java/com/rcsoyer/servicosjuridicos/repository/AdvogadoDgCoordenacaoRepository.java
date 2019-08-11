package com.rcsoyer.servicosjuridicos.repository;

import static com.rcsoyer.servicosjuridicos.repository.AdvDgCoordenacaoRestrictions.getRestrictions;

import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.AdvogadoDgCoordenacao;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the AdvogadoDgCoordenacao entity.
 */
@Repository
public interface AdvogadoDgCoordenacaoRepository extends JpaRepository<AdvogadoDgCoordenacao, Long>,
                                                         QuerydslPredicateExecutor<AdvogadoDgCoordenacao> {
    
    int countByDgDuplaEquals(Integer digitoDupla);
    
    default Page<AdvogadoDgCoordenacao> query(final AdvogadoDgCoordenacao dgCoordenacao, final Pageable pageable) {
        return findAll(getRestrictions(dgCoordenacao), pageable);
    }
    
    @Query("FROM AdvogadoDgCoordenacao a WHERE "
               + "(a.rangeDgCoordenacao = 'EXCLUSIVE' AND (a.dgPessoalInicio = ?1 OR a.dgPessoalFim = ?1)) "
               + "OR ((a.rangeDgCoordenacao = 'INCLUSIVE' AND (a.dgPessoalInicio >= ?1 AND a.dgPessoalFim <= ?1))) "
               + "OR a.dgDupla = ?1")
    List<AdvogadoDgCoordenacao> findByAnyDigitoEq(int sextoDigito);
    
}
