package com.rcsoyer.servicosjuridicos.repository;

import static java.util.function.Predicate.not;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import com.rcsoyer.servicosjuridicos.domain.QModalidade;
import java.util.Optional;

/**
 * @author rcsoyer
 */
final class ModalidadeRestrictions {
    
    private final Modalidade modalidade;
    private final QModalidade qModalidade;
    
    private ModalidadeRestrictions(final Modalidade modalidade) {
        this.modalidade = modalidade;
        this.qModalidade = QModalidade.modalidade;
    }
    
    static Predicate getRestrictions(final Modalidade modalidade) {
        return new ModalidadeRestrictions(modalidade)
                   .extractRestrictions();
    }
    
    private BooleanExpression extractRestrictions() {
        return Optional.ofNullable(modalidade.getDescricao())
                       .filter(not(String::isBlank))
                       .map(descricao -> qModalidade.descricao.likeIgnoreCase("%" + descricao + "%"))
                       .orElse(null);
    }
    
}
