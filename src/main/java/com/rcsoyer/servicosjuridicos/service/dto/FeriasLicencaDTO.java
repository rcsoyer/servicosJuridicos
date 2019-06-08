package com.rcsoyer.servicosjuridicos.service.dto;

import com.rcsoyer.servicosjuridicos.domain.enumeration.FeriasLicencaTipo;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A DTO for the FeriasLicenca entity.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public final class FeriasLicencaDTO implements Serializable {
    
    private static final long serialVersionUID = 8253942326687428160L;
    
    private Long id;
    
    @NotNull
    private LocalDate dtInicio;
    
    @NotNull
    private LocalDate dtFim;
    
    @NotNull
    private FeriasLicencaTipo tipo;
    
    @Min(1L)
    @NotNull
    private Long advogadoId;
    
}
