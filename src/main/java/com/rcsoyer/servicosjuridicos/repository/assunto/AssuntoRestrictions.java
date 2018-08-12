package com.rcsoyer.servicosjuridicos.repository.assunto;

import java.util.Optional;
import java.util.function.Function;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.QAssunto;

/**
 * @author rcsoyer
 */
final class AssuntoRestrictions {

  private final Assunto assunto;
  private final QAssunto qAssunto;

  private AssuntoRestrictions(final Assunto assunto) {
    this.assunto = assunto;
    this.qAssunto = QAssunto.assunto;
  }

  static Function<Assunto, BooleanExpression> getRestrictions() {
    return assunto -> new AssuntoRestrictions(assunto).defineRestrictions();
  }

  private BooleanExpression defineRestrictions() {
    BooleanExpression pesoRestriction = definePesoRestriction();
    BooleanExpression ativoRestriction = defineAtivoRestriction();
    BooleanExpression descricacaoRestriction = defineDescricacaoRestriction();
    return Expressions.allOf(descricacaoRestriction, pesoRestriction, ativoRestriction);
  }
  
  private BooleanExpression defineDescricacaoRestriction() {
    return Optional.ofNullable(assunto.getDescricao())
                   .map(createDescricaoRestriction())
                   .orElse(null);
  }

  private Function<String, BooleanExpression> createDescricaoRestriction() {
    return descricacao -> qAssunto.descricao.likeIgnoreCase("%" + descricacao + "%");
  }
  
  private BooleanExpression definePesoRestriction() {
    return Optional.ofNullable(assunto.getPeso())
                   .map(qAssunto.peso::eq)
                   .orElse(null);
  }
  
  private BooleanExpression defineAtivoRestriction() {
    return Optional.ofNullable(assunto.isAtivo())
                   .map(qAssunto.ativo::eq)
                   .orElse(null);
  }
}