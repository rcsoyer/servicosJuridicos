package com.rcsoyer.servicosjuridicos.domain;

import com.rcsoyer.servicosjuridicos.domain.enumeration.RangeDgCoordenacao;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdvogadoDgCoordenacao.
 */
@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Table(name = "advogado_dg_coordenacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdvogadoDgCoordenacao implements Serializable {
    
    private static final long serialVersionUID = 5655625437095215917L;
    
    @Id
    @Column(updatable = false, nullable = false)
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @Min(0)
    @Max(9)
    @NotNull
    @Column(name = "dg_pessoal_inicio", nullable = false)
    private Integer dgPessoalInicio;
    
    @Min(0)
    @Max(9)
    @NotNull
    @Column(name = "dg_pessoal_fim", nullable = false)
    private Integer dgPessoalFim;
    
    @Min(0)
    @Max(9)
    @Column(name = "dg_dupla")
    private Integer dgDupla;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "range_dg_coordenacao")
    private RangeDgCoordenacao rangeDgCoordenacao;
    
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Advogado advogado;
    
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CoordenacaoJuridica coordenacao;
    
}
