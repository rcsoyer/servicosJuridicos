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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Modalidade.
 */
@Entity
@Getter
@ToString
@Accessors(chain = true)
@Table(name = "modalidade")
@EqualsAndHashCode(of = "id")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public final class Modalidade implements Serializable {
    
    private static final long serialVersionUID = -8998428913027688634L;
    
    @Id
    @Setter
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @NotBlank
    @Size(max = 60)
    @Column(length = 60, nullable = false, unique = true)
    private String descricao;
    
    public Modalidade setDescricao(String descricao) {
        this.descricao = trimToNull(descricao);
        return this;
    }
}
