package com.rcsoyer.servicosjuridicos.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.rcsoyer.servicosjuridicos.domain.Assunto;
import com.rcsoyer.servicosjuridicos.domain.QAssunto;
import java.util.Optional;
import java.util.function.Function;

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
    
    static Predicate getRestrictions(final Assunto assunto) {
        return new AssuntoRestrictions(assunto).get();
    }
    
    private BooleanExpression get() {
        return Expressions.allOf(descricacaoRestriction(), pesoRestriction(), ativoRestriction());
    }
    
    private BooleanExpression descricacaoRestriction() {
        Function<String, BooleanExpression> createExpression =
            descricacao -> qAssunto.descricao.likeIgnoreCase("%" + descricacao + "%");
        return Optional.ofNullable(assunto.getDescricao())
                       .map(createExpression)
                       .orElse(null);
    }
    
    private BooleanExpression pesoRestriction() {
        return Optional.ofNullable(assunto.getPeso())
                       .map(qAssunto.peso::eq)
                       .orElse(null);
    }
    
    private BooleanExpression ativoRestriction() {
        return Optional.ofNullable(assunto.getAtivo())
                       .map(qAssunto.ativo::eq)
                       .orElse(null);
    }
}
