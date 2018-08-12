package com.rcsoyer.servicosjuridicos.repository.processo;

import java.util.Optional;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;
import com.rcsoyer.servicosjuridicos.domain.QProcessoJudicial;

/**
 * @author rcsoyer
 */
final class ProcessoRestrictions {

  private final ProcessoJudicial processo;
  private final QProcessoJudicial qProcesso;

  private ProcessoRestrictions(final ProcessoJudicial processo) {
    this.processo = processo;
    this.qProcesso = QProcessoJudicial.processoJudicial;
  }

  static BooleanExpression getRestrictions(final ProcessoJudicial processo) {
    return new ProcessoRestrictions(processo).defineRestrictions();
  }

  private BooleanExpression defineRestrictions() {
    BooleanExpression numeroRestriction = defineNumeroRestriction();
    BooleanExpression advogadoRestriction = defineAdvogadoRestriction();
    BooleanExpression assuntoRestriction = defineAssuntoRestriction();
    BooleanExpression modalidadeRestriction = defineModalidadeRestriction();
    return Expressions.allOf(numeroRestriction, advogadoRestriction, assuntoRestriction, modalidadeRestriction);
  }
  
  private BooleanExpression defineNumeroRestriction() {
    return Optional.ofNullable(processo.getNumero())
                   .map(qProcesso.numero::eq)
                   .orElse(null);
  }
  
  private BooleanExpression defineAdvogadoRestriction() {
    return Optional.ofNullable(processo.getAdvogado())
                   .map(qProcesso.advogado::eq)
                   .orElse(null);
  }
  
  private BooleanExpression defineAssuntoRestriction() {
    return Optional.ofNullable(processo.getAssunto())
                   .map(qProcesso.assunto::eq)
                   .orElse(null);
  }
  
  private BooleanExpression defineModalidadeRestriction() {
    return Optional.ofNullable(processo.getModalidade())
                   .map(qProcesso.modalidade::eq)
                   .orElse(null);
  }
}
