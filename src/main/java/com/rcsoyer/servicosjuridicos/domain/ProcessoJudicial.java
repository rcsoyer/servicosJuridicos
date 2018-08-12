package com.rcsoyer.servicosjuridicos.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A ProcessoJudicial.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Table(name = "processo_judicial")
@ToString(exclude = {"assunto", "modalidade", "advogado"})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessoJudicial implements Serializable {

  private static final long serialVersionUID = 5978891789222560660L;

  @Id
  @SequenceGenerator(name = "sequenceGenerator")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  private Long id;

  @NotNull
  @Size(min = 20, max = 20)
  @Column(name = "numero", length = 20, nullable = false)
  private String numero;

  @NotNull
  @Column(name = "prazo_final", nullable = false)
  private LocalDate prazoFinal;

  @NotNull
  @Column(name = "dt_atribuicao", nullable = false)
  private LocalDate dtAtribuicao;

  @Column(name = "dt_inicio")
  private LocalDate dtInicio;

  @Column(name = "dt_conclusao")
  private LocalDate dtConclusao;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Assunto assunto;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Modalidade modalidade;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Advogado advogado;

  public ProcessoJudicial setDtAtribuicao(LocalDate dtAtribuicao) {
    this.dtAtribuicao = Optional.ofNullable(dtAtribuicao).map(dt -> dt).orElseGet(LocalDate::now);
    return this;
  }

  public boolean foiDistribuido() {
    return Objects.nonNull(id);
  }

  public boolean naoFoiAtribuidoAdvogado() {
    return Objects.isNull(advogado);
  }

  public void verificarTrocouAdvogado(final ProcessoJudicial consultadoBD) {
    Optional.of(this)
            .filter(advogadoDiferente(consultadoBD))
            .ifPresent(setNewDtAtribuicao());
  }

  private Predicate<ProcessoJudicial> advogadoDiferente(final ProcessoJudicial consultadoBD) {
    return processo -> !consultadoBD.getAdvogado().equals(processo.getAdvogado());
  }

  private Consumer<ProcessoJudicial> setNewDtAtribuicao() {
    return processo -> processo.setDtAtribuicao(LocalDate.now());
  }
}
