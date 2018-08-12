package com.rcsoyer.servicosjuridicos.repository.coordenacao;

import java.util.Optional;
import java.util.function.Function;
import org.apache.commons.collections4.CollectionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.domain.QCoordenacaoJuridica;

/**
 * @author rcsoyer
 */
final class CoordenacaoRestrictions {
  
  private final CoordenacaoJuridica coordenacao;
  private final QCoordenacaoJuridica qCoordenacao;

  private CoordenacaoRestrictions(final CoordenacaoJuridica coordenacao) {
    this.coordenacao = coordenacao;
    this.qCoordenacao = QCoordenacaoJuridica.coordenacaoJuridica;
  }

  static BooleanExpression getRestrictions(final CoordenacaoJuridica coordenacao) {
    return new CoordenacaoRestrictions(coordenacao).defineRestrictions();
  }

  private BooleanExpression defineRestrictions() {
    BooleanExpression nomeRestriction = defineNomeRestriction();
    BooleanExpression siglaRestriction = defineSiglaRestriction();
    BooleanExpression assuntosRestriction = defineAssuntosRestriction();
    return Expressions.allOf(nomeRestriction, siglaRestriction, assuntosRestriction);
  }
  
  private BooleanExpression defineSiglaRestriction() {
    return Optional.ofNullable(coordenacao.getSigla())
                   .map(createSiglaRestriction())
                   .orElse(null);
  }

  private Function<String, BooleanExpression> createSiglaRestriction() {
    return sigla -> qCoordenacao.sigla.likeIgnoreCase("%" + sigla + "%");
  }
  
  private BooleanExpression defineNomeRestriction() {
    return Optional.ofNullable(coordenacao.getNome())
                   .map(createNomeRestriction())
                   .orElse(null);
  }

  private Function<String, BooleanExpression> createNomeRestriction() {
    return nome -> qCoordenacao.nome.likeIgnoreCase("%" + nome + "%");
  }

  private BooleanExpression defineAssuntosRestriction() {
    return Optional.of(coordenacao.getAssuntos())
                   .filter(CollectionUtils::isNotEmpty)
                   .map(qCoordenacao.assuntos.any()::in)
                   .orElse(null);
  }
}