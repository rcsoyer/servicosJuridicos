package com.rcsoyer.servicosjuridicos.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A Advogado.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "advogado")
@EqualsAndHashCode(of = "id")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@ToString(exclude = {"processos", "feriasLicencas", "dgCoordenacoes"})
public class Advogado implements Serializable {

  private static final long serialVersionUID = 1619909263889107243L;

  @Id
  @SequenceGenerator(name = "sequenceGenerator")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  private Long id;

  @NotNull
  @Size(min = 1, max = 80)
  @Column(name = "nome", length = 80, nullable = false)
  private String nome;

  @NotNull
  @Size(min = 11, max = 11)
  @Column(name = "cpf", length = 11, nullable = false)
  private String cpf;

  @Column(name = "ramal")
  private Integer ramal;

  @JsonIgnore
  @OneToMany(mappedBy = "advogado", fetch = FetchType.LAZY)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<ProcessoJudicial> processos = new HashSet<>(0);

  @JsonIgnore
  @OneToMany(mappedBy = "advogado", fetch = FetchType.LAZY)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<FeriasLicenca> feriasLicencas = new HashSet<>(0);

  @JsonIgnore
  @OneToMany(mappedBy = "advogado", fetch = FetchType.LAZY)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<AdvogadoDgCoordenacao> dgCoordenacoes = new HashSet<>(0);

  public Advogado addProcesso(ProcessoJudicial processoJudicial) {
    processos.add(processoJudicial);
    processoJudicial.setAdvogado(this);
    return this;
  }

  public Advogado removeProcesso(ProcessoJudicial processoJudicial) {
    processos.remove(processoJudicial);
    processoJudicial.setAdvogado(null);
    return this;
  }

  public Advogado addFeriasLicenca(FeriasLicenca feriasLicenca) {
    feriasLicencas.add(feriasLicenca);
    feriasLicenca.setAdvogado(this);
    return this;
  }

  public Advogado removeFeriasLicenca(FeriasLicenca feriasLicenca) {
    feriasLicencas.remove(feriasLicenca);
    feriasLicenca.setAdvogado(null);
    return this;
  }

  public Advogado addDgCoordenacao(AdvogadoDgCoordenacao advogadoDgCoordenacao) {
    dgCoordenacoes.add(advogadoDgCoordenacao);
    advogadoDgCoordenacao.setAdvogado(this);
    return this;
  }

  public Advogado removeDgCoordenacao(AdvogadoDgCoordenacao advogadoDgCoordenacao) {
    dgCoordenacoes.remove(advogadoDgCoordenacao);
    advogadoDgCoordenacao.setAdvogado(null);
    return this;
  }
}
