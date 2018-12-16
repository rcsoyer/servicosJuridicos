package com.rcsoyer.servicosjuridicos.repository.advogado;

import static java.util.function.Predicate.not;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.QAdvogado;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author rcsoyer
 */
final class AdvogadoRestrictions {
    
    private final Advogado advogado;
    private final QAdvogado qAdvogado;
    
    private AdvogadoRestrictions(final Advogado advogado) {
        this.advogado = advogado;
        this.qAdvogado = QAdvogado.advogado;
    }
    
    static BooleanExpression getRestrictions(final Advogado advogado) {
        return new AdvogadoRestrictions(advogado).defineRestrictions();
    }
    
    private BooleanExpression defineRestrictions() {
        var nomeRestriction = defineNomeRestriction();
        var cpfRestriction = defineCpfRestriction();
        return Expressions.allOf(nomeRestriction, cpfRestriction);
    }
    
    private BooleanExpression defineNomeRestriction() {
        return Optional.ofNullable(advogado.getNome())
                       .filter(not(String::isBlank))
                       .map(createNomeRestriction())
                       .orElse(null);
    }
    
    private Function<String, BooleanExpression> createNomeRestriction() {
        return nome -> qAdvogado.nome.likeIgnoreCase("%" + nome + "%");
    }
    
    private BooleanExpression defineCpfRestriction() {
        return Optional.ofNullable(advogado.getCpf())
                       .filter(not(String::isBlank))
                       .map(qAdvogado.cpf::eq)
                       .orElse(null);
    }
}
