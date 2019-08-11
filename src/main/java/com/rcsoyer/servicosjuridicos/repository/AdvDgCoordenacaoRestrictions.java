package com.rcsoyer.servicosjuridicos.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.domain.advdgcoordenacao.QAdvogadoDgCoordenacao;
import java.util.Optional;

final class AdvDgCoordenacaoRestrictions {
    
    private final AdvogadoDgCoordenacao dgCoordenacao;
    private final QAdvogadoDgCoordenacao qDgCoordenacao;
    
    private AdvDgCoordenacaoRestrictions(final AdvogadoDgCoordenacao dgCoordenacao) {
        this.dgCoordenacao = dgCoordenacao;
        this.qDgCoordenacao = QAdvogadoDgCoordenacao.advogadoDgCoordenacao;
    }
    
    static Predicate getRestrictions(final AdvogadoDgCoordenacao dgCoordenacao) {
        return new AdvDgCoordenacaoRestrictions(dgCoordenacao)
                   .extractPredicate();
    }
    
    private BooleanExpression extractPredicate() {
        return Expressions.allOf(dgPessoalInicio(), dgPessoalFim(), dgDupla(),
                                 rangeDgCoordenacao(), advogado(), coordenacao());
    }
    
    private BooleanExpression dgPessoalInicio() {
        return Optional.ofNullable(dgCoordenacao.getDgPessoalInicio())
                       .map(qDgCoordenacao.dgPessoalInicio::eq)
                       .orElse(null);
    }
    
    private BooleanExpression dgPessoalFim() {
        return Optional.ofNullable(dgCoordenacao.getDgPessoalFim())
                       .map(qDgCoordenacao.dgPessoalFim::eq)
                       .orElse(null);
    }
    
    private BooleanExpression dgDupla() {
        return Optional.ofNullable(dgCoordenacao.getDgDupla())
                       .map(qDgCoordenacao.dgDupla::eq)
                       .orElse(null);
    }
    
    private BooleanExpression rangeDgCoordenacao() {
        return Optional.ofNullable(dgCoordenacao.getRangeDgCoordenacao())
                       .map(qDgCoordenacao.rangeDgCoordenacao::eq)
                       .orElse(null);
    }
    
    private BooleanExpression advogado() {
        return Optional.ofNullable(dgCoordenacao.getAdvogado())
                       .map(qDgCoordenacao.advogado::eq)
                       .orElse(null);
    }
    
    private BooleanExpression coordenacao() {
        return Optional.ofNullable(dgCoordenacao.getCoordenacao())
                       .map(qDgCoordenacao.coordenacao::eq)
                       .orElse(null);
    }
}
