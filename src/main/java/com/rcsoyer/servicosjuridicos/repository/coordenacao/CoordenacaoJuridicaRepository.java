package com.rcsoyer.servicosjuridicos.repository.coordenacao;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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

  @Query(
      value = "select distinct coordenacao_juridica from CoordenacaoJuridica coordenacao_juridica left join fetch coordenacao_juridica.assuntos",
      countQuery = "select count(distinct coordenacao_juridica) from CoordenacaoJuridica coordenacao_juridica")
  Page<CoordenacaoJuridica> findAllWithEagerRelationships(Pageable pageable);

  @Query("select distinct coordenacao_juridica from CoordenacaoJuridica coordenacao_juridica left join fetch coordenacao_juridica.assuntos")
  List<CoordenacaoJuridica> findAllWithEagerRelationships();

  @Query("select coordenacao_juridica from CoordenacaoJuridica coordenacao_juridica left join fetch coordenacao_juridica.assuntos where coordenacao_juridica.id =:id")
  Optional<CoordenacaoJuridica> findOneWithEagerRelationships(@Param("id") Long id);

  default Function<CoordenacaoJuridica, Page<CoordenacaoJuridica>> query(Pageable pageable) {
    Function<BooleanExpression, Page<CoordenacaoJuridica>> findAll = restrictions -> findAll(restrictions, pageable);
    return CoordenacaoRestrictions.getRestrictions().andThen(findAll);
  }
}
