package com.rcsoyer.servicosjuridicos.domain;

import com.rcsoyer.servicosjuridicos.domain.enumeration.FeriasLicensaTipo;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FeriasLicenca.
 */
@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Table(name = "ferias_licenca")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FeriasLicenca implements Serializable {
    
    private static final long serialVersionUID = 8831667760716620943L;
    
    @Id
    @Column(updatable = false)
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @NotNull
    @Column(name = "dt_inicio", nullable = false)
    private LocalDate dtInicio;
    
    @NotNull
    @Column(name = "dt_fim", nullable = false)
    private LocalDate dtFim;
    
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FeriasLicensaTipo tipo;
    
    @NotNull
    @ManyToOne(optional = false)
    private Advogado advogado;
    
}
