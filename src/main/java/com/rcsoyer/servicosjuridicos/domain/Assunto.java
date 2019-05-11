package com.rcsoyer.servicosjuridicos.domain;

import static org.apache.commons.lang3.StringUtils.trimToNull;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@Table(name = "assunto")
@EqualsAndHashCode(of = {"id", "descricao"})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Assunto implements Serializable {
    
    private static final long serialVersionUID = 5969365551210317302L;
    
    @Id
    @Column(updatable = false, nullable = false)
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @NotBlank
    @Size(max = 70)
    @Setter(AccessLevel.NONE)
    @Column(length = 70, nullable = false, unique = true)
    private String descricao;
    
    @NotNull
    @Column(nullable = false)
    private Boolean ativo;
    
    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer peso;
    
    public Assunto setDescricao(String descricao) {
        this.descricao = trimToNull(descricao);
        return this;
    }
}
