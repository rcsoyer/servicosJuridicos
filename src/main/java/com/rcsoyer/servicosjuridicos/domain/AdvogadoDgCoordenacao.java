package com.rcsoyer.servicosjuridicos.domain;

import com.rcsoyer.servicosjuridicos.domain.enumeration.RangeDgCoordenacao;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@EqualsAndHashCode(of = {"id", "advogado", "coordenacao"})
@Table(name = "advogado_dg_coordenacao", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"dg_pessoal_inicio", "dg_pessoal_fim", "coordenacaoId"}),
    @UniqueConstraint(columnNames = {"advogadoId", "coordenacaoId"})
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public final class AdvogadoDgCoordenacao implements Serializable {
    
    private static final long serialVersionUID = 5655625437095215917L;
    
    @Id
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
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "range_dg_coordenacao")
    private RangeDgCoordenacao rangeDgCoordenacao;
    
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "advogadoId")
    private Advogado advogado;
    
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "coordenacaoId")
    private CoordenacaoJuridica coordenacao;
    
}
