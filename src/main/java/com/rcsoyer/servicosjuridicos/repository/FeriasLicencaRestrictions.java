package com.rcsoyer.servicosjuridicos.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.rcsoyer.servicosjuridicos.domain.FeriasLicenca;
import com.rcsoyer.servicosjuridicos.domain.feriaslicenca.QFeriasLicenca;
import java.util.Optional;

final class FeriasLicencaRestrictions {
    
    private final FeriasLicenca feriasLicenca;
    private final QFeriasLicenca qFeriasLicenca;
    
    private FeriasLicencaRestrictions(final FeriasLicenca feriasLicenca) {
        this.feriasLicenca = feriasLicenca;
        this.qFeriasLicenca = QFeriasLicenca.feriasLicenca;
    }
    
    static Predicate getRestrictions(final FeriasLicenca feriasLicenca) {
        return new FeriasLicencaRestrictions(feriasLicenca)
                   .extractPredicate();
    }
    
    private BooleanExpression extractPredicate() {
        return Expressions.allOf(dtInicioRestriction(), dtFimRestriction(),
                                 feriasLicencaTipoRestriction(), advogadoRestriction());
    }
    
    private BooleanExpression dtInicioRestriction() {
        return Optional.ofNullable(feriasLicenca.getDtInicio())
                       .map(qFeriasLicenca.dtInicio::eq)
                       .orElse(null);
    }
    
    private BooleanExpression dtFimRestriction() {
        return Optional.ofNullable(feriasLicenca.getDtFim())
                       .map(qFeriasLicenca.dtFim::eq)
                       .orElse(null);
    }
    
    private BooleanExpression feriasLicencaTipoRestriction() {
        return Optional.ofNullable(feriasLicenca.getTipo())
                       .map(qFeriasLicenca.tipo::eq)
                       .orElse(null);
    }
    
    private BooleanExpression advogadoRestriction() {
        return Optional.ofNullable(feriasLicenca.getAdvogado())
                       .map(qFeriasLicenca.advogado::eq)
                       .orElse(null);
    }
    
}
