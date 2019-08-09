package com.rcsoyer.servicosjuridicos.repository.processo;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.rcsoyer.servicosjuridicos.domain.ProcessoJudicial;
import com.rcsoyer.servicosjuridicos.domain.QProcessoJudicial;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author rcsoyer
 */
@ToString
@EqualsAndHashCode(of = "processo")
final class ProcessoRestrictions {
    
    private final ProcessoJudicial processo;
    private final QProcessoJudicial qProcesso;
    
    private ProcessoRestrictions(final ProcessoJudicial processo) {
        this.processo = processo;
        this.qProcesso = QProcessoJudicial.processoJudicial;
    }
    
    static Predicate getRestrictions(final ProcessoJudicial processo) {
        return new ProcessoRestrictions(processo)
                   .extractRestrictions();
    }
    
    private BooleanExpression extractRestrictions() {
        return Expressions.allOf(numeroRestriction(), advogadoRestriction(),
                                 assuntoRestriction(), modalidadeRestriction());
    }
    
    private BooleanExpression numeroRestriction() {
        return Optional.ofNullable(processo.getNumero())
                       .map(qProcesso.numero::eq)
                       .orElse(null);
    }
    
    private BooleanExpression advogadoRestriction() {
        return Optional.ofNullable(processo.getAdvogado())
                       .map(qProcesso.advogado::eq)
                       .orElse(null);
    }
    
    private BooleanExpression assuntoRestriction() {
        return Optional.ofNullable(processo.getAssunto())
                       .map(qProcesso.assunto::eq)
                       .orElse(null);
    }
    
    private BooleanExpression modalidadeRestriction() {
        return Optional.ofNullable(processo.getModalidade())
                       .map(qProcesso.modalidade::eq)
                       .orElse(null);
    }
    
}
