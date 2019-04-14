package com.rcsoyer.servicosjuridicos.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
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
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@Table(name = "modalidade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Modalidade implements Serializable {
    
    private static final long serialVersionUID = -8998428913027688634L;
    
    @Id
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @NotEmpty
    @Size(min = 1, max = 60)
    @Column(length = 60, nullable = false, unique = true)
    private String descricao;
    
}
