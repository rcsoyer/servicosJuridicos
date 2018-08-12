package com.rcsoyer.servicosjuridicos.repository.advogado;

import java.util.Optional;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.rcsoyer.servicosjuridicos.domain.Advogado;
import com.rcsoyer.servicosjuridicos.domain.QAdvogado;

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
    BooleanExpression nomeRestriction = defineNomeRestriction();
    BooleanExpression cpfRestriction = defineCpfRestriction();
    return Expressions.allOf(nomeRestriction, cpfRestriction);
  }

  private BooleanExpression defineNomeRestriction() {
    return Optional.of(advogado.getNome())
                   .filter(StringUtils::isNotEmpty)
                   .map(createNomeRestriction())
                   .orElse(null);
  }

  private Function<String, BooleanExpression> createNomeRestriction() {
    return nome -> qAdvogado.nome.likeIgnoreCase("%" + nome + "%");
  }
  
  private BooleanExpression defineCpfRestriction() {
    return Optional.of(advogado.getCpf())
                   .filter(StringUtils::isNotEmpty)
                   .map(qAdvogado.cpf::eq)
                   .orElse(null);
  }
}