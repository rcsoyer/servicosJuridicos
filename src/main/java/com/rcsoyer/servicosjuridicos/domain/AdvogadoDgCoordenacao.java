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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import static org.apache.commons.lang3.StringUtils.trimToNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AdvogadoDgCoordenacao.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@Table(name = "advogado_dg_coordenacao")
@ToString(exclude = {"advogado", "coordenacao"})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdvogadoDgCoordenacao implements Serializable {
    
    private static final long serialVersionUID = 5655625437095215917L;
    
    @Id
    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    
    @NotEmpty
    @Size(min = 1, max = 1)
    @Setter(AccessLevel.NONE)
    @Column(name = "dg_pessoal_inicio", length = 1, nullable = false)
    private String dgPessoalInicio;
    
    @Size(min = 1, max = 1)
    @Setter(AccessLevel.NONE)
    @Column(name = "dg_pessoal_fim", length = 1)
    private String dgPessoalFim;
    
    @Size(min = 1, max = 1)
    @Setter(AccessLevel.NONE)
    @Column(name = "dg_dupla", length = 1)
    private String dgDupla;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "range_dg_coordenacao")
    private RangeDgCoordenacao rangeDgCoordenacao;
    
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Advogado advogado;
    
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CoordenacaoJuridica coordenacao;
    
    
    public AdvogadoDgCoordenacao setDgPessoalInicio(String dgPessoalInicio) {
        this.dgPessoalInicio = trimToNull(dgPessoalInicio);
        return this;
    }
    
    public AdvogadoDgCoordenacao setDgPessoalFim(String dgPessoalFim) {
        this.dgPessoalFim = trimToNull(dgPessoalFim);
        return this;
    }
    
    public AdvogadoDgCoordenacao setDgDupla(String dgDupla) {
        this.dgDupla = trimToNull(dgDupla);
        return this;
    }
}
