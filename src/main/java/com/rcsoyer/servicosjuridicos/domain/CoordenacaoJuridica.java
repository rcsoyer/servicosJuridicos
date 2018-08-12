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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A CoordenacaoJuridica.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Table(name = "coordenacao_juridica")
@ToString(exclude = {"dgAdvogados", "assuntos"})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CoordenacaoJuridica implements Serializable {

  private static final long serialVersionUID = 7821224258437788453L;

  @Id
  @SequenceGenerator(name = "sequenceGenerator")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  private Long id;

  @NotBlank
  @Size(min = 1, max = 6)
  @Column(name = "sigla", length = 6, nullable = false)
  private String sigla;

  @NotBlank
  @Size(min = 1, max = 50)
  @Column(name = "nome", length = 50, nullable = false)
  private String nome;

  @Size(min = 3, max = 3)
  @Setter(value = AccessLevel.NONE)
  @Column(name = "centena", length = 3)
  private String centena;

  @JsonIgnore
  @OneToMany(mappedBy = "coordenacao", fetch = FetchType.LAZY)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private Set<AdvogadoDgCoordenacao> dgAdvogados = new HashSet<>(0);

  @NotNull
  @ManyToMany(fetch = FetchType.LAZY)
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  @JoinTable(name = "coordenacao_juridica_assunto",
      joinColumns = @JoinColumn(name = "coordenacao_juridicas_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "assuntos_id", referencedColumnName = "id"))
  private Set<Assunto> assuntos = new HashSet<>(0);

  public CoordenacaoJuridica setCentena(String centena) {
    this.centena = StringUtils.defaultIfEmpty(centena, null);
    return this;
  }

  public CoordenacaoJuridica addDgAdvogado(AdvogadoDgCoordenacao advogadoDgCoordenacao) {
    dgAdvogados.add(advogadoDgCoordenacao);
    advogadoDgCoordenacao.setCoordenacao(this);
    return this;
  }

  public CoordenacaoJuridica removeDgAdvogado(AdvogadoDgCoordenacao advogadoDgCoordenacao) {
    dgAdvogados.remove(advogadoDgCoordenacao);
    advogadoDgCoordenacao.setCoordenacao(null);
    return this;
  }

  public CoordenacaoJuridica addAssunto(Assunto assunto) {
    assuntos.add(assunto);
    return this;
  }

  public CoordenacaoJuridica removeAssunto(Assunto assunto) {
    assuntos.remove(assunto);
    return this;
  }
}
