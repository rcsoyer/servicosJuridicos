package com.rcsoyer.servicosjuridicos.service.dto;

import com.rcsoyer.servicosjuridicos.domain.enumeration.RangeDgCoordenacao;
import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A DTO for the AdvogadoDgCoordenacao entity.
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "advogado", "coordenacao"})
public class AdvogadoDgCoordenacaoDTO implements Serializable {
    
    private static final long serialVersionUID = 7110673865104666877L;
    
    private Long id;
    
    @Min(0)
    @Max(9)
    @NotNull
    private Integer dgPessoalInicio;
    
    @Min(0)
    @Max(9)
    @NotNull
    private Integer dgPessoalFim;
    
    @Min(0)
    @Max(9)
    private Integer dgDupla;
    
    @NotNull
    private RangeDgCoordenacao rangeDgCoordenacao;
    
    @Min(1)
    @NotNull
    private Long advogado;
    
    @Min(1)
    @NotNull
    private Long coordenacao;
    
}
