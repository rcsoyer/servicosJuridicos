package com.rcsoyer.servicosjuridicos.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A Assunto.
 */
@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "assunto")
@EqualsAndHashCode(of = "id")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Assunto implements Serializable {

  private static final long serialVersionUID = 5969365551210317302L;

  @Id
  @SequenceGenerator(name = "sequenceGenerator")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  private Long id;

  @NotNull
  @Size(min = 1, max = 70)
  @Column(name = "descricao", length = 70, nullable = false)
  private String descricao;

  @NotNull
  @Getter(value = AccessLevel.NONE)
  @Column(name = "ativo", nullable = false)
  private Boolean ativo;

  @NotNull
  @Min(value = 1)
  @Max(value = 5)
  @Column(name = "peso", nullable = false)
  private Integer peso;

  public Boolean isAtivo() {
    return ativo;
  }
}
