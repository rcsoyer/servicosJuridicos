package com.rcsoyer.servicosjuridicos.repository.coordenacao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;

/**
 * Spring Data JPA repository for the CoordenacaoJuridica entity.
 */
@Repository
public interface CoordenacaoJuridicaRepository extends JpaRepository<CoordenacaoJuridica, Long>,
    QuerydslPredicateExecutor<CoordenacaoJuridica> {

  @Query("select distinct coordenacao_juridica from CoordenacaoJuridica coordenacao_juridica left join fetch coordenacao_juridica.assuntos left join fetch coordenacao_juridica.dgAdvogados")
  List<CoordenacaoJuridica> findAllWithEagerRelationships();

  @Query("select coordenacao_juridica from CoordenacaoJuridica coordenacao_juridica left join fetch coordenacao_juridica.assuntos left join fetch coordenacao_juridica.dgAdvogados where coordenacao_juridica.id =:id")
  CoordenacaoJuridica findOneWithEagerRelationships(@Param("id") Long id);

  default Page<CoordenacaoJuridica> query(CoordenacaoJuridica coordenacao, Pageable pageable) {
    BooleanExpression restrictions = CoordenacaoRestrictions.getRestrictions(coordenacao);
    return findAll(restrictions, pageable);
  }
}
