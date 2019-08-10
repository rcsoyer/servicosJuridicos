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
                                 assuntoRestriction(), modalidadeRestriction(),
                                 dtAtribuicaoRestriction(), dtInicioRestriction(),
                                 dtConclusaoRestriction(), prazoFinalRestriction());
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
    
    private BooleanExpression prazoFinalRestriction() {
        return Optional.ofNullable(processo.getPrazoFinal())
                       .map(qProcesso.prazoFinal::eq)
                       .orElse(null);
    }
    
    private BooleanExpression dtAtribuicaoRestriction() {
        return Optional.ofNullable(processo.getDtAtribuicao())
                       .map(qProcesso.dtAtribuicao::eq)
                       .orElse(null);
    }
    
    private BooleanExpression dtInicioRestriction() {
        return Optional.ofNullable(processo.getDtInicio())
                       .map(qProcesso.dtInicio::eq)
                       .orElse(null);
    }
    
    private BooleanExpression dtConclusaoRestriction() {
        return Optional.ofNullable(processo.getDtConclusao())
                       .map(qProcesso.dtConclusao::eq)
                       .orElse(null);
    }
    
}
