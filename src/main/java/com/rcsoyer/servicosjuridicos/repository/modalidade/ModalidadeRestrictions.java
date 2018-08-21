package com.rcsoyer.servicosjuridicos.repository.modalidade;

import java.util.Optional;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.rcsoyer.servicosjuridicos.domain.Modalidade;
import com.rcsoyer.servicosjuridicos.domain.QModalidade;

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

  static BooleanExpression getRestrictions(final Modalidade modalidade) {
    return new ModalidadeRestrictions(modalidade).defineDescricacaotriction();
  }

  private BooleanExpression defineDescricacaotriction() {
    return Optional.ofNullable(modalidade.getDescricao())
                   .filter(StringUtils::isNotEmpty)
                   .map(createDescricaoRestriction())
                   .orElse(null);
  }
  
  private Function<String, BooleanExpression> createDescricaoRestriction() {
    return descricao -> qModalidade.descricao.likeIgnoreCase("%" + descricao + "%");
  }
}
