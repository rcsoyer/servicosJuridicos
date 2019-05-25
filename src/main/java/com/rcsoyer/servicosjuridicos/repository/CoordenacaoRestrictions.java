package com.rcsoyer.servicosjuridicos.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.rcsoyer.servicosjuridicos.domain.CoordenacaoJuridica;
import com.rcsoyer.servicosjuridicos.domain.QCoordenacaoJuridica;
import java.util.Optional;
import org.apache.commons.collections4.CollectionUtils;

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
    
    static Predicate getRestrictions(final CoordenacaoJuridica coordenacao) {
        return new CoordenacaoRestrictions(coordenacao)
                   .extractPredicate();
    }
    
    private BooleanExpression extractPredicate() {
        return Expressions.allOf(nomeRestriction(), siglaRestriction(), assuntosRestriction());
    }
    
    private BooleanExpression siglaRestriction() {
        return Optional.ofNullable(coordenacao.getSigla())
                       .map(sigla -> qCoordenacao.sigla.likeIgnoreCase("%" + sigla + "%"))
                       .orElse(null);
    }
    
    private BooleanExpression nomeRestriction() {
        return Optional.ofNullable(coordenacao.getNome())
                       .map(nome -> qCoordenacao.nome.likeIgnoreCase("%" + nome + "%"))
                       .orElse(null);
    }
    
    private BooleanExpression assuntosRestriction() {
        return Optional.ofNullable(coordenacao.getAssuntos())
                       .filter(CollectionUtils::isNotEmpty)
                       .map(qCoordenacao.assuntos.any()::in)
                       .orElse(null);
    }
}
