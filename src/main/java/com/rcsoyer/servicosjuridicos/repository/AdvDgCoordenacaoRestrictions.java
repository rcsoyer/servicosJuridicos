package com.rcsoyer.servicosjuridicos.repository;

import static java.util.function.Predicate.not;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.rcsoyer.servicosjuridicos.domain.AdvogadoDgCoordenacao;
import com.rcsoyer.servicosjuridicos.domain.QAdvogadoDgCoordenacao;
import java.util.Optional;

final class AdvDgCoordenacaoRestrictions {
    
    private final AdvogadoDgCoordenacao dgCoordenacao;
    private final QAdvogadoDgCoordenacao qDgCoordenacao;
    
    private AdvDgCoordenacaoRestrictions(final AdvogadoDgCoordenacao dgCoordenacao) {
        this.dgCoordenacao = dgCoordenacao;
        this.qDgCoordenacao = QAdvogadoDgCoordenacao.advogadoDgCoordenacao;
    }
    
    static Predicate getRestrictions(final AdvogadoDgCoordenacao dgCoordenacao) {
        return new AdvDgCoordenacaoRestrictions(dgCoordenacao).define();
    }
    
    private BooleanExpression define() {
        return Expressions.allOf(dgPessoalInicioRestriction(), dgPessoalFimRestriction(), dgDuplaRestriction(),
                                 rangeDgCoordenacaoRestriction(), advogadoRestriction(), coordenacaoRestriction());
    }
    
    private BooleanExpression dgPessoalInicioRestriction() {
        return Optional.ofNullable(dgCoordenacao.getDgPessoalInicio())
                       .filter(not(String::isBlank))
                       .map(qDgCoordenacao.dgPessoalInicio::eq)
                       .orElse(null);
    }
    
    private BooleanExpression dgPessoalFimRestriction() {
        return Optional.ofNullable(dgCoordenacao.getDgPessoalFim())
                       .filter(not(String::isBlank))
                       .map(qDgCoordenacao.dgPessoalFim::eq)
                       .orElse(null);
    }
    
    private BooleanExpression dgDuplaRestriction() {
        return Optional.ofNullable(dgCoordenacao.getDgDupla())
                       .filter(not(String::isBlank))
                       .map(qDgCoordenacao.dgDupla::eq)
                       .orElse(null);
    }
    
    private BooleanExpression rangeDgCoordenacaoRestriction() {
        return Optional.ofNullable(dgCoordenacao.getRangeDgCoordenacao())
                       .map(qDgCoordenacao.rangeDgCoordenacao::eq)
                       .orElse(null);
    }
    
    private BooleanExpression advogadoRestriction() {
        return Optional.ofNullable(dgCoordenacao.getAdvogado())
                       .map(qDgCoordenacao.advogado::eq)
                       .orElse(null);
    }
    
    private BooleanExpression coordenacaoRestriction() {
        return Optional.ofNullable(dgCoordenacao.getCoordenacao())
                       .map(qDgCoordenacao.coordenacao::eq)
                       .orElse(null);
    }
}
